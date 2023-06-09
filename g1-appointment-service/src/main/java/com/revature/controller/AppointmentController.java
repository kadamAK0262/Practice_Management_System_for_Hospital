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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.revature.dto.AppointmentDto;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.service.AppointmentService;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*")
//@EnableDiscoveryClient
@RequestMapping("/api/v1/appointment-service")
public class AppointmentController {

	@Autowired	
	private AppointmentService appointmentService;

	
	private final AtomicLong counter = new AtomicLong();

	private final MeterRegistry registry;

	/**
	 * We inject the MeterRegistry into this class
	 */
	public AppointmentController(MeterRegistry registry) {
		this.registry = registry;
	}
	
	@PostMapping("/appointment")
	@Timed(value="saveAppointment.time",description = "saving the appointment details")
	public ResponseEntity<AppointmentDto> saveAppointment(@RequestBody AppointmentDto appointmentDto) {
		registry.counter("saveAppointment.counter").increment();
		return new ResponseEntity<>(appointmentService.saveAppointment(appointmentDto), HttpStatus.CREATED);
	}

	@GetMapping("/appointments/{physicianEmail}/{date}/{acceptance}")
	@Timed(value="getAcceptedAppointments.time",description = "getting the accepted appointments")
	public ResponseEntity<List<AppointmentDto>> getAcceptedAppointments(
			@PathVariable(name = "physicianEmail") String physicianEmail, @PathVariable(name = "date") String date,
			@PathVariable(name = "acceptance") String acceptance) {
		List<AppointmentDto> details= appointmentService.getAcceptedAppointments(physicianEmail, date, acceptance);
		if(details== null|| details.isEmpty()) {
			return new ResponseEntity<List<AppointmentDto>>(HttpStatus.NO_CONTENT);
		}
		else
		{
			registry.counter("getAcceptedAppointments.counter").increment();
			return new ResponseEntity<>(details, HttpStatus.OK);
		}
	}

	@GetMapping("/appointments/{status}")
	@Timed(value="getAppointmentsByStatus.time",description = "getting appointment details by status")
	public ResponseEntity<List<AppointmentDto>> getOnlyAcceptedAppointments(
			@PathVariable(name = "status") String acceptance) {

		List<AppointmentDto> details= appointmentService.getOnlyAcceptedAppointments(acceptance);
		if(details== null|| details.isEmpty()) {
			throw new ResourceNotFoundException("details not found with"+acceptance+" ");
		}
		else
		{
			registry.counter("getAppointmentsByStatus.counter").increment();
			return new ResponseEntity<>(details, HttpStatus.OK);
		}
	}

	@GetMapping("/patient-appointment/{id}")
	@Timed(value="getPatientAppointmentsById.time",description="getting patient appointment details by Id")
	public ResponseEntity<List<AppointmentDto>> getAllAppointments(@PathVariable(name = "id") int patientId) {
		
		List<AppointmentDto> details= appointmentService.getAllAppointments(patientId);
		if(details== null|| details.isEmpty()) {
			throw new ResourceNotFoundException("details not found with id "+patientId+" ");
		}
		else
		{
			registry.counter("getPatientAppointmentsById.counter").increment();
			return new ResponseEntity<>(details, HttpStatus.OK);
		}
		
//		return new ResponseEntity<>(appointmentService.getAllAppointments(patientId), HttpStatus.OK);
	}

	@GetMapping("/appointment/{id}")
	@Timed(value="getAppointmentId.time",description = "get appointment details by id")
	public ResponseEntity<AppointmentDto> getAppointment(@PathVariable(name = "id") int appointmentId) {
		
		AppointmentDto details= appointmentService.getAppointment(appointmentId);
		if(details== null) {
			throw new ResourceNotFoundException("details not found with id "+appointmentId+" ");
		}
		else
		{
			registry.counter("getAppointmentId.counter").increment();
			return new ResponseEntity<>(details, HttpStatus.OK);
		}
		
//		return new ResponseEntity<>(appointmentService.getAppointment(appointmentId), HttpStatus.OK);
	}

	@GetMapping("/appointment/{id}/{status}/{index}/{size}")
	@Timed(value="getAppointmentIdStatusIndex.time",description = "appointment details with pagination")
	public ResponseEntity<List<AppointmentDto>> getAppointmentByStatus(@PathVariable(name = "id") int id,
			@PathVariable(name = "status") String status,@PathVariable(name = "index") int index,@PathVariable(name = "size") int size) {
		List<AppointmentDto> details= (List<AppointmentDto>) appointmentService.getAppointmentByIdAndStatus(id, status,index,size);
		
		if(details== null|| details.isEmpty()) {
//			throw new ResourceNotFoundException("details not found with status "+ status);
			return new ResponseEntity<List<AppointmentDto>>(HttpStatus.NO_CONTENT);
		}
		else
		{
			registry.counter("getAppointmentIdStatusIndex.counter").increment();
			return new ResponseEntity<>(details, HttpStatus.OK);
		}
		
	}

	@PutMapping("/appointment/{id}/{status}")
	@Timed(value="updateAppointmentByStatus.time",description = "updating appointment status with id")
	@Transactional
	public ResponseEntity<AppointmentDto> updateAppointmentStatus(@PathVariable(name="id") int id,@PathVariable(name="status") String status){
		registry.counter("updateAppointmentByStatus.counter").increment();
		return new ResponseEntity<>(appointmentService.updateAppointmentStatus(id, status),HttpStatus.OK);
	}
	
	
	
	@GetMapping("/indexed-appointments/{status}/{date}/{index}/{size}")
	@Timed(value="getIndexedAppointmentsDateIndex.time",description = "getting Indexed Appointments with pagination")
	public List<AppointmentDto> patientsByStatus(@PathVariable String status,@PathVariable int index,@PathVariable int size,@PathVariable String date){
	
		registry.counter("getIndexedAppointmentsDateIndex.counter").increment();
		return appointmentService.getAppointments(status,date,index,size);		
	}
	
	@GetMapping("last/appointment/{patientId}/{status}")
	@Timed(value="getLastAppointmentsPatientIdStatus.time",description = "getting last Appointment details of patient")
	public AppointmentDto getLastAppointmentByPatientIdAndAcceptance(@PathVariable int patientId,@PathVariable String status) {
		registry.counter("getLastAppointmentsPatientIdStatus.counter").increment();
		return appointmentService.getLastAppointmentByPatientIdAndAcceptance(patientId, status);
	}
	
	@GetMapping("/appointment-count/{email}")
	public long getAppointmentCount(@PathVariable String email) {
		return appointmentService.getAppointmentCount(email);
	}
	
	@GetMapping("/pending-count/{email}")
	public long getPendingAppointmentCount(@PathVariable String email) {
		return appointmentService.getPendingAppointmentCount(email);
	}
	
	@GetMapping("/accepted-count/{email}")
	public long getAcceptedAppointmentCount(@PathVariable String email) {
		return appointmentService.getAcceptedAppointmentCount(email);
	}
	
	@GetMapping("/completed/appointment/patient/{id}")
	public long getCompletedAppointmentCountByPatientId(@PathVariable int id) {
		return appointmentService.getCompletedAppointmentByPatientId(id);
	}
	
	@GetMapping("pending/appointment/patient/{id}")
	public long getPendingAppointmentCountByPatientId(@PathVariable int id) {
		return appointmentService.getPendingAppointmentByPatientId(id);
	}
	
	@GetMapping("acceptance/appointment/count/{date}")
	public long  getAllAcceptanceAppointment(@PathVariable String date) {
		return appointmentService.countAllAcceptanceAppointment(date);
	}
	
	
} 
