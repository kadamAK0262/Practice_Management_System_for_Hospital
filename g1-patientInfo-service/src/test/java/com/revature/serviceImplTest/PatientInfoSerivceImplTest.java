package com.revature.serviceImplTest;

import static org.junit.Assert.assertEquals;


import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.entity.Patient;
import com.revature.entity.dto.PatientDto;
import com.revature.repository.PatientRepository;
import com.revature.serviceImpl.PatientInfoServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PatientInfoSerivceImplTest {
	@Autowired
	private PatientInfoServiceImpl patientService;

	@MockBean
	private PatientRepository patientRepo;

	@Test
	public void getAllPatientsTests() {
		when(patientRepo.findAll()).thenReturn(Stream.of(new Patient(160,"sneha@gmail.com","Ms.","Sneha","Katika","19/09/2002",
				"1234567890","sneha123","female","Hyderabad")).collect(Collectors.toList()));
		assertEquals(1,patientService.getAllPatients().size());
//		patientService.getAllPatients();
//		verify(patientRepo).findAll();
	}

	@Test
	public void getPatientById() {
		Optional<Patient> p = Optional.of(new Patient(160, "sneha@gmail.com", "Ms.", "Sneha", "Katika", "19/09/2002",
				"1234567890", "sneha123", "female", "Hyderabad"));
		when(patientRepo.findById(160)).thenReturn(p);
		assertEquals(160, patientService.getPatientById(160).getPatientId());	

	}

	@Test
	public void updatePatientById() {
		Optional<Patient> p = Optional.of(new Patient(160, "sneha@gmail.com", "Ms.", "Sneha", "Katika", "19/09/2002",
				"1234567890", "sneha123", "female", "Hyderabad"));
		PatientDto newP = new PatientDto(160, "vamshi@gmail.com", "Mr.", "vamshi", "Katika", "19/09/2001", "1234567890",
				"vamshi123", "male", "Hyderabad");
		when(patientRepo.findById(160)).thenReturn(p);
		Assertions.assertEquals("vamshi", patientService.updatePatientById(160, newP).getFirstName());
		Assertions.assertEquals("vamshi@gmail.com", patientService.updatePatientById(160, newP).getEmail());
	}
	

}
