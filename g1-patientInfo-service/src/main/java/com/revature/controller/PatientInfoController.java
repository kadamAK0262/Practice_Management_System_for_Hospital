package com.revature.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.entity.Patient;
import com.revature.entity.dto.PatientDto;
import com.revature.exception.ResourceNotFoundException;
import com.revature.service.PatientInfoService;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
//@EnableDiscoveryClient
@RequestMapping("/api/v1/patient-info")
@CrossOrigin(origins="*")
public class PatientInfoController {

	@Autowired
	PatientInfoService patientService;
	
	private final AtomicLong counter = new AtomicLong();
	private final MeterRegistry registry;
	/**
	 * We inject the MeterRegistry into this class
	 */
	public PatientInfoController(MeterRegistry registry) {
		this.registry = registry;
	}
	
	
	@GetMapping("/patient")
	@Timed(value="patientdetails.time",description="get patient details")
	public ResponseEntity<?> getPatients() {
		
		List<PatientDto> bpi=patientService.getAllPatients();		


		if (bpi.isEmpty() || bpi == null) {
			return new ResponseEntity<>("no data present", HttpStatus.BAD_REQUEST);
		} else {
			registry.counter("patientdetails.counter").increment();
			return new ResponseEntity<List<PatientDto>>(bpi, HttpStatus.OK);
		}
	}
	@GetMapping("/patient/{id}")
	@Timed(value="patientdetailsbyId", description = "patient details by Id")
	public ResponseEntity<?> getPatientById(@PathVariable("id") int id) {	
		
		try {
			PatientDto bpi = patientService.getPatientById(id);
			return new ResponseEntity<PatientDto>(bpi, HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			throw new ResourceNotFoundException("Patient not found for id " + id);
		}
		
	}
	@PutMapping("/patient/{id}")
	@Timed(value="updatepatientbyId",description="update patient details by Id")
	public ResponseEntity<PatientDto> putPatient(@PathVariable("id") int id,
			@RequestBody @Valid PatientDto patient ) {
		
//		return new ResponseEntity<>(patientService.updatePatientById(id, basic), HttpStatus.OK);
		try {
			registry.counter("updatepatientbyId.counter").increment();
			return new ResponseEntity<>(patientService.updatePatientById(id, patient), HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			throw new ResourceNotFoundException("Patient not found for id " + id);
		}
	}
	
	@GetMapping("/patient/count")
	public long getCount(){
		return patientService.getCount();
	}
	
}
