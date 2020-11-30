package com.ssafy.catchcam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.catchcam.model.UserAuthDetails;
import com.ssafy.catchcam.model.UserResponse;
import com.ssafy.catchcam.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public List<UserResponse> getUserList() throws Exception{
		return userRepository.getUserList();
	}
	
	@Override
	public void insertUser(UserAuthDetails user) throws Exception{
		userRepository.insertUser(user);
	}
	
}