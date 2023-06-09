package com.revature.service;

import java.util.List;


import com.revature.dto.AllergyDto;

public interface AllergyService {

	public AllergyDto getAllergyById(int id);
	public List<AllergyDto> allAllergies();
}
