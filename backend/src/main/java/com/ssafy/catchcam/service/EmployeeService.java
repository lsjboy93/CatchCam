package com.ssafy.catchcam.service;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
	public List<Map<String, Object>> getEmployee() throws Exception;
	public void insertEmployee(String name) throws Exception;
}
