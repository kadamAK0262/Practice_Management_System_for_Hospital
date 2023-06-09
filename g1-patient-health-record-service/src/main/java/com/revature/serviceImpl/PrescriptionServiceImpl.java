package com.revature.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.entity.Prescription;
import com.revature.entity.VisitDetails;
import com.revature.exception.VisitNotFound;
import com.revature.payload.PrescriptionDto;
import com.revature.repository.PrescriptionRepository;
import com.revature.repository.VisitRepository;
import com.revature.service.PrescriptionService;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

	@Autowired
	private PrescriptionRepository prescriptionRepository;

	@Autowired
	private VisitRepository visitRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<PrescriptionDto> getPrescriptionDetails(int visitId) {
		Optional<VisitDetails> v = Optional.of(visitRepository.findById(visitId)
				.orElseThrow(() -> new VisitNotFound(String.format("Visit Id %d not found", visitId))));
		List<Prescription> medicines = prescriptionRepository.findAllByVisitId(v);
		return medicines.stream().map(medicine -> modelMapper.map(medicine, PrescriptionDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<PrescriptionDto> savePrescription(List<PrescriptionDto> prescriptionDto) {
		List<Prescription> list = prescriptionDto.stream()
				.map(entity->modelMapper.map(entity,Prescription.class))
				.collect(Collectors.toList()); 
		List<PrescriptionDto> dtoList = prescriptionRepository.saveAll(list).stream()
			    .map(entity -> modelMapper.map(entity, PrescriptionDto.class))
			    .collect(Collectors.toList());
		return dtoList;
	}

//	@Override
//	public void deletePrescription(int prescriptionId) {
//		prescriptionRepository.deleteById(prescriptionId);
//
//	}

}
