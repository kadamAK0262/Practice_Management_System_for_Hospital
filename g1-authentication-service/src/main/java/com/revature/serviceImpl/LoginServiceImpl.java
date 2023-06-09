package com.revature.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.revature.entity.Patient;
import com.revature.entity.dto.PatientDto;
import com.revature.repository.PatientRepository;
import com.revature.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PatientRepository patientRepository;

	@Override
	public PatientDto loginService(String email, String password) {
		Patient patient=patientRepository.findByEmailAndPassword(email,password);
		return modelMapper.map(patient, PatientDto.class);
	}	
	@Override
	public void updatepassword(String email,String password) {
		patientRepository.updatePassword(password,email);
	}
}
