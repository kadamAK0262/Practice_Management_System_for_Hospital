package com.revature.controller;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.cloud.client.discovery.EnableDiscoveryClient;*/
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.entity.Email;
import com.revature.entity.dto.PatientDto;
import com.revature.service.EmailService;
import com.revature.service.LoginService;
import com.revature.service.RegisterService;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.transaction.Transactional;

@RestController
//@EnableDiscoveryClient
@CrossOrigin(origins="*") 
@RequestMapping("/api/v1/authentication-service")
public class AuthenticationController {
	
	@Autowired
	private RegisterService registerService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private LoginService loginService;
	
	private final AtomicLong counter = new AtomicLong();
	private final MeterRegistry registry;
	/**
	 * We inject the MeterRegistry into this class
	 */
	public AuthenticationController(MeterRegistry registry) {
		this.registry = registry;
	}
	
	@PostMapping("/patient/register")
	@Timed(value="registerPatient.time",description="new patient to register")
	public ResponseEntity<PatientDto> registerPatient(@RequestBody PatientDto patientDto){
		PatientDto patient=null;
		try {
			patient=registerService.registerService(patientDto);
		}
		catch (NullPointerException  e) {
			return new ResponseEntity<PatientDto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (NoSuchElementException e) {
			return new ResponseEntity<PatientDto>(HttpStatus.NO_CONTENT);
		}
		registry.counter("registerPatient.counter").increment();
		return new ResponseEntity<>(patient,HttpStatus.CREATED);
	}
	
	@PostMapping("/patient/login")
	@Timed(value="loginPatient.time",description="patient login")
	public ResponseEntity<PatientDto> loginPatient(@RequestParam("email") String email,
			@RequestParam("password") String password){
		System.out.println(email+" gcv"+password);
		PatientDto dto=null;
		try {
			
		dto = loginService.loginService(email,password);
		}
		catch (NullPointerException e) {
			return new ResponseEntity<PatientDto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch (NoSuchElementException e) {
			return new ResponseEntity<PatientDto>(HttpStatus.NO_CONTENT);
		}
		registry.counter("loginPatient.counter").increment();
		return new ResponseEntity<>(dto,HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/patient")
	@Transactional
	@Timed(value="updatePassword.time",description="patient password update")
	public void updateStatusById(@RequestParam("email") String email,
			@RequestParam("password") String password) {
		System.out.println(email+" "+password);
		loginService.updatepassword(email,password);
		registry.counter("updatePassword.counter").increment();
	}
	
	@PostMapping(value = "/sendemail")
	@Timed(value="sendEmail.time",description="email sending")
	public ResponseEntity<?> sendEmail(@RequestBody Email email){
		System.out.println(email);
		boolean result =this.emailService.sendEmail(email.getSubject(), email.getMessage(),email.getToMail());
		if(result) {
			registry.counter("sendEmail.counter").increment();
			return ResponseEntity.ok("Email is sent successfully.");
			
		}
		else {	
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email Not send");
		}
	}

}
