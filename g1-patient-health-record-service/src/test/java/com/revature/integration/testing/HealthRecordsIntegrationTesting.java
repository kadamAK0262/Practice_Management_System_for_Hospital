package com.revature.integration.testing;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
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
import com.revature.G1HealthRecordsServiceApplication;
import com.revature.entity.Prescription;
import com.revature.entity.TestDetails;
import com.revature.entity.VisitDetails;
import com.revature.payload.PrescriptionDto;
import com.revature.payload.TestDto;
import com.revature.payload.VisitDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = G1HealthRecordsServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HealthRecordsIntegrationTesting {

	@Autowired
	Jackson2ObjectMapperBuilder mapperBuilder;
	
	@Autowired
	private ModelMapper modelMapper;

	@LocalServerPort
	private int port;
	TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
	
	@Test
	public void getVisitDetailsByVisitId() {
		String uri="http://localhost:" + port + "/api/v1/visitdetails/5";
		Map<String,Integer> pathVariable=new HashMap<>();
		pathVariable.put("visitId",5);
		HttpEntity entity = new HttpEntity<>(null, null);
		UriComponentsBuilder builder=UriComponentsBuilder.fromUriString(uri);
		ResponseEntity<String> res=restTemplate.exchange(builder.buildAndExpand(pathVariable).toUri(), HttpMethod.GET, entity, String.class);
		System.out.println(res.getBody());
		assertEquals(HttpStatus.OK, res.getStatusCode());
	}

	@Test
	public void testGetAllVisitsByPatientId() throws JSONException, JsonMappingException, JsonProcessingException {
		HttpEntity entity = new HttpEntity<>(null, headers);
		ResponseEntity<List<VisitDto>> response = restTemplate.exchange(createURLWithPort("/api/v1/patient/1/visits"),
				HttpMethod.GET, entity, new ParameterizedTypeReference<List<VisitDto>>() {
				});
		List<VisitDto> v = response.getBody();
		assertEquals(2, v.size());

	}

	@Test
	public void saveVisits() throws JsonMappingException, JsonProcessingException {
		VisitDto v = new VisitDto(9, 3, 150.0f, 70.0f, 100, 80, 120.0f, 80, "O+ve", "nurse3@gmail.com",
				"physician3@gmail.com", 9, "cold", 9);
		HttpEntity<VisitDto> entity = new HttpEntity<>(v, headers);
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/patient/3/visits"),
				HttpMethod.POST, entity, String.class);
		ObjectMapper mapper = mapperBuilder.build();
		VisitDto found = mapper.readValue(response.getBody(), VisitDto.class);
		Assertions.assertEquals("physician3@gmail.com", found.getPhysicianEmail());

	}

	@Test
	public void updateVisit() throws JsonMappingException, JsonProcessingException {
		String uri = "http://localhost:" + port + "/api/v1/visitdetails/3";
		VisitDto v = new VisitDto(4, 2, 150.0f, 70.0f, 100, 80, 120.0f, 80, "AB+ve", "nurse3@gmail.com",
				"physician3@gmail.com", 4, "cold", 4);
		Map<String, Integer> pathVariable = new HashMap<>();
		pathVariable.put("visitid", 4);
		HttpEntity<VisitDto> entity = new HttpEntity<VisitDto>(v, headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(uri);

		ResponseEntity<String> response = restTemplate.exchange(builder.buildAndExpand(pathVariable).toUri(),
				HttpMethod.PUT, entity, String.class);
		ObjectMapper mapper = mapperBuilder.build();
		VisitDto found = mapper.readValue(response.getBody(), VisitDto.class);
		Assertions.assertEquals("AB+ve", found.getBloodGroup());
	}
	
	@Test
	public void getVisitDetailsByAppointmentId() {
		String uri="http://localhost:" + port + "/api/v1/visit/4";
		Map<String,Integer> pathVariable=new HashMap<>();
		pathVariable.put("appointmentId",4);
		HttpEntity entity = new HttpEntity<>(null, null);
		UriComponentsBuilder builder=UriComponentsBuilder.fromUriString(uri);
		ResponseEntity<String> res=restTemplate.exchange(builder.buildAndExpand(pathVariable).toUri(), HttpMethod.GET, entity, String.class);
		System.out.println(res.getBody());
		assertEquals(HttpStatus.OK, res.getStatusCode());
	}
	
	//Get LastVisit by patient id
		@Test
		public void getLastVisitByPatientId() {
			String uri="http://localhost:" + port + "/api/v1/patient/3/lastvisit";
			Map<String,Integer> pathVariable=new HashMap<>();
			pathVariable.put("patientId",3);
			HttpEntity entity = new HttpEntity<>(null, null);
			UriComponentsBuilder builder=UriComponentsBuilder.fromUriString(uri);
			ResponseEntity<String> res=restTemplate.exchange(builder.buildAndExpand(pathVariable).toUri(), HttpMethod.GET, entity, String.class);
			System.out.println(res.getBody());
			assertEquals(HttpStatus.OK, res.getStatusCode());
		}

//////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testGetAllTestsByVisitId() throws JSONException, JsonMappingException, JsonProcessingException {
		HttpEntity entity = new HttpEntity<>(null, headers);
		ResponseEntity<List<TestDto>> response = restTemplate.exchange(
				createURLWithPort("/api/v1/tests/1"), HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<TestDto>>() {
				});
		List<TestDto> t = response.getBody();
		assertEquals(2, t.size());

	}

	@Test
	public void saveTests() throws JsonMappingException, JsonProcessingException {
		VisitDto visit = new VisitDto(7);
		List<TestDto> testDto = Arrays.asList(new TestDto(11, "Sugar test", "Negative", "Everything looks good", visit),
				new TestDto(12, "Blood test", "10 points", "fewer blood cells", visit));
		HttpEntity<List<TestDto>> entity = new HttpEntity<>(testDto, headers);
		ResponseEntity<List<TestDto>> response = restTemplate.exchange(createURLWithPort("/api/v1/visitdetails/tests"),
				HttpMethod.POST, entity, new ParameterizedTypeReference<List<TestDto>>() {
				});
		List<TestDto> v = response.getBody();
		Assertions.assertEquals(2, v.size());

	}

//	@Test
//	public void deleteTest() throws JsonMappingException, JsonProcessingException {
//		HttpEntity entity = new HttpEntity<>(null, headers);
//		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/testdetails/3"),
//				HttpMethod.DELETE, entity, String.class);
//		String found = response.getBody();
//		Assertions.assertEquals("Test deleted Successfully", found);
//
//	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void testGetAllPrescriptionByVisitId() throws JSONException, JsonMappingException, JsonProcessingException {
		HttpEntity entity = new HttpEntity<>(null, headers);
		ResponseEntity<List<PrescriptionDto>> response = restTemplate.exchange(
				createURLWithPort("/api/v1/prescriptions/1"), HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<PrescriptionDto>>() {
				});
		List<PrescriptionDto> p = response.getBody();
		assertEquals(2, p.size());

	}

	@Test
	public void savePrescription() throws JsonMappingException, JsonProcessingException {
		List<PrescriptionDto> prescriptionDto = Arrays.asList(
				new PrescriptionDto(12, "Dolo", "1-0-1", "After eat", new VisitDto(7)),
				new PrescriptionDto(13, "fortamet", "1-0-0", "Before eat", new VisitDto(7)));
		HttpEntity<List<PrescriptionDto>> entity = new HttpEntity<>(prescriptionDto, headers);
		ResponseEntity<List<PrescriptionDto>> response = restTemplate.exchange(createURLWithPort("/api/v1/visitdetails/prescription"),
				HttpMethod.POST, entity, new ParameterizedTypeReference<List<PrescriptionDto>>() {
				});
		List<PrescriptionDto> v = response.getBody();
		Assertions.assertEquals(2, v.size());
	}

//	@Test
//	public void deletePrescription() throws JsonMappingException, JsonProcessingException {
//		HttpEntity entity = new HttpEntity<>(null, headers);
//		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/prescription/3"),
//				HttpMethod.DELETE, entity, String.class);
//		String found = response.getBody();
//		Assertions.assertEquals("Prescription deleted Successfully", found);
//
//	}
}
