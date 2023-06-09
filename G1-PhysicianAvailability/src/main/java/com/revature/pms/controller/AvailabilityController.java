package com.revature.pms.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.revature.pms.entity.PhysicianAvailability;
import com.revature.pms.exceptions.ResourceNotFoundException;
import com.revature.pms.service.PhysicianAvailabiltiyService;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/v1/physician-availability")
@CrossOrigin(origins = "*")
public class AvailabilityController {
	@Autowired
	private PhysicianAvailabiltiyService service;

	@Autowired
	ObjectMapper objectMapper;
	
	private final AtomicLong counter = new AtomicLong();

	private final MeterRegistry registry;

	/**
	 * We inject the MeterRegistry into this class
	 */
	public AvailabilityController(MeterRegistry registry) {
		this.registry = registry;
	}
	
	@GetMapping("/physician")
	@Timed(value="getphysician.time",description="get physician details")
	public ResponseEntity<List<PhysicianAvailability>> getPhysiciansService() throws UnirestException {
		
		List<PhysicianAvailability> details = service.addPhysician(service.getPhysicianFromAuth());
		
		if(details==null || details.isEmpty())
		{
			throw new ResourceNotFoundException("details not found");
		}
		else
		{
			registry.counter("getphysician.counter").increment();
			return new ResponseEntity<List<PhysicianAvailability>>(details,
					HttpStatus.OK);
		}
	}

	@GetMapping("/physician-available/{page}/{size}/{available}")
	@Timed(value="getAvailablePhysician.time",description="get available physician details")
	public ResponseEntity<List<PhysicianAvailability>> getAvailablePhysiciansService(@PathVariable int page,@PathVariable int size, @PathVariable boolean available) {
		List<PhysicianAvailability> availabilities = service.getAvailablePhysicians(page,size,available);
		if (availabilities == null)
			return new ResponseEntity<List<PhysicianAvailability>>(HttpStatus.INTERNAL_SERVER_ERROR);
		else if (availabilities.isEmpty())
			return new ResponseEntity<List<PhysicianAvailability>>(HttpStatus.NO_CONTENT);
		else
			registry.counter("getphysician.counter").increment();
			return new ResponseEntity<List<PhysicianAvailability>>(availabilities, HttpStatus.OK);
	}

//	@PostMapping("/add-availability")
//	public ResponseEntity<PhysicianAvailability> addPhysicianAvailabilityService(
//			@RequestBody PhysicianAvailability availability) {
//		PhysicianAvailability available = service.addPhysicianAvailability(availability);
//		if (available == null)
//			return new ResponseEntity<PhysicianAvailability>(HttpStatus.INTERNAL_SERVER_ERROR);
//		return new ResponseEntity<PhysicianAvailability>(available, HttpStatus.CREATED);
//	}

	@GetMapping("/count")
	public long countPhysicianAvailability() {
		return service.count();
	}

	@PutMapping("/update-availability")
	@Transactional
	@Timed(value="updatePhysicianAvailability.time",description = "update physician availability")
	public ResponseEntity<PhysicianAvailability> updatePhysicianAvailabilityService(
			@RequestBody PhysicianAvailability availability) {
		service.schedulePhysician(availability.getStartDate(), availability.getEndDate(), availability.getEmail());
		registry.counter("updatePhysicianAvailability.counter").increment();
		return new ResponseEntity<PhysicianAvailability>(HttpStatus.ACCEPTED);
	}

	@PutMapping("/update-status")
	@Timed(value="updatestatus.time",description="update status")
	public ResponseEntity<PhysicianAvailability> update(@RequestBody PhysicianAvailability availability) {
		PhysicianAvailability avail = service.update(availability);
		registry.counter("updatestatus.counter").increment();
		return new ResponseEntity<PhysicianAvailability>(avail, HttpStatus.OK);
	}

	@DeleteMapping("/physician-available/{email}")
	@Transactional
	@Timed(value="Deletephysicianavailable.time",description="physician available through email")
	public ResponseEntity<String> getPhysicianAvailabilitServiceyById(@PathVariable String email) {
		String status = service.deletePhysicianAvailabilityById(email);
		if (Integer.parseInt(status) == 0)
			return new ResponseEntity<String>(status, HttpStatus.NOT_ACCEPTABLE);
		else
			registry.counter("physicianavailable.counter").increment();
			return new ResponseEntity<String>(status, HttpStatus.ACCEPTED);
	}

	@GetMapping("/physician-details/{email}")
	@Timed(value="getphysiciandetails.time",description="getting physician details through email")
	public PhysicianAvailability getPhysicianByEmail(@PathVariable String email) {
		registry.counter("getphysiciandetails.counter").increment();
		return service.getPhysicianByEmail(email);
	}
}
