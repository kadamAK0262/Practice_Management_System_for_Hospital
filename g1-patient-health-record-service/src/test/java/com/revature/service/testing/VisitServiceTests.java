package com.revature.service.testing;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.entity.VisitDetails;
import com.revature.payload.VisitDto;
import com.revature.repository.VisitRepository;
import com.revature.service.VisitService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class VisitServiceTests {

	@Autowired
	private VisitService visitService;

	@Autowired
	private ModelMapper modelMapper;

	@MockBean
	private VisitRepository visitRepo;

	@Test
	public void getAllVisitsTest() {
		
		  when(visitRepo.findAllByPatientId(1)).thenReturn(Stream.of(new VisitDetails(1,1,150.0f,70.0f,80,60,100.0f,90,"B+ve","nurse1@gmail.com","physician1@gmail.com",1,"high fever",1),new VisitDetails(2,1,150.0f,70.0f,100,80,120.0f,80,"O+ve","nurse2@gmail.com","physician2@gmail.com",2,"cold",2)).collect(Collectors.toList()));
		  assertEquals(2,visitService.getAllVisits(1).size());
		}

	@Test
	public void getVisityById() {
		Optional<VisitDetails> v = Optional.of(new VisitDetails(1, 1, 150.0f, 70.0f, 80, 60, 100.0f, 90, "B+ve",
				"nurse1@gmail.com", "physician1@gmail.com", 1, "high fever", 1));
		when(visitRepo.findById(1)).thenReturn(v);
		assertEquals(1, visitService.getVisit(1).getVisitId());
	}

	@Test
	public void updateVisitById() {
		Optional<VisitDetails> v = Optional.of(new VisitDetails(1, 1, 150.0f, 70.0f, 80, 60, 100.0f, 90, "B+ve",
				"nurse1@gmail.com", "physician1@gmail.com", 1, "high fever", 1));
		VisitDto newVisit = new VisitDto(1, 1, 150.0f, 70.0f, 100, 80, 120.0f, 80, "O+ve", "nurse2@gmail.com",
				"physician2@gmail.com", 1, "cold", 1);
		when(visitRepo.findById(1)).thenReturn(v);
		Assertions.assertEquals("O+ve", visitService.updateVisit(newVisit, 1).getBloodGroup());
		Assertions.assertEquals("physician2@gmail.com", visitService.updateVisit(newVisit, 1).getPhysicianEmail());
	}

	@Test
	public void saveVisitById() {
		VisitDto visit = new VisitDto(1, 1, 150.0f, 70.0f, 100, 80, 120.0f, 80, "O+ve", "nurse2@gmail.com",
				"physician2@gmail.com", 1, "cold", 1);
		VisitDetails visit1 = modelMapper.map(visit, VisitDetails.class);
		when(visitRepo.save(visit1)).thenReturn(visit1);
		Assertions.assertEquals("O+ve", visitService.saveVisit(1, visit).getBloodGroup());
		Assertions.assertEquals("physician2@gmail.com", visitService.saveVisit(1, visit).getPhysicianEmail());
	}

	@Test

	public void getVisitByAppointmentId() {
		VisitDetails v = new VisitDetails(1, 1, 150.0f, 70.0f, 80, 60, 100.0f, 90, "B+ve", "nurse1@gmail.com",
				"physician1@gmail.com", 1, "high fever", 1);
		when(visitRepo.findByAppointmentId(1)).thenReturn(v);
		assertEquals(1, visitService.getVisitByAppointmentId(1).getAppointmentId());
	}

	@Test
	public void getLastVisitByPatientId() {
		VisitDetails v = new VisitDetails(1, 2, 150.0f, 70.0f, 80, 60, 100.0f, 90, "B+ve", "nurse1@gmail.com",
				"physician1@gmail.com", 1, "high fever", 1);
		when(visitRepo.getLastVisitDetailsByPatientId(2)).thenReturn(v);
		assertEquals(2, visitService.getLastVisitDetailsByPatientId(2).getPatientId());
	}

}
