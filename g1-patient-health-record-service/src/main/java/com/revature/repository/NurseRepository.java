package com.revature.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.entity.Nurse;


@Repository
public interface NurseRepository extends JpaRepository<Nurse, Integer>{

}
