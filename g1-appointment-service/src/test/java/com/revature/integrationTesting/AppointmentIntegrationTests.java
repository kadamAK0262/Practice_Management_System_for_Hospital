package com.revature.integrationTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.G1AppointmentServiceApplication;
import com.revature.dto.AppointmentDto;
import com.revature.entity.Appointment;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = G1AppointmentServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppointmentIntegrationTests {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	Jackson2ObjectMapperBuilder mapperBuilder;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();
	
//	@Test
//  public void addAppointment() throws JsonMappingException, JsonProcessingException {
//		AppointmentDto a = new AppointmentDto(3,"fever", "12-10-2022", "accepted", 3, "sam@gmail.com", "11-12-2022");
//      HttpEntity<AppointmentDto> entity = new HttpEntity<>(a, headers);
//      ResponseEntity<String> response = restTemplate.exchange(
//              createURLWithPort("/api/v1/appointment"),
//              HttpMethod.POST, entity, String.class);
//      ObjectMapper mapper=mapperBuilder.build();
//      AppointmentDto found=mapper.readValue(response.getBody(), AppointmentDto.class);
//      Assertions.assertEquals("sam@gmail.com", found.getPhysicianEmail());
//
//  }
//	@Test
//	public void testGetPatientIdByAppointment() throws JSONException, JsonMappingException, JsonProcessingException {
//		HttpEntity entity = new HttpEntity<>(null, headers);
//		ResponseEntity<List<AppointmentDto>> response = restTemplate.exchange(createURLWithPort("/api/v1/patient-appointment/3"),
//				HttpMethod.GET, entity, new ParameterizedTypeReference<List<AppointmentDto>>() {});
//		List<AppointmentDto> a = response.getBody();
//		assertEquals(2, a.size());
//
//	}

//	@Test
//	public void testAppointmentById() throws JSONException, JsonMappingException, JsonProcessingException {
//
//		HttpEntity<String> entity = new HttpEntity<>(null, headers);
//
//		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/appointment/1"),
//				HttpMethod.GET, entity, String.class);		
//		ObjectMapper mapper = mapperBuilder.build();
//		Appointment a = mapper.readValue(response.getBody(), Appointment.class);
//		Assertions.assertEquals(0, a.getId());
//	}
//	@Test
//	public void getAppointmentByIdAndStatus() throws JSONException, JsonMappingException, JsonProcessingException {
//
//		HttpEntity<String> entity = new HttpEntity<>(null, headers);
//
//		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/appointment/2/accepted"),
//				HttpMethod.GET, entity, String.class);		
//		ObjectMapper mapper = mapperBuilder.build();
//		Appointment a = mapper.readValue(response.getBody(), Appointment.class);
//		Assertions.assertEquals("accepted", a.getAcceptance());
//	}

//	 @Test
//		public void updateAppointmentIdAndStatus() throws JsonMappingException, JsonProcessingException {
//			String uri="http://localhost:" + port + "/api/v1/appointment/1/pending";
//			AppointmentDto a = new AppointmentDto(1,"fever", "12-10-2022", "pending", 2, "james@gmail.com", "21-12-2022");
//			Map<String,Integer> pathVariable=new HashMap<>();
//			pathVariable.put("id", 1);
//			Map<String,String> pathVariable1=new HashMap<>();
//			
//			pathVariable1.put("acceptance", "accepted");
//			HttpEntity<AppointmentDto> entity = new HttpEntity<AppointmentDto>(a, headers);
//			UriComponentsBuilder builder=UriComponentsBuilder.fromUriString(uri);
//			ResponseEntity<String> response = restTemplate.exchange(builder.buildAndExpand(pathVariable1).toUri(), HttpMethod.PUT, entity,String.class);
//			ObjectMapper mapper = mapperBuilder.build();
//			AppointmentDto found=mapper.readValue(response.getBody(), AppointmentDto.class);
//			Assertions.assertEquals("pending", found.getAcceptance());
//		}
	
	@Test
	public void testAppointmentByAcceptance() {
		String uri="http://localhost:" + port + "/api/v1/appointment/sam@gmail.com/12-10-2022/accepted";
		Map<String,String> pathVariable=new HashMap<>();
		pathVariable.put("physicianEmail", "sam@gmail.com");
		pathVariable.put("date", "12-10-2022");
		pathVariable.put("status","accepted");
		HttpEntity entity = new HttpEntity<>(null, null);
		UriComponentsBuilder builder=UriComponentsBuilder.fromUriString(uri);
		ResponseEntity<String> res=restTemplate.exchange(builder.buildAndExpand(pathVariable).toUri(), HttpMethod.GET, entity, String.class);
		System.out.println(res.getBody());
		assertEquals(HttpStatus.OK, res.getStatusCode());
	}
	
//	@Test
//	public void testAcceptedAppointments() {
//		String uri="http://localhost:" + port + "/api/v1/appointment/accepted";
//		Map<String,String> pathVariable=new HashMap<>();
//		pathVariable.put("status","accepted");
//		HttpEntity entity = new HttpEntity<>(null, null);
//		UriComponentsBuilder builder=UriComponentsBuilder.fromUriString(uri);
//		ResponseEntity<String> res=restTemplate.exchange(builder.buildAndExpand(pathVariable).toUri(), HttpMethod.GET, entity, String.class);
//		System.out.println(res.getBody());
//		assertEquals(HttpStatus.OK, res.getStatusCode());
//	}
//
//	
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}
