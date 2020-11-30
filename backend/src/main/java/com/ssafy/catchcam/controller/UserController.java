package com.ssafy.catchcam.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.catchcam.model.CommonResponse;
import com.ssafy.catchcam.service.UserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RequestMapping("/api")
@RestController
public class UserController {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserService userService;
	

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@GetMapping("/users")
	@ApiOperation(value = "모든 회원 목록 조회")
	public ResponseEntity<CommonResponse> getUsers() {
		log.info(">> Load : getUsers <<");
		ResponseEntity<CommonResponse> response = null;
		final CommonResponse result = new CommonResponse();

		try {
			result.result = userService.getUserList();
			result.msg = "success";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.OK);
		} catch (Exception e) {
			result.msg = "fail";
			response = new ResponseEntity<CommonResponse>(result, HttpStatus.BAD_REQUEST);
			log.info(">> Error : getUsers <<");
			log.info(e.getMessage().toString());
		}
		return response;
	}
}
