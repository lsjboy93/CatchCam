package com.ssafy.catchcam.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.catchcam.model.UserAuthDetails;
import com.ssafy.catchcam.model.UserResponse;

public interface UserRepository {
	
	//유저 목록 조회
	public List<UserResponse> getUserList() throws Exception;

	// 회원가입 시 닉네임, 이메일 체크 중복 체크
	public int checkValidEmail(@Param("email") String email) throws Exception;
	
	//회원가입
	public void insertUser(UserAuthDetails user) throws Exception;

	// 등록된 유저 id로 검색
	public UserAuthDetails findUserByUserId(long userId) throws Exception;
	
	// 등록된 이메일로 유저 검색
	public UserAuthDetails findUserByEmail(String email) throws Exception;
}
