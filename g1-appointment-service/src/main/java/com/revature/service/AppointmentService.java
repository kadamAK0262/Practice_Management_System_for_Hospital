package com.revature.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.revature.dto.AppointmentDto;
import com.revature.entity.Appointment;

public interface AppointmentService {

	public AppointmentDto saveAppointment(AppointmentDto appointmentDto);

	public List<AppointmentDto> getAcceptedAppointments(String physicianEmail, String acceptance, String date);
	
	public List<AppointmentDto> getOnlyAcceptedAppointments(String acceptance);
	
	public List<AppointmentDto> getAllAppointments(int patientId);
	
	
	
	public AppointmentDto getAppointment(int appointmentId);
	
	public List<AppointmentDto> getAppointmentByIdAndStatus(int id,String Status,int index,int size);
	
	public AppointmentDto updateAppointmentStatus(int id,String Status);
	
	
	public AppointmentDto getLastAppointmentByPatientIdAndAcceptance(int id ,String status);
	
	public long getAppointmentCount(String email) ;
	public long getAcceptedAppointmentCount(String email);
	
	public long getPendingAppointmentCount(String email);
	
	public long  getCompletedAppointmentByPatientId(int id);
	public long  getPendingAppointmentByPatientId(int id);
	
	public long countAllAcceptanceAppointment(String date);
	public List<AppointmentDto> getAppointmentByPatientId(int id);
	public List<AppointmentDto> getAppointments(String acceptence ,String date,int index,int size);
}
