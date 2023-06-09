package com.revature.service;

import java.util.List;

import com.revature.payload.TestDto;

public interface TestService {

	public List<TestDto> saveTest(List<TestDto> testDto);

	public List<TestDto> getTestDetails(int visitId);

//	public void deleteTest(int testId);
}
