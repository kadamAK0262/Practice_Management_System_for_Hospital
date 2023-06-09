package com.revature.pms.serviceImplTest;

import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.pms.entity.PhysicianAvailability;
import com.revature.pms.repo.PhysicianAvailabilityRepo;
import com.revature.pms.service.PhysicianAvailabiltiyService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ServiceImplTest {

	@Autowired
	private PhysicianAvailabiltiyService service;

	@MockBean
	private PhysicianAvailabilityRepo repo;

	@Test
	public void getPhysiciansTest() {
		List<PhysicianAvailability> p = Arrays.asList(new PhysicianAvailability("sam@gmail.com", "sam", "7/04/2023",
				"12/04/2023", true, "nureology", "physician"));
		when(repo.findAll()).thenReturn(p);
		assertEquals(1, service.getPhysicians().size());
	}

	@Test
	public void countTest() {
		when(repo.count()).thenReturn((long) 0);
		assertEquals(0,service.count());
	}

//	@Test
//	public void getAvailablePhysiciansTest() {
//		List<PhysicianAvailability> p = Arrays.asList(new PhysicianAvailability("sam@gmail.com", "sam", "7/04/2023",
//				"12/04/2023", true, "nureology", "physician"));
//		when(repo.findByIsAvailable(true)).thenReturn(p);
//		assertEquals(1, service.getAvailablePhysicians(true).size());
//	}

	@Test
	public void addPhysicianTest() {
		List<PhysicianAvailability> p = Arrays.asList(new PhysicianAvailability("sam@gmail.com", "sam", "7/04/2023",
				"12/04/2023", true, "nureology", "physician"));
		for (PhysicianAvailability physicianAvailability : p) {
			if (!repo.existsById(physicianAvailability.getEmail())) {
				repo.save(physicianAvailability);
			}
		}
		when(repo.findAll()).thenReturn(p);
		assertEquals(1, service.addPhysician(p).size());

	}

	@Test
	public void schedulePhysicianTest() {
		PhysicianAvailability p = new PhysicianAvailability("sam@gmail.com", "sam", "7/04/2023", "12/04/2023", true,
				"nureology", "physician");
		p.setStartDate("8/04/2023");
		p.setEndDate("13/04/2023");
		service.schedulePhysician("8/04/2023", "13/04/2023", "sam@gmail.com");
		assertEquals("8/04/2023", p.getStartDate());
	}

	@Test
	public void updateTest() {
		PhysicianAvailability p1 = new PhysicianAvailability("sam@gmail.com", "sam", "8/04/2023", "13/04/2023", true,
				"nureology", "physician");
		when(repo.save(p1)).thenReturn(p1);
		assertEquals("8/04/2023", service.update(p1).getStartDate());
	}

	@Test
	public void deleteTest() {
		PhysicianAvailability p1 = new PhysicianAvailability("sam@gmail.com", "sam", "8/04/2023", "13/04/2023", true,
				"nureology", "physician");
		when(repo.deleteByEmail("sam@gmail.com")).thenReturn("deleted");
		assertEquals("deleted", service.deletePhysicianAvailabilityById("sam@gmail.com"));
	}

	@Test
	public void findByEmailTest() {
		PhysicianAvailability p1 = new PhysicianAvailability("sam@gmail.com", "sam", "8/04/2023", "13/04/2023", true,
				"nureology", "physician");
		when(repo.findByEmail("sam@gmail.com")).thenReturn(p1);
		assertEquals("sam@gmail.com", service.getPhysicianByEmail("sam@gmail.com").getEmail());
	}
}
