package com.revature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.revature.entity.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Integer>{
	public Patient findByEmailAndPassword(String email, String password);
	
	@Modifying
	@Query( value="update patient set password=:password where email=:email" ,nativeQuery = true)
	void updatePassword(@Param(value = "password") String password, @Param(value = "email") String email);
}
