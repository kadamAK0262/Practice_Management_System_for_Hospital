package com.revature.serviceImplTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
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

import com.revature.dto.AppointmentDto;
import com.revature.entity.Appointment;
import com.revature.repository.AppointmentRepository;
import com.revature.serviceImpl.AppointmentServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AppointmentServiceImplTest {

	@Autowired
	private AppointmentServiceImpl appointmentService;

	@MockBean
	private AppointmentRepository appointmentRepo;

	@Autowired
	private ModelMapper modelMapper;
	
//	@Test
//	public void saveAppointment() {
//		Appointment a = Appointment.builder()
//                .id(1)
//                .reason("Fadatare")
//                .date("12/12/2023")
//                .acceptance("accepatnce")
//                .patientId(1).physicianEmail("sam@gmail.com").submissionDate("12/3/2022")
//                .build();
//		appointmentRepo.save(a);
//        assertThat(a.getId()).isGreaterThan(0);
//	}
//	
//	@Test
//	public void getAcceptedAppointments() {
//		when(appointmentRepo.findByPhysicianEmailAndDateAndAcceptance("physician@gmail.com", "04/04/2023", "Accepted")).thenReturn(Stream.of(new Appointment(10,"Cold","04/04/2023","Accepted",1,
//				"physician@gmail.com","01/04/2023")).collect(Collectors.toList()));
//		assertEquals(1,appointmentService.getAcceptedAppointments("physician@gmail.com","04/04/2023", "Accepted").size());
//	}
//	
//	@Test
//	public void getOnlyAcceptedAppointments() {
//		when(appointmentRepo.findByAcceptance("Accepted")).thenReturn(Stream.of(new Appointment(10,"Cold","04/04/2023","Accepted",1,
//				"physician@gmail.com","01/04/2023")).collect(Collectors.toList()));
//		assertEquals(1,appointmentService.getOnlyAcceptedAppointments("Accepted").size());
//		
//	}
//	
//	@Test
//	public void getAllAppointment() {
//		when(appointmentRepo.findAllByPatientId(1)).thenReturn(Stream.of(new Appointment(1,"Cold","04/04/2023","Accepted",1,
//				"physician@gmail.com","01/04/2023")).collect(Collectors.toList()));
//		assertEquals(1,appointmentService.getAllAppointments(1).size());		
//	}
	
	@Test
	public void getAppointmentById() {
		AppointmentDto a=new AppointmentDto(1,"Cold","04/04/2023","Accepted",1,
				"physician@gmail.com","01/04/2023");
		when(appointmentRepo.findById(1)).thenReturn(Optional.ofNullable(modelMapper.map(a, Appointment.class)));
		assertEquals(1,appointmentService.getAppointment(1).getId());
	}
	//checking------
//	@Test
//	public void getAppointmentByIdAndStatus() {
//		AppointmentDto a=new AppointmentDto(1,"Cold","04/04/2023","Accepted",1,
//				"physician@gmail.com","01/04/2023");
//		when(appointmentRepo.findByIdAndAcceptance(1, "Accepted")).thenReturn(modelMapper.map(a, Appointment.class));
//		assertEquals("Accepted",appointmentService.getAppointmentByIdAndStatus(1, "Accepted").getAcceptance());
//	}
	@Test
	public void updateAppointmentIdAndStatus() {
		String acceptance="Accepted";
		Appointment a=new Appointment(1,"Cold","04/04/2023","acceptance",1,
				"physician@gmail.com","01/04/2023");
		a.setId(2);
		appointmentRepo.saveAndFlush(a);
		Appointment a1=appointmentRepo.findById(1).get();
		assertThat(a1.getId()).isEqualTo(1);
	}
}
