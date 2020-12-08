package com.ssafy.catchcam.service;

import java.util.List;
import java.util.Map;

import com.ssafy.catchcam.model.UserAuthDetails;
import com.ssafy.catchcam.model.UserResponse;

public interface UserService {

	//유저 목록 조회
	public List<UserResponse> getUserList() throws Exception;
	
	//회원가입
	public void insertUser(UserAuthDetails user) throws Exception;
}
