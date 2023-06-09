package com.revature.controllertestcases;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controller.AuthenticationController;
import com.revature.entity.dto.PatientDto;
import com.revature.serviceImpl.LoginServiceImpl;
import com.revature.serviceImpl.RegisterServiceImpl;

import io.micrometer.core.instrument.MeterRegistry;

@WebMvcTest(AuthenticationController.class)
public class PatientController {
	@MockBean
	public LoginServiceImpl loginService;
	
	@MockBean
	public RegisterServiceImpl registerService;
	
	@Autowired
	public AuthenticationController authController;
	
	 @Autowired
	 MockMvc mockMvc;
	 
	 @Test
	 public void loginTest() throws Exception{
		 PatientDto p = new PatientDto(160, "sneha@gmail.com", "Ms.", "Sneha", "Katika", "19/09/2002", "1234567890",
					"sneha123", "female", "Hyderabad");
		 String email="sneha@gmail.com";
		 String password="sneha123";
         when(loginService.loginService("sneha@gmail.com","sneha123")).thenReturn(p);
         mockMvc.perform(post("/api/v1/patient/login")
        		 .param("email", email)
        		 .param("password",password))
                 .andExpect(status().isAccepted())
                 .andExpect(content().contentType("application/json"))
                 .andExpect(jsonPath("$.firstName").value("Sneha"));
	 }
	 
	 @Test
	 public void registerTest() throws Exception{
		 PatientDto p = new PatientDto(160, "sneha@gmail.com", "Ms.", "Sneha", "Katika", "19/09/2002", "1234567890",
					"sneha123", "female", "Hyderabad");
		 when(registerService.registerService(any(PatientDto.class))).thenReturn(p);
		 this.mockMvc.perform(post("/api/v1/patient/register",p)
        		 .contentType(MediaType.APPLICATION_JSON)
        		 .content(new ObjectMapper().writeValueAsString(p)))
		 			.andExpect(status().isCreated())
        		 .andExpect(jsonPath("$.firstName",is("Sneha")));
	 }
	 
	 
	

}
