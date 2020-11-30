import logging
import contextlib
import os
import socket
import subprocess
import threading
import time

import cv2 as cv
import numpy as np

from droneapp.models.base import Singleton

logger = logging.getLogger(__name__)

DEFAULT_DISTANCE = 0.30
DEFAULT_SPEED = 10
DEFAULT_DEGREE = 10

FRAME_X = int(960/2)
FRAME_Y = int(720/2)
# FRAME_X = 960
# FRAME_Y = 720
FRAME_AREA = FRAME_X * FRAME_Y

FRAME_SIZE = FRAME_AREA * 3
FRAME_CENTER_X = FRAME_X / 2
FRAME_CENTER_Y = FRAME_Y / 2

CMD_FFMPEG = (f'ffmpeg -hwaccel auto -hwaccel_device opencl -i pipe:0 '
              f'-pix_fmt bgr24 -s {FRAME_X}x{FRAME_Y} -f rawvideo pipe:1')

FACE_DETECT_XML_FILE = './droneapp/models/haarcascade_frontalface_default.xml'

SNAPSHOT_IMAGE_FOLDER = './droneapp/static/img/snapshots/'

# Load Yolo
YOLO_WEIGHT_FILE = './droneapp/models/yolov3.weights'
YOLO_CFG_FILE = './droneapp/models/yolov3.cfg'
COCO_NAMES_FILE = './droneapp/models/coco.names'
net = cv.dnn.readNet(YOLO_WEIGHT_FILE, YOLO_CFG_FILE)
classes = []
with open(COCO_NAMES_FILE, "r") as f:
   classes = [line.strip() for line in f.readlines()]
layer_names = net.getLayerNames()
output_layers = [layer_names[i[0] - 1] for i in net.getUnconnectedOutLayers()]
colors = np.random.uniform(0, 255, size=(len(classes), 3))

class ErrorNoFaceDetectXMLFile(Exception):
    """Error no face detect xml file"""


class ErrorNoImageDir(Exception):
    """Error no image dir"""


class DroneManager(metaclass=Singleton):
    def __init__(self, host_ip='192.168.10.2', host_port=8889,
                 drone_ip='192.168.10.1', drone_port=8889,
                 is_imperial=False, speed=DEFAULT_SPEED):
        self.host_ip = host_ip
        self.host_port = host_port
        self.drone_ip = drone_ip
        self.drone_port = drone_port
        self.drone_address = (drone_ip, drone_port)
        self.is_imperial = is_imperial
        self.speed = speed
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        self.socket.bind((self.host_ip, self.host_port))

        self.response = None
        self.stop_event = threading.Event()
        self._response_thread = threading.Thread(target=self.receive_response,
                                           args=(self.stop_event, ))
        self._response_thread.start()

        self.patrol_event = None
        self.is_patrol = False
        self._patrol_semaphore = threading.Semaphore(1)
        self._thread_patrol = None

        self.proc = subprocess.Popen(CMD_FFMPEG.split(' '),
                                     stdin=subprocess.PIPE,
                                     stdout=subprocess.PIPE)
        self.proc_stdin = self.proc.stdin
        self.proc_stdout = self.proc.stdout

        self.video_port = 11111

        self._receive_video_thread = threading.Thread(
            target=self.receive_video,
            args=(self.stop_event, self.proc_stdin,
                  self.host_ip, self.video_port,))
        self._receive_video_thread.start()

        if not os.path.exists(FACE_DETECT_XML_FILE):
            raise ErrorNoFaceDetectXMLFile(f'No {FACE_DETECT_XML_FILE}')
        self.face_cascade = cv.CascadeClassifier(FACE_DETECT_XML_FILE)
        self._is_enable_face_detect = False

        if not os.path.exists(SNAPSHOT_IMAGE_FOLDER):
            raise ErrorNoImageDir(f'{SNAPSHOT_IMAGE_FOLDER} does not exists')
        self.is_snapshot = False
        self.file_opened = False
        self.writer = None

        self._command_semaphore = threading.Semaphore(1)
        self._command_thread = None

        self.send_command('command')
        self.send_command('streamon')
        self.send_command('battery?')
        self.set_speed(self.speed)

        # self.tracker = cv.TrackerCSRT_create()
        self.tracker = cv.TrackerKCF_create()
        self.initBB = None
        self.x_coor = None
        self.y_coor = None


    def receive_response(self, stop_event):
        while not stop_event.is_set():
            try:
                self.response, ip = self.socket.recvfrom(3000)
                logger.info({'action': 'receive_response',
                             'response': self.response})
            except socket.error as ex:
                logger.error({'action': 'receive_response',
                             'ex': ex})
                break

    def __dell__(self):
        self.stop()

    def stop(self):
        self.stop_event.set()
        retry = 0
        while self._response_thread.isAlive():
            time.sleep(0.3)
            if retry > 30:
                break
            retry += 1
        self.socket.close()
        os.kill(self.proc.pid, 9)
        # Windows
        # import signal
        # os.kill(self.proc.pid, signal.CTRL_C_EVENT)

    def send_command(self, command, blocking=True):
        self._command_thread = threading.Thread(
            target=self._send_command,
            args=(command, blocking,))
        self._command_thread.start()

    def _send_command(self, command, blocking=True):
        is_acquire = self._command_semaphore.acquire(blocking=blocking)
        if is_acquire:
            with contextlib.ExitStack() as stack:
                stack.callback(self._command_semaphore.release)
                logger.info({'action': 'send_command', 'command': command})
                self.socket.sendto(command.encode('utf-8'), self.drone_address)

                retry = 0
                while self.response is None:
                    time.sleep(0.3)
                    if retry > 3:
                        break
                    retry += 1

                if self.response is None:
                    response = None
                else:
                    response = self.response.decode('utf-8')
                self.response = None
                return response

        else:
            logger.warning({'action': 'send_command', 'command': command, 'status': 'not_acquire'})

    def takeoff(self):
        return self.send_command('takeoff')

    def land(self):
        return self.send_command('land')

    def move(self, direction, distance):
        distance = float(distance)
        if self.is_imperial:
            distance = int(round(distance * 30.48))
        else:
            distance = int(round(distance * 100))
        return self.send_command(f'{direction} {distance}')

    def up(self, distance=DEFAULT_DISTANCE):
        return self.move('up', distance)

    def down(self, distance=DEFAULT_DISTANCE):
        return self.move('down', distance)

    def left(self, distance=DEFAULT_DISTANCE):
        return self.move('left', distance)

    def right(self, distance=DEFAULT_DISTANCE):
        return self.move('right', distance)

    def forward(self, distance=DEFAULT_DISTANCE):
        return self.move('forward', distance)

    def back(self, distance=DEFAULT_DISTANCE):
        return self.move('back', distance)

    def set_speed(self, speed):
        return self.send_command(f'speed {speed}')

    def clockwise(self, degree=DEFAULT_DEGREE):
        return self.send_command(f'cw {degree}')

    def counter_clockwise(self, degree=DEFAULT_DEGREE):
        return self.send_command(f'ccw {degree}')

    def flip_front(self):
        return self.send_command('flip f')

    def flip_back(self):
        return self.send_command('flip b')

    def flip_left(self):
        return self.send_command('flip l')

    def flip_right(self):
        return self.send_command('flip r')

    def patrol(self):
        if not self.is_patrol:
            self.patrol_event = threading.Event()
            self._thread_patrol = threading.Thread(
                target=self._patrol,
                args=(self._patrol_semaphore, self.patrol_event,))
            self._thread_patrol.start()
            self.is_patrol = True

    def stop_patrol(self):
        if self.is_patrol:
            self.patrol_event.set()
            retry = 0
            while self._thread_patrol.isAlive():
                time.sleep(0.3)
                if retry > 300:
                    break
                retry += 1
            self.is_patrol = False

    def _patrol(self, semaphore, stop_event):
        is_acquire = semaphore.acquire(blocking=False)
        if is_acquire:
            logger.info({'action': '_patrol', 'status': 'acquire'})
            with contextlib.ExitStack() as stack:
                stack.callback(semaphore.release)
                status = 0
                while not stop_event.is_set():
                    status += 1
                    if status == 1:
                        # self.up()
                        self.flip_left()
                    if status == 2:
                        self.clockwise(90)
                    if status == 3:
                        # self.down()
                        self.flip_right()
                    if status == 4:
                        self.flip_front()
                    if status == 5:
                        self.flip_back()                    
                    if status == 6:
                        status = 0
                    time.sleep(5)
        else:
            logger.warning({'action': '_patrol', 'status': 'not_acquire'})

    def receive_video(self, stop_event, pipe_in, host_ip, video_port):
        with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as sock_video:
            sock_video.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
            sock_video.settimeout(.5)
            sock_video.bind((host_ip, video_port))
            data = bytearray(2048)
            while not stop_event.is_set():
                try:
                    size, addr = sock_video.recvfrom_into(data)
                    # logger.info({'action': 'receive_video', 'data': data})
                except socket.timeout as ex:
                    logger.warning({'action': 'receive_video', 'ex': ex })
                    time.sleep(0.5)
                    continue
                except socket.error as ex:
                    logger.error({'action': 'receive_video', 'ex': ex})
                    break

                try:
                    pipe_in.write(data[:size])
                    pipe_in.flush()
                except Exception as ex:
                    logger.error({'action': 'receive_video', 'ex': ex})
                    break

    def video_binary_generator(self):
        while True:
            try:
                frame = self.proc_stdout.read(FRAME_SIZE)
            except Exception as ex:
                logger.error({'action': 'video_binary_generator', 'ex': ex})
                continue

            if not frame:
                continue

            frame = np.fromstring(frame, np.uint8).reshape(FRAME_Y, FRAME_X, 3)
            yield frame

    def enable_face_detect(self):
        self._is_enable_face_detect = True

    def disable_face_detect(self):
        self._is_enable_face_detect = False

    def video_jpeg_generator(self):
        for frame in self.video_binary_generator():
            if self._is_enable_face_detect:
                if self.is_patrol:
                    self.stop_patrol()

                gray = cv.cvtColor(frame, cv.COLOR_BGR2GRAY)
                faces = self.face_cascade.detectMultiScale(gray, 1.3, 5)
                for (x, y, w, h) in faces:
                    cv.rectangle(frame, (x, y), (x+w, y+h), (255, 0, 0), 2)

                    face_center_x = x + (w/2)
                    face_center_y = y + (h/2)
                    diff_x = FRAME_CENTER_X - face_center_x
                    diff_y = FRAME_CENTER_Y - face_center_y
                    face_area = w * h
                    percent_face = face_area / FRAME_AREA

                    drone_x, drone_y, drone_z, speed = 0, 0, 0, self.speed
                    # rotate_cw, rotate_ccw = 0, 0
                    if diff_x < -30:
                        drone_y = -30
                        # rotate_cw = 10
                    if diff_x > 30:
                        drone_y = 30
                        # rotate_ccw = 10
                    if diff_y < -15:
                        drone_z = -30
                    if diff_y > 15:
                        drone_z = 30
                    if percent_face > 0.30:
                        drone_x = -30
                    if percent_face < 0.02:
                        drone_x = 30
                    
                    # if rotate_cw:
                    #     self.clockwise(rotate_cw)
                    # elif rotate_ccw:
                    #     self.counter_clockwise(rotate_ccw)
                    # elif drone_x or drone_z:
                    self.send_command(f'go {drone_x} {drone_y} {drone_z} {speed}',
                                        blocking=False)

                    break

            # if self.is_snapshot and self.x_coor and self.y_coor:
            if self.x_coor and self.y_coor:
                if not self.initBB:
                    self.init_tracking(frame)
                else:
                    # draw rectangle
                    (success, box) = self.tracker.update(frame)
                    if success:
                        (x, y, w, h) = [int(v) for v in box]
                        cv.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 1)


                        object_center_x = x + (w/2)
                        object_center_y = y + (h/2)
                        diff_x = FRAME_CENTER_X - object_center_x
                        diff_y = FRAME_CENTER_Y - object_center_y
                        object_area = w * h
                        percent_object = object_area / FRAME_AREA

                        drone_x, drone_y, drone_z, speed = 0, 0, 0, self.speed
                        # rotate_cw, rotate_ccw = 0, 0
                        if diff_x < -30:
                            drone_y = -20
                            # rotate_cw = 10
                        if diff_x > 30:
                            drone_y = 20
                            # rotate_ccw = 10
                        if diff_y < -15:
                            drone_z = -20
                        if diff_y > 15:
                            drone_z = 20
                        if percent_object > 0.70:
                            drone_x = -30
                        if percent_object < 0.01:
                            drone_x = 30

                        # if rotate_cw:
                        #     self.clockwise(rotate_cw)
                        # elif rotate_ccw:
                        #     self.counter_clockwise(rotate_ccw)
                        # elif drone_x or drone_z:
                        self.send_command(f'go {drone_x} {drone_y} {drone_z} {speed}',
                                            blocking=False)

            _, jpeg = cv.imencode('.jpg', frame)
            jpeg_binary = jpeg.tobytes()

            if self.is_snapshot:
                backup_file = time.strftime("%Y%m%d-%H%M%S") + '.avi'
                snapshot_file = 'snapshot.avi'
                for filename in (backup_file, snapshot_file):
                    file_path = os.path.join(
                        SNAPSHOT_IMAGE_FOLDER, filename)
                    if not self.file_opened:
                        self.writer = cv.VideoWriter(file_path, cv.VideoWriter_fourcc(*'MJPG'), 60, (FRAME_X, FRAME_Y))
                        self.file_opened = True
                    else:
                        self.writer.write(frame)
                    # with open(file_path, 'wb') as f:
                    #     f.write(jpeg_binary)
                # self.is_snapshot = False

            yield jpeg_binary

    def snapshot(self):
        if self.is_snapshot:
            self.is_snapshot = False
            if self.file_opened:
                self.writer.release()
                self.file_opened = False
            return True
        else:
            self.is_snapshot = True
            return True
        retry = 0
        while retry < 3:
            if not self.is_snapshot:
                return True
            time.sleep(0.1)
            retry += 1
        return False

    def init_coordinate(self, x_coor, y_coor):
        # if self.is_snapshot:
        #     self.x_coor = int(x_coor)
        #     self.y_coor = int(y_coor)
        # else:
        #     self.x_coor = None
        #     self.y_coor = None
        if self.x_coor or self.y_coor:
            self.x_coor = None
            self.y_coor = None
        else:
            self.x_coor = int(x_coor)
            self.y_coor = int(y_coor)

    def init_tracking(self, frame):
        # if self.is_snapshot and self.x_coor and self.y_coor:
        if self.x_coor and self.y_coor:
            logger.info({'action': 'init_tracking'})
            # self.initBB = (self.x_coor, self.y_coor, 45*3, 45*3)
            # self.tracker.init(frame, self.initBB)

            (H, W) = frame.shape[:2]

            # Detecting objects
            blob = cv.dnn.blobFromImage(frame, 0.00392, (416, 416), (0, 0, 0), True, crop=False)
            net.setInput(blob)
            outs = net.forward(output_layers)        
            
            
            #Showing informations on the screen
            class_ids = []
            confidences = []
            boxes = []
            for out in outs:
                for detection in out:
                    scores = detection[5:]
                    class_id = np.argmax(scores)
                    if str(classes[class_id]) == 'sports ball' or str(classes[class_id]) == 'person':
                        confidence = scores[class_id]
                        if confidence > 0.5:
                            # Object detected
                            center_x = int(detection[0] * W)
                            center_y = int(detection[1] * H)
                            w = int(detection[2] * W)
                            h = int(detection[3] * H)

                            # Rectangle coordinates
                            x = int(center_x - w / 2)
                            y = int(center_y - h / 2)

                            if (x <= self.x_coor <= x+w and y <= self.y_coor <= y+h):
                                boxes.append([x, y, w, h])
                                confidences.append(float(confidence))
                                class_ids.append(class_id)


            if boxes:
                indexes = cv.dnn.NMSBoxes(boxes, confidences, 0.5, 0.4)
                for i in range(len(boxes)):
                    if i in indexes:
                        x, y, w, h = boxes[i]
                        label = str(classes[class_ids[i]])
                        logger.info({'label': label})
                        
                        # if (label == 'sports ball' or label == 'person' ) and (x <= self.x_coor <= x+w and y <= self.y_coor <= y+h):
                        if label == 'sports ball' or label == 'person' :
                            self.initBB = (x,y,w,h)
                            self.tracker.init(frame, self.initBB)
                            return

            self.x_coor = None
            self.y_coor = None
                        



