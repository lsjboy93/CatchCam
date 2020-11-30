package com.ssafy.catchcam.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.catchcam.config.security.JwtTokenProvider;
import com.ssafy.catchcam.model.CommonResponse;
import com.ssafy.catchcam.model.LoginRequest;
import com.ssafy.catchcam.model.SignUpRequest;
import com.ssafy.catchcam.model.UserAuthDetails;
import com.ssafy.catchcam.service.UserAuthDetailService;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/api")
@RestController
public class AccountController {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired // 회원 관리
	UserAuthDetailService userAuthDetailService;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@PostMapping("/login")
	@ApiOperation(value = "로그인")
	public ResponseEntity<CommonResponse> login(@RequestBody LoginRequest request) {
		log.info(">> Load : login <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		// 사용자가 입력한 이메일로 DB검색
		try {
			UserAuthDetails user = userAuthDetailService.getUserByEmail(request.getEmail());
			// user 정보가 조회 됐다면
			if (user != null) {
				// 비밀번호 매칭
				if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
					result.msg = "fail";
					result.result = "비밀번호가 일치하지 않습니다.";
					response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
				} else {
					result.msg = "success";
					result.result = jwtTokenProvider.createToken(
							String.valueOf(user.getUserId()));
					response = new ResponseEntity<>(result, HttpStatus.OK);
				}
			} else {
				result.msg = "fail";
				result.result = "가입하지 않은 이메일이거나, 잘못된 비밀번호입니다.";
				response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.info(">> Error : login <<");
			log.info(e.getMessage().toString());
			result.msg = "fail";
			result.result = e.getMessage().toString();
			response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@PostMapping("/signup")
	@ApiOperation(value = "가입하기")
	public ResponseEntity<CommonResponse> signUp(@RequestBody SignUpRequest request) {
		log.info(">> Load : signUp <<");
		final CommonResponse result = new CommonResponse();
		ResponseEntity<CommonResponse> response = null;
		
		// DB에 user Email이 있다면 그에 해당하는 user 정보를 / 그렇지 않을 경우 null 반환
		try {
			// email, nickname 중복 체크
			if (checkUser(request.getEmail(), request.getUserName())) {
				UserAuthDetails user = new UserAuthDetails(request.getEmail(), request.getUserName(),
						passwordEncoder.encode(request.getPassword()));
				userAuthDetailService.signUp(user);
				result.msg = "success";
				result.result = "가입이 완료 되었습니다.";
				response = new ResponseEntity<CommonResponse>(result, HttpStatus.CREATED);
			} else {
				// if 중복되는 계정이 있다면 중복
				result.msg = "duplicate";
				result.result = "이미 가입한 이메일입니다.";
				response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.info(">> Error : signUp <<");
			log.info(e.getMessage().toString());
			result.msg = "fail";
			result.result = e.getMessage().toString();
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	public boolean checkUser(String email, String nickname) throws Exception {
		return userAuthDetailService.checkValidEmail(email) == 0;
	}
}