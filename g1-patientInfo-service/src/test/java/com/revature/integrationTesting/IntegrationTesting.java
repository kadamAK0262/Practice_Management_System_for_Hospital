package com.revature.integrationTesting;



import java.util.List;

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
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.G1PatientInfoServiceApplication;
import com.revature.entity.dto.PatientDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = G1PatientInfoServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTesting {

	@LocalServerPort
	private int port;
	
	@Autowired
	Jackson2ObjectMapperBuilder mapperBuilder;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void getPatientByIdTest() throws JSONException, JsonMappingException, JsonProcessingException {
		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/patient/103"), HttpMethod.GET, entity,
				String.class);
		ObjectMapper mapper = mapperBuilder.build();
		PatientDto found=mapper.readValue(response.getBody(), PatientDto.class);
		Assertions.assertEquals(103, found.getPatientId());

	}
	
	@Test
	public void getAllPatientsTest() throws JSONException, JsonMappingException, JsonProcessingException {
		HttpEntity entity = new HttpEntity<>(null, headers);

		ResponseEntity<List<PatientDto>> response = restTemplate.exchange(createURLWithPort("/api/v1/patient"), HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<PatientDto>>() {});
		List<PatientDto> found= response.getBody();
		Assertions.assertEquals(5,found.size());
	}

	@Test
	public void putPatientTest() throws JsonMappingException, JsonProcessingException {
		PatientDto p=new PatientDto(1, "swapna@gmail.com", "Ms.", "Veera Swapna", "Raga", "19/09/2002", "1234567890", "sneha123",
				"female", "AndraPradesh");
		HttpEntity<PatientDto> entity = new HttpEntity<PatientDto>(p, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/patient/1"), HttpMethod.PUT, entity,String.class);
		ObjectMapper mapper = mapperBuilder.build();
		PatientDto found=mapper.readValue(response.getBody(), PatientDto.class);
		Assertions.assertEquals("Veera Swapna", found.getFirstName());

		
	}
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}
