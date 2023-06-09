package com.revature.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.revature.entity.VisitDetails;
import com.revature.payload.VisitDto;
import com.revature.repository.VisitRepository;
import com.revature.service.VisitService;

@Service
@Component
public class VisitServiceImpl implements VisitService {
	
	@Autowired
	private VisitRepository visitRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public VisitDto saveVisit(int patientId, VisitDto visitDto) {
//		Patient p = patientRepository.findById(patientId)
//				.orElseThrow(() -> new PatientNotFound(String.format("Patient id %d not found", patientId)));
		VisitDetails visit = modelMapper.map(visitDto, VisitDetails.class);
		visit.setPatientId(patientId);
		VisitDetails savedVisit = visitRepository.save(visit);
		return modelMapper.map(savedVisit, VisitDto.class);
	}

	@Override
	public List<VisitDto> getAllVisits(int patientId) {
//		Patient p = patientRepository.findById(patientId)
//				.orElseThrow(() -> new PatientNotFound(String.format("Patient id %d not found", patientId)));
		List<VisitDetails> visits = visitRepository.findAllByPatientId(patientId);
		return visits.stream().map(visit -> modelMapper.map(visit, VisitDto.class)).collect(Collectors.toList());
	}
	
	@Override
	public VisitDto updateVisit(VisitDto visitDto, int visitId) {
		VisitDetails v = visitRepository.findById(visitId).get();
		v.setBloodGroup(visitDto.getBloodGroup());
		v.setBodyTemparature(visitDto.getBodyTemparature());
		v.setKeyNotes(visitDto.getKeyNotes());
		v.setHeight(visitDto.getHeight());
		v.setWeight(visitDto.getWeight());
		v.setBpSystolic(visitDto.getBpSystolic());
		v.setBpDiastolic(visitDto.getBpDiastolic());
		v.setRespirationRate(visitDto.getRespirationRate());
		v.setNurseEmail(visitDto.getNurseEmail());
		v.setPhysicianEmail(visitDto.getPhysicianEmail());
		visitRepository.saveAndFlush(v);
		return modelMapper.map(v, VisitDto.class);
	}

	@Override
	public VisitDetails getVisit(int visitId) {
		return visitRepository.findById(visitId).get();	 
	}

	@Override
	public VisitDetails getLastVisitDetailsByPatientId(int patientId) {
		return visitRepository.getLastVisitDetailsByPatientId(patientId);
	}
	
	@Override
	public VisitDetails getVisitByAppointmentId(int id){
		VisitDetails visit=visitRepository.findByAppointmentId(id);
		return visit;
	}


}

