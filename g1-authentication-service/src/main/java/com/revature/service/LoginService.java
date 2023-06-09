package com.revature.service;

import org.springframework.stereotype.Service;

import com.revature.entity.dto.PatientDto;


public interface LoginService {
	public PatientDto loginService(String email,String password);
	public void updatepassword(String email,String password);
}
