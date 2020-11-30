package com.ssafy.catchcam.repository;

import java.util.List;
import java.util.Map;

public interface EmployeeRepository {
	
	public List<Map<String, Object>> getEmployee() throws Exception;
	public void insertEmployee(String name) throws Exception;
}
