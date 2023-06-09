package com.revature.service.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.revature.entity.Allergy;
import com.revature.repository.AllergyRepository;
import com.revature.service.AllergyService;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AllergyServiceTest {
	@Autowired
	private AllergyService allergyService;

	@MockBean
	private AllergyRepository allergyRepo;

	@Test
	public void getAllAllergiesTest() {
	
		when(allergyRepo.findAll()).thenReturn(Stream.of(new Allergy(5,"Nose Allergy","Itching"),new Allergy(6,"Skin Allergy","Rashes")).collect(Collectors.toList()));
		assertEquals(2,allergyService.allAllergies().size());
	}

	@Test
	public void getAllergyById() {
		Optional<Allergy> a = Optional.ofNullable(new Allergy(1, "Nose Allergy", "Itching"));
		when(allergyRepo.findById(1)).thenReturn(a);
		assertEquals(1, allergyService.getAllergyById(1).getAllergyId());
	}
}
