package com.revature.integrationTests;

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
import com.revature.G1AllergyServiceApplication;
import com.revature.dto.AllergyDto;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = G1AllergyServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlllergyIntegrationTests {
	@LocalServerPort
	private int port;
	
	@Autowired
	Jackson2ObjectMapperBuilder mapperBuilder;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void getAllergytByIdTest() throws JSONException, JsonMappingException, JsonProcessingException {
		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/api/v1/allergy/2"), HttpMethod.GET, entity,
				String.class);
		ObjectMapper mapper = mapperBuilder.build();
		AllergyDto found=mapper.readValue(response.getBody(), AllergyDto.class);
		Assertions.assertEquals("Pet Allergy", found.getAllergyName());

	}
	
	@Test
	public void getAllAllergiesTest() throws JSONException, JsonMappingException, JsonProcessingException {
		HttpEntity entity = new HttpEntity<>(null, headers);

		ResponseEntity<List<AllergyDto>> response = restTemplate.exchange(createURLWithPort("/api/v1/allergy"), HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<AllergyDto>>() {});
		List<AllergyDto> found= response.getBody();
		Assertions.assertEquals(8,found.size());

	}
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}


