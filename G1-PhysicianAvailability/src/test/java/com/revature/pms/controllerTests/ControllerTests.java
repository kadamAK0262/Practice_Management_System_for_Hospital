package com.revature.pms.controllerTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.pms.controller.AvailabilityController;
import com.revature.pms.entity.PhysicianAvailability;
import com.revature.pms.service.PhysicianAvailabiltiyService;

@WebMvcTest(AvailabilityController.class)
public class ControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PhysicianAvailabiltiyService physicianService;

	@Test
	public void getPhysicianTest() throws Exception {
		List<PhysicianAvailability> p = Arrays.asList(new PhysicianAvailability("sam@gmail.com", "sam", "7/04/2023",
				"12/04/2023", true, "nureology", "physician"));
		when(physicianService.addPhysician(physicianService.getPhysicianFromAuth())).thenReturn(p);
		mockMvc.perform(get("http://localhost:9008/api/v1/physician").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].physicianName").value("sam"));
	}

//	@Test
//	public void getAvailablePhysicianTest() throws Exception {
//		List<PhysicianAvailability> p = Arrays.asList(new PhysicianAvailability("sam@gmail.com", "sam", "7/04/2023",
//				"12/04/2023", true, "nureology", "physician"));
//		when(physicianService.getAvailablePhysicians(any(Boolean.class))).thenReturn(p);
//		mockMvc.perform(
//				get("http://localhost:9008/api/v1/physician-available", true).param("available", new String("true")))
//				.andExpect(status().isOk()).andExpect(jsonPath("$[0].physicianName").value("sam"));
//	}

	@Test
	public void physicianCountTest() throws Exception {
		when(physicianService.count()).thenReturn((long) 0);
		mockMvc.perform(get("http://localhost:9008/api/v1/count"));
		
	}

	@Test
	public void updatePhysicianTest() throws JsonProcessingException, Exception {
		PhysicianAvailability p = new PhysicianAvailability("sam@gmail.com", "sam", "7/04/2023", "12/04/2023", true,
				"nureology", "physician");
		PhysicianAvailability p1 = new PhysicianAvailability("sam@gmail.com", "sam", "8/04/2023", "13/04/2023", true,
				"nureology", "physician");
		mockMvc.perform(put("http://localhost:9008/api/v1/update-availability", p1)
				.contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(p1)))
				.andExpect(status().isAccepted());
	}

	@Test
	public void updateStatusTest() throws JsonProcessingException, Exception {

		PhysicianAvailability p1 = new PhysicianAvailability("sam@gmail.com", "sam", "8/04/2023", "13/04/2023", true,
				"nureology", "physician");
		p1.setAvailable(false);
		when(physicianService.update(any(PhysicianAvailability.class))).thenReturn(p1);
		mockMvc.perform(put("http://localhost:9008/api/v1/update-status", p1).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(p1))).andExpect(status().isOk())
				.andExpect(jsonPath("$.available").value(false));
	}

	@Test
	public void deleteTest() throws Exception {
		when(physicianService.deletePhysicianAvailabilityById(any())).thenReturn("1");
		mockMvc.perform(delete("http://localhost:9008/api/v1/physician-available/sam@gmail.com","sam@gmail.com"))
		.andExpect(status().isAccepted());
	}

	@Test
	public void physicianByEmailTest() throws Exception {
		PhysicianAvailability p1 = new PhysicianAvailability("sam@gmail.com", "sam", "8/04/2023", "13/04/2023", true,
				"nureology", "physician");
		when(physicianService.getPhysicianByEmail(any())).thenReturn(p1);
		mockMvc.perform(get("http://localhost:9008/api/v1/physician-details/sam@gmail.com"));
	}
}
