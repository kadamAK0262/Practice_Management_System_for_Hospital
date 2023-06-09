package com.revature.service;

import java.util.List;
import java.util.Optional;

import com.revature.entity.dto.PatientDto;

public interface PatientInfoService {
	
	public List<PatientDto> getAllPatients();
	
	public PatientDto getPatientById(int id);

	PatientDto updatePatientById(int id, PatientDto patientDto);
	
	public long getCount();
	
}
