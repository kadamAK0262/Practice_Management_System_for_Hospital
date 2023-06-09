package com.revature.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.entity.TestDetails;
import com.revature.entity.VisitDetails;

public interface TestRepository extends JpaRepository<TestDetails, Integer> {

	List<TestDetails> findAllByVisitId(Optional<VisitDetails> v);

}