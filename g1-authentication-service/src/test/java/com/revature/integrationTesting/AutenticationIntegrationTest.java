package com.revature.integrationTesting;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.G1AuthenticationServiceApplication;
import com.revature.entity.dto.PatientDto;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = G1AuthenticationServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AutenticationIntegrationTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	Jackson2ObjectMapperBuilder mapperBuilder;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();
	
	@Test
	public void regsiterPatientTest() throws JsonMappingException, JsonProcessingException {
		PatientDto p=new PatientDto(123, "rk@gmail.com", "Mr.", "Rk", "Kukretti", "19/09/2002", "1234567890", "Aman123",
				"male", "AndraPradesh");
		HttpEntity<PatientDto> entity = new HttpEntity<PatientDto>(p, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/patient/register"), HttpMethod.POST, entity,String.class);
		
		ObjectMapper mapper = mapperBuilder.build();
		PatientDto found=mapper.readValue(response.getBody(), PatientDto.class);
		Assertions.assertEquals("Rk", found.getFirstName());

		
	}
	@Test
	public void loginPatientTest() throws JsonMappingException, JsonProcessingException {
		
		String url="http://localhost:"+port+"/api/v1/patient/login";
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
		        .queryParam("email", "sriramaleti123@gmail.com")
		        .queryParam("password", "1234");
		        
		
		HttpEntity<PatientDto> entity = new HttpEntity<PatientDto>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, entity,String.class);
		ObjectMapper mapper = mapperBuilder.build();
		PatientDto found=mapper.readValue(response.getBody(), PatientDto.class);
		Assertions.assertEquals("Sriram", found.getFirstName());

		
	}
	
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
