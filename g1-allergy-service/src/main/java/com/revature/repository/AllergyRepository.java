package com.revature.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.entity.Allergy;

public interface AllergyRepository extends JpaRepository<Allergy,Integer> {

}
