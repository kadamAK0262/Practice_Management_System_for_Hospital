package com.revature.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dto.AllergyDto;
import com.revature.entity.Allergy;
import com.revature.repository.AllergyRepository;
import com.revature.service.AllergyService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class AllergyServiceImpl implements AllergyService {

	@Autowired
	AllergyRepository allergyRepository;
	@Autowired
	ModelMapper modelMapper;

//	private static final Logger log = LogManager.getLogger(AllergyService.class);

	@Override
	public List<AllergyDto> allAllergies() {

		List<Allergy> allergies = allergyRepository.findAll();
		System.out.println("getting data" + allergies);
		return allergies.stream().map(allergy -> modelMapper.map(allergy, AllergyDto.class))
				.collect(Collectors.toList());

	}

	@Override
	public AllergyDto getAllergyById(int id) {

		return modelMapper.map(allergyRepository.findById(id).get(), AllergyDto.class);
	}
}
