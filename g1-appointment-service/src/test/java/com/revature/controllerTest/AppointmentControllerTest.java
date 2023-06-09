package com.revature.controllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controller.AppointmentController;
import com.revature.dto.AppointmentDto;
import com.revature.serviceImpl.AppointmentServiceImpl;



@WebMvcTest(AppointmentController.class)
public class AppointmentControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AppointmentServiceImpl appointmentService;

	
//	@Test
//	public void saveAppointment() throws Exception{
//		AppointmentDto a = new AppointmentDto(1,"Cold","04/04/2023","Accepted",1,"physician@gmail.com","01/04/2023");
//		when(appointmentService.saveAppointment(any(AppointmentDto.class))).thenReturn(a);
//		mockMvc.perform(post("/api/v1/appointment",a)
//				.contentType(MediaType.APPLICATION_JSON_VALUE)
//				.content(new ObjectMapper().writeValueAsString(a)))
//				.andExpect(status().isCreated())
//				.andExpect(jsonPath("$.id").value(1));
//		
//	}
//	
//	@Test
//	public void getAllAppointments() throws Exception{
//		List<AppointmentDto> a = Arrays.asList(new AppointmentDto(1,"Cold","04/04/2023","Accepted",1,"physician@gmail.com","01/04/2023"));
//		when(appointmentService.getAllAppointments(any(Integer.class))).thenReturn(a);
//		mockMvc.perform(get("/api/v1/patient-appointment/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
//				.andExpect(jsonPath("$[0].acceptance").value("Accepted"));
//	}
	
	@Test
	public void getAcceptedAppointments() throws Exception{
//		List<AppointmentDto> a = Arrays.asList(new AppointmentDto(1,"Cold","04-04-2023","Accepted",1,"physician@gmail.com","01/04/2023"));
//		when(appointmentService.getAcceptedAppointments(any(),any(),any())).thenReturn(a);
//		mockMvc.perform(get("/api/v1/appointment/physician@gmail.com/04-04-2023/Accepted","physician@gmail.com","04-04-2023","Accepted").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	
		List<AppointmentDto> contact = Arrays.asList( new AppointmentDto(1,"Cold","04-04-2023","Accepted",1,"physician@gmail.com","01/04/2023"));
        when(appointmentService.getAcceptedAppointments("physician@gmail.com","04-04-2023","Accepted")).thenReturn(contact);
        mockMvc.perform(get("/api/v1/appointment/physician@gmail.com/04-04-2023/Accepted","physician@gmail.com","04-04-2023","Accepted")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].acceptance").value("Accepted"));
	
	}
	
	@Test
	public void getOnlyAcceptedAppointments() throws Exception{
		List<AppointmentDto> a = Arrays.asList(new AppointmentDto(1,"Cold","04/04/2023","Accepted",1,"physician@gmail.com","01/04/2023"));
		when(appointmentService.getOnlyAcceptedAppointments(any())).thenReturn(a);
		mockMvc.perform(get("/api/v1/appointment/Accepted","Accepted")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].acceptance").value("Accepted"));
	}
	
	
//	@Test
//	public void updateVisitById() throws Exception {
//		AppointmentDto v = new AppointmentDto(1,"Cold","04-04-2023","Accepted",1,"physician@gmail.com","01/04/2023");
//				AppointmentDto newVisit = new AppointmentDto(1,"Cold","04-04-2023","Accepted",1,"physician@gmail.com","01/04/2023");
//		when(appointmentService.updateAppointmentStatus(1, "Accepted")).thenReturn(newVisit);
//		this.mockMvc
//				.perform(put("/appointment/{id}/{status}").contentType(MediaType.APPLICATION_JSON)
//						.content(new ObjectMapper().writeValueAsString(newVisit)))
//				.andExpect(status().isAccepted()).andExpect(jsonPath("$.nurseEmail").value("nurse2@gmail.com"));
//
//	}

	
	
}
