package com.ssafy.catchcam.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.ssafy.catchcam.model.CommonResponse;
import com.ssafy.catchcam.model.RentalRequest;
import com.ssafy.catchcam.model.ReviewRequest;
import com.ssafy.catchcam.model.UserAuthDetails;
import com.ssafy.catchcam.service.RentalService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/api")
@RestController
public class RentalController {

	Logger log = LoggerFactory.getLogger(this.getClass());


	@Autowired
	RentalService rentalService;
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@PostMapping("/rental")
	@ApiOperation(value = "대여 등록")
	public ResponseEntity<CommonResponse> insertRental(@RequestBody RentalRequest request) throws Exception{
		log.info(">> Load : insertRental <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {
			
			rentalService.insertRental(request);
			result.result = "대여 신청이 완료되었습니다.";
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.info(">> Error : insertRental <<");
			log.info(e.getMessage().toString());
			result.msg = "fail";
			result.result = e.getMessage().toString();
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		}
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@GetMapping("/rentals")
	@ApiOperation(value = "모든 대여 목록 조회")
	public ResponseEntity<CommonResponse> getRentalList() {
		log.info(">> Load : getRentalList <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			result.result = rentalService.getRentalList();
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
			log.info(">> Error : getRentalList <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}

	@GetMapping("/stores")
	@ApiOperation(value = "모든 지점 목록 조회")
	public ResponseEntity<CommonResponse> getStoreList() {
		log.info(">> Load : getStoreList <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			result.result = rentalService.getStoreList();
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
			log.info(">> Error : getStoreList <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}

	@GetMapping("/item/{itemId}")
	@ApiOperation(value = "단일 상품 조회")
	public ResponseEntity<CommonResponse> getItemInfo(@PathVariable long itemId) {
		
		log.info(">> Load : getItemInfo <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			result.result = rentalService.getItemInfo(itemId);
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
			log.info(">> Error : getItemInfo <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}

//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@GetMapping("/reviews/{itemId}")
	@ApiOperation(value = "모든 리뷰 목록 조회")
	public ResponseEntity<CommonResponse> getReviewList(@PathVariable long itemId, @RequestParam("page") int page) {
		log.info(">> Load : getReviewList <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			UserAuthDetails user = null;
			try {
				user = (UserAuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			} catch (Exception e) {
				user = new UserAuthDetails();
				user.setUserId(0);
			}
			result.result = rentalService.getReviewList(itemId, user.getUserId(), page);
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
			log.info(">> Error : getReviewList <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@PostMapping("/review")
	@ApiOperation(value = "리뷰 등록")
	public ResponseEntity<CommonResponse> insertReview(@RequestBody ReviewRequest request) throws Exception{
		log.info(">> Load : insertReview <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {
			
			rentalService.insertReview(request);
			result.result = "리뷰 등록이 완료되었습니다.";
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.info(">> Error : insertReview <<");
			log.info(e.getMessage().toString());
			result.msg = "fail";
			result.result = e.getMessage().toString();
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		}
		return response;
	}
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "리뷰 수정")
	@PutMapping("/review")
	public ResponseEntity<CommonResponse> updateReview(@RequestBody ReviewRequest request) {
		log.info(">> Load : updateReview <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();
		try {
			rentalService.updateReview(request);
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			log.info(">> Error : updateReview <<");
			log.info(e.getMessage().toString());
			result.msg = "fail";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "리뷰 삭제")
	@DeleteMapping("/review/{reviewId}")
	public ResponseEntity<CommonResponse> deleteReview(@PathVariable("reviewId") long reviewId) {
		log.info(">> Load : deleteReview <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			rentalService.deleteReview(reviewId);
			result.msg = "success";
			result.result = "삭제되었습니다.";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			result.result = "리뷰를 삭제할 수 없습니다.";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
			log.info(">> Error : deleteReview <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}
	
	@GetMapping("/reviews/recent")
	@ApiOperation(value = "최신 리뷰 조회")
	public ResponseEntity<CommonResponse> getRecentReviews() {
		log.info(">> Load : getRecentReviews <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			result.result = rentalService.getRecentReviews();
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
			log.info(">> Error : getRecentReviews <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "대여 예약 취소")
	@DeleteMapping("/rental/{rentalId}")
	public ResponseEntity<CommonResponse> deleteRental(@PathVariable("rentalId") long rentalId) {
		log.info(">> Load : deleteRental <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			rentalService.deleteRental(rentalId);
			result.msg = "success";
			result.result = "예약이 취소되었습니다.";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			result.result = "예약을 취소할 수 없습니다.";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
			log.info(">> Error : deleteRental <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}
}
