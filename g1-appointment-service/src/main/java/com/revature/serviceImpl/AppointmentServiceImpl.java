package com.revature.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.revature.dto.AppointmentDto;
import com.revature.entity.Appointment;
import com.revature.repository.AppointmentRepository;

import com.revature.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {
	@Autowired
	private AppointmentRepository appointmentRepository;
	@Autowired
	public ModelMapper modelMapper;


	@Override
	public AppointmentDto saveAppointment(AppointmentDto appointmentDto) {
		// TODO Auto-generated method stub
		Appointment appointment = modelMapper.map(appointmentDto, Appointment.class);
		Appointment savedAppointments = appointmentRepository.save(appointment);
		return modelMapper.map(savedAppointments, AppointmentDto.class);
	}

	@Override
	public List<AppointmentDto> getAcceptedAppointments(String physicianEmail, String date, String acceptance) {

		// TODO Auto-generated method stub
		List<Appointment> appointments = appointmentRepository.findByPhysicianEmailAndDateAndAcceptance(physicianEmail,
				date, acceptance);
		return appointments.stream().map(appointment -> modelMapper.map(appointment, AppointmentDto.class))
				.collect(Collectors.toList());
	}
	
	@Override
	public List<AppointmentDto> getOnlyAcceptedAppointments(String acceptance) {
		List<Appointment> appointments = appointmentRepository.findByAcceptance(acceptance);
		return appointments.stream().map(appointment -> modelMapper.map(appointment, AppointmentDto.class))
				.collect(Collectors.toList());
	}
	
	@Override
	public List<AppointmentDto> getAllAppointments(int patientId) {
		List<Appointment> appointments = appointmentRepository.findAllByPatientId(patientId);
		return appointments.stream().map(appointment -> modelMapper.map(appointment, AppointmentDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public AppointmentDto getAppointment(int appointmentId){
		return modelMapper.map(appointmentRepository.findById(appointmentId).get(),AppointmentDto.class);
	}
	
	@Override
	public List<AppointmentDto> getAppointmentByIdAndStatus(int id,String Status,int index,int size) {
	List<Appointment> appointments = appointmentRepository.findByPatientIdAndAcceptanceOrderByDateDesc(id, Status,PageRequest.of(index, size));
	return  appointments.stream().map(appointment-> modelMapper.map(appointment,AppointmentDto.class))
	 .collect(Collectors.toList());
	}
	
	@Override
	public AppointmentDto updateAppointmentStatus(int id,String status) {
		Appointment existing=appointmentRepository.findById(id).get();
		existing.setAcceptance(status);
		return modelMapper.map(appointmentRepository.saveAndFlush(existing), AppointmentDto.class);
	}
	
	
	public AppointmentDto getLastAppointmentByPatientIdAndAcceptance(int id ,String status) {
		return modelMapper.map(appointmentRepository.findTopByPatientIdAndAcceptanceOrderByDateDesc(id,status),AppointmentDto.class);
	}
	public long getAppointmentCount(String email) {
		return appointmentRepository.countAppointment(email);
	}
	
	public long getPendingAppointmentCount(String email) {
		return appointmentRepository.countPendingAppointment(email);
	}
	
	public long getAcceptedAppointmentCount(String email) {
		return appointmentRepository.countAcceptedAppointment(email);
	}
	
	public long  getCompletedAppointmentByPatientId(int id) {
		return appointmentRepository.countCompletedAppointmentByPatientId(id);
	}
	
	public long  getPendingAppointmentByPatientId(int id) {
		return appointmentRepository.countPendingAppointmentByPatientId(id);
	}
	
	public long countAllAcceptanceAppointment(String date) {
		return appointmentRepository.countAllAcceptanceAppointment(date);
	}
	public List<AppointmentDto>getAppointmentByPatientId(int id){
		return appointmentRepository.findByPatientId(id);
	}
	
	public List<AppointmentDto> getAppointments(String acceptence ,String date,int index,int size){
		 List<Appointment> appointments = appointmentRepository.findByAcceptanceAndDate(acceptence,date,PageRequest.of(index,size));
		 return appointments.stream().map(appointment->modelMapper.map(appointment, AppointmentDto.class))
				 .collect(Collectors.toList());			
	}
	
}
