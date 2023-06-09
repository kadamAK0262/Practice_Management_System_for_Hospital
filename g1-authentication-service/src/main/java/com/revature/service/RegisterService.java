package com.revature.service;

import org.springframework.stereotype.Service;

import com.revature.entity.dto.PatientDto;

public interface RegisterService {
	public PatientDto registerService(PatientDto patientDto);
}
