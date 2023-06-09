package com.revature.controller;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

import javax.imageio.spi.RegisterableService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.revature.entity.Nurse;
import com.revature.entity.Prescription;
import com.revature.entity.TestDetails;
import com.revature.entity.VisitDetails;
//import com.revature.entity.dto.PatientDto;
import com.revature.exception.HealthServiceException;
import com.revature.exception.ResourceNotFoundException;
import com.revature.payload.PrescriptionDto;
import com.revature.payload.TestDto;
import com.revature.payload.VisitDto;
import com.revature.service.NurseService;
import com.revature.service.PrescriptionService;
import com.revature.service.TestService;
import com.revature.service.VisitService;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/health-record")
public class HealthRecordsController {

	@Autowired(required = false)
	private VisitService visitService;

	@Autowired(required = false)
	private TestService testService;
	
	@Autowired(required = false)
	private NurseService nurseService;

	@Autowired(required = false)
	private PrescriptionService prescriptionService;
	
	

	private final AtomicLong counter = new AtomicLong();
	private final MeterRegistry registry;
	/**
	 * We inject the MeterRegistry into this class
	 */
	public HealthRecordsController(MeterRegistry registry) {
		this.registry = registry;
	}
	
	
	@GetMapping("/nurse")
	@Timed(value="getNurseDetails.time",description="gets all the nurse details")
	public ResponseEntity<List<Nurse>> getAllNurse() {
		try {
			registry.counter("getNurseDetails.counter").increment();
			return new ResponseEntity<List<Nurse>>(nurseService.addNurse(nurseService.getNurseFromAuth()),
					HttpStatus.OK);
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return new ResponseEntity<List<Nurse>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// get visits details
	@GetMapping("/patient/{patientId}/visits")
	@Timed(value="getAllVisitByPatientId.time",description = "gets all visit details with patientId")
	public ResponseEntity<?> getAllVisits(@PathVariable(name = "patientId") int patientId) {
		
		List<VisitDto> visitDetails=visitService.getAllVisits(patientId);
		
		if(visitDetails==null || visitDetails.isEmpty())
		{
			throw new ResourceNotFoundException("Patient details not found for id " + patientId);
		}
		else
		{
			registry.counter("getAllVisitByPatientId.counter").increment();
			return new ResponseEntity<List<VisitDto>>(visitDetails,HttpStatus.OK);
		}
	
	}

	// save visits
	@PostMapping("/patient/{patientId}/visits")
	@Timed(value="saveVisitById.time",description = "save visit details by patientId")
	public ResponseEntity<VisitDto> saveVisit(@PathVariable(name = "patientId") int patientId,
			@RequestBody VisitDto visitDto) {
		VisitDto dto=visitService.saveVisit(patientId, visitDto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{patientId}")
				.buildAndExpand(dto.getAllergyId()).toUri();
		registry.counter("saveVisitById.counter").increment();
		return ResponseEntity.created(location).body(dto);
	}

	// update visit
	@PutMapping("/visitdetails/{visitid}")
	@Timed(value="updateVisitById.time",description="update visit details by PatientId")
	public ResponseEntity<VisitDto> updateAppointment(@RequestBody VisitDto visitDto,
			@PathVariable(name = "visitid") int visitId) {
		registry.counter("updateVisitById.counter").increment();
		return new ResponseEntity<>(visitService.updateVisit(visitDto, visitId), HttpStatus.ACCEPTED);
	}

	// get all test details
	@GetMapping("/tests/{visitId}")
	@Timed(value="getTestDetailsByVisitId.time",description="gets test details by Id")
	public ResponseEntity<?> getTestDetails(@PathVariable(name = "visitId") int visitId) {
		
		List<TestDto> tests=testService.getTestDetails(visitId);
		if(tests==null || tests.isEmpty())
		{
			
			throw new ResourceNotFoundException("visit details not found for id " + visitId);
		}
		else {
			registry.counter("getTestDetailsByVisitId.counter").increment();
		return new ResponseEntity<List<TestDto>>(tests,HttpStatus.OK);
		}

	}

	// save tests
	@PostMapping("/visitdetails/tests")
	@Timed(value="savetests.time",description="save test details")
	public ResponseEntity<List<TestDto>> saveTest(@RequestBody List<TestDto> testDto) {
		registry.counter("savetests.counter").increment();
		return new ResponseEntity<>(testService.saveTest(testDto), HttpStatus.CREATED);
	}

//	// delete test
//	@DeleteMapping("/testdetails/{testid}")
//	public ResponseEntity<String> deleteTest(@PathVariable(name = "testid") int testId) {
//		testService.deleteTest(testId);
//		return new ResponseEntity<>("Test deleted Successfully", HttpStatus.ACCEPTED);
//	}

	// get all prescription details
	@GetMapping("/prescriptions/{visitId}")
	@Timed(value="prescriptionsByVisitId.time",description="prescription details by visit id")
	public ResponseEntity<?> getPrescriptionDetails(@PathVariable(name = "visitId") int visitId) {
		List<PrescriptionDto> list=prescriptionService.getPrescriptionDetails(visitId);
		if(list==null || list.isEmpty())
		{
			throw new ResourceNotFoundException("prescription details not found for id " + visitId);
		}
		else {
			registry.counter("prescriptionsByVisitId.counter").increment();
			return new ResponseEntity<List<PrescriptionDto>>(list,HttpStatus.OK);
		}

	}

	// save prescription
	@PostMapping("/visitdetails/prescription")
	@Timed(value="savePrescription.time",description="save prescription details")
	public ResponseEntity<List<PrescriptionDto>> savePrescription(
			@RequestBody List<PrescriptionDto> prescriptionDto) {
		registry.counter("savePrescription.counter").increment();
		return new ResponseEntity<>(prescriptionService.savePrescription(prescriptionDto), HttpStatus.CREATED);
	}

//	 delete prescription
//	@DeleteMapping("/prescription/{prescriptionid}")
//	public ResponseEntity<String> deletePrescription(@PathVariable(name = "prescriptionid") int prescriptionId) {
//		prescriptionService.deletePrescription(prescriptionId);
//		return new ResponseEntity<>("Prescription deleted Successfully", HttpStatus.ACCEPTED);
//	}

	@GetMapping("/visitdetails/"
			+ "{visitId}")
	@Timed(value="getVisitDetailsByVisitId.time",description="gets visit details by visit id")
	public ResponseEntity<?> getVisit(@PathVariable(name = "visitId") int visitId) {
		VisitDetails visit=visitService.getVisit(visitId);
		if(visit==null)
		{	
			throw new ResourceNotFoundException("visit details not found for id " + visitId);
		}
		else
		{
			registry.counter("getVisitDetailsByVisitId.counter").increment();
			return new ResponseEntity<VisitDetails>(visit,HttpStatus.OK);
		}
			
	}

	@GetMapping("/patient/{patientId}/lastvisit")
	@Timed(value="getLastVisitDetails.time",description = "gets last visit details")
	public ResponseEntity<?> getLastVisitDetails(@PathVariable(name="patientId")int patientId){
		VisitDetails visit=visitService.getLastVisitDetailsByPatientId(patientId);
		if(visit==null)
		{	
			throw new ResourceNotFoundException("visit details not found for id " + patientId);
		}
		else
			registry.counter("getLastVisitDetails.counter").increment();
			return new ResponseEntity<VisitDetails>(visit,HttpStatus.OK);
	}
	
	
	
	@GetMapping("/visit/{appointmentId}")
	@Timed(value="getVisitByAppointment.time",description="gets visit details by appointment Id")
	public ResponseEntity<?> getVisitByAppointment(@PathVariable int appointmentId) throws HealthServiceException {
		VisitDetails visit=visitService.getVisitByAppointmentId(appointmentId);
		if(visit==null)
		{	
			throw new ResourceNotFoundException("Appointment details not found for id " + appointmentId);
			}
		else
			registry.counter("getVisitByAppointment.counter").increment();
			return new ResponseEntity<VisitDetails>(visit,HttpStatus.OK);
}
	
	@GetMapping("nurse/count")
	public long  getNurseCount() {
		return  nurseService.getCount();
	}
	
}
