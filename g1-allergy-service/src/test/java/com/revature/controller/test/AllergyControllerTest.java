package com.revature.controller.test;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.revature.controller.AllergyController;
import com.revature.dto.AllergyDto;
import com.revature.service.AllergyService;

@WebMvcTest(AllergyController.class)
public class AllergyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AllergyService allergyService;
	
	@Test
	public void getAllergyById() throws Exception{
		AllergyDto a=new AllergyDto(1,"Nose Allergy","Itching");
		when(allergyService.getAllergyById(1)).thenReturn(a);
		mockMvc.perform(get("/api/v1/allergy/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.allergyId").value(1));
}
	@Test
	public void getAllergyTest()throws Exception{
		List<AllergyDto> allergies = Arrays.asList(
			new AllergyDto(1,"Nose Allergy","Itching"),new AllergyDto(2,"Skin Allergy","Rashes"));
		when(allergyService.allAllergies()).thenReturn(allergies);
		mockMvc.perform(get("/api/v1/allergy").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
	.andExpect(jsonPath("$[0].allergyName").value("Nose Allergy"));
		
		mockMvc.perform(get("/api/v1/allergy").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$[1].allergyName").value("Skin Allergy"));
	
	
	}
}
