package com.revature.controllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controller.PatientInfoController;
import com.revature.entity.dto.PatientDto;
import com.revature.service.PatientInfoService;

@WebMvcTest(PatientInfoController.class)
public class PatientControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PatientInfoService patientService;

	@Test
	public void GetPatientByIdTest() throws Exception {
		PatientDto p = new PatientDto(160, "sneha@gmail.com", "Ms.", "Sneha", "Katika", "19/09/2002", "1234567890",
				"sneha123", "female", "Hyderabad");
		when(patientService.getPatientById(160)).thenReturn(p);
		mockMvc.perform(get("/api/v1/patient/160").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.patientId").value(160));
	}

	@Test
	public void getPatientsTest() throws Exception {
		List<PatientDto> patients = Arrays.asList(
				new PatientDto(160, "sneha@gmail.com", "Ms.", "Sneha", "Katika", "19/09/2002", "1234567890", "sneha123",
						"female", "Hyderabad"),
				new PatientDto(161, "vamshi@gmail.com", "Mr.", "Vamshi", "Katika", "04/11/2001", "1234567890",
						"vamshi123", "male", "Hyderabad"));
		when(patientService.getAllPatients()).thenReturn(patients);
		mockMvc.perform(get("/api/v1/patient").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].firstName").value("Sneha"));
		
		mockMvc.perform(get("/api/v1/patient").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$[1].firstName").value("Vamshi"));

	}
	
	
	@Test
	public void putPatientTest() throws Exception {
		PatientDto p = new PatientDto(160, "sneha@gmail.com", "Ms.", "Sneha", "Katika", "19/09/2002", "1234567890",
				"sneha123", "female", "Hyderabad");
		PatientDto p1 = new PatientDto(160, "sneha123@gmail.com", "Ms.", "Sneha", "Katika", "19/09/2002", "1234567890",
				"sneha123", "female", "Hyderabad");
		when(patientService.updatePatientById(any(Integer.class),any(PatientDto.class))).thenReturn(p1);
		mockMvc.perform(put("/api/v1/patient/160",p1).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(p1)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email").value("sneha123@gmail.com"));
		

	}

}
