package com.revature.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revature.entity.VisitDetails;

public interface VisitRepository extends JpaRepository<VisitDetails, Integer> {

	List<VisitDetails> findAllByPatientId(int p);
	
	@Query(value = "select * from visit_details where patient_id=:id order by visit_id desc limit 1" ,nativeQuery=true)
	public VisitDetails getLastVisitDetailsByPatientId(@Param(value="id") int id);

	VisitDetails findByAppointmentId(int id);

}