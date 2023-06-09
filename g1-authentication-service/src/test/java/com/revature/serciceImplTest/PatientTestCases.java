package com.revature.serciceImplTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.stubbing.OngoingStubbing;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.entity.Patient;
import com.revature.entity.dto.PatientDto;
import com.revature.repository.PatientRepository;
import com.revature.serviceImpl.LoginServiceImpl;
import com.revature.serviceImpl.RegisterServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PatientTestCases {
	@Autowired
	public LoginServiceImpl patientService;
	@Autowired
	public RegisterServiceImpl registerService;
	@MockBean
	public PatientRepository patientRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Test
	public void testPatientLogin() {
		String email="sneha@gmail.com";
		String password="sneha123";
		Patient p=new Patient(1, "sneha@gmail.com", "Ms.", "Sneha", "Katika", "19/09/2002","1234567890", "sneha123", "female", "Hyderabad");
		when(patientRepository.findByEmailAndPassword(email,password)).thenReturn(p);
		Assertions.assertEquals("sneha@gmail.com", patientService.loginService("sneha@gmail.com", "sneha123").getEmail());

	}
	
//	@Test
//	public void registerTest() {
//		Patient p=new Patient.builder()
//				.patientId(1)
//				.email("sneha@gmail.com")
//				.title("Ms.")
//				.firstName("Sneha")
//				.lastName("Katika")
//				.dob("19/09/2002")
//				.contactNumber("1234567890")
//				.password("sneha123")
//				.gender("female")
//				.address("Hyderabad").build();
//		patientRepository.save(p);
//		assertThat(p.getPatientId()).isGreaterThan(0);
//	}

	
}
