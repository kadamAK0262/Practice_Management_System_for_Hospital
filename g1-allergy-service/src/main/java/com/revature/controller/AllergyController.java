package com.revature.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dto.AllergyDto;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.service.AllergyService;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.log4j.Log4j2;

@Log4j2
@CrossOrigin(origins="*")
@RestController
//@EnableDiscoveryClient
@RequestMapping("/api/v1/allergy-service")
public class AllergyController {
	@Autowired
	private AllergyService allergyService;
	
	private final AtomicLong counter = new AtomicLong();

	private final MeterRegistry registry;

	/**
	 * We inject the MeterRegistry into this class
	 */
	public AllergyController(MeterRegistry registry) {
		this.registry = registry;
	}

	@GetMapping("/allergy")
	@Timed(value="greeting.time", description = "time taken to retrieve allergies")
	public ResponseEntity<List<AllergyDto>> getAllAllergies() {
		List<AllergyDto> allergies = null;
		allergies = allergyService.allAllergies();
		
		if (allergies == null || allergies.isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
//		log.info("AllergyController getAll() response{}",allergies);
			registry.counter("greetings.counter").increment();
			return new ResponseEntity<>(allergies, HttpStatus.OK);
	}

	@GetMapping("/allergy/{id}")
	@Timed(value="greetingById.time",description="Time taken to retrieve allergy by Id")
	public ResponseEntity<AllergyDto> getAllergyUsingId(@PathVariable int id) {
		AllergyDto allergy = allergyService .getAllergyById(id);
		
		if (allergy == null)
			throw new ResourceNotFoundException("Patient not found for id " + id);
		else
//		log.info("AllergyController getAllergy() response{}",allergy);
			registry.counter("greetings.counter").increment();
			return new ResponseEntity<>(allergy, HttpStatus.OK);
	}
}
