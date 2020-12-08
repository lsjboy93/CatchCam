package com.ssafy.catchcam.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.catchcam.repository.EmployeeRepository;

@Service
public class EmployeeServiceImplement implements EmployeeService {
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Override
	public List<Map<String, Object>> getEmployee() throws Exception{
		System.out.println("EmployeeServiceImplement getEmploy~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~```");
		return employeeRepository.getEmployee();
	}
	
	@Override
	public void insertEmployee(String name) throws Exception{
		employeeRepository.insertEmployee(name);
	}
}