package com.revature.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.entity.Patient;
import com.revature.entity.dto.PatientDto;
import com.revature.repository.PatientRepository;
import com.revature.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PatientRepository patientRepository;
	
	
	@Override
	public PatientDto registerService(PatientDto patientDto) {
		Patient registerPatient=(Patient) patientRepository.save(modelMapper.map(patientDto, Patient.class));
		return modelMapper.map(registerPatient,PatientDto.class);
	}
	
}