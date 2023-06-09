package com.revature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.revature.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient,Integer>{

	
}
