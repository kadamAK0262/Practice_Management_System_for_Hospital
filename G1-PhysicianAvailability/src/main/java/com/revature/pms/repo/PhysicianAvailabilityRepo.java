package com.revature.pms.repo;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.revature.pms.entity.PhysicianAvailability;

@Repository
public interface PhysicianAvailabilityRepo extends JpaRepository<PhysicianAvailability,String> {

	public List<PhysicianAvailability> findByIsAvailable(boolean isAvailable,PageRequest pageable);
	
    public String deleteByEmail(String email);
    
    @Modifying
    @Query(value = "update physician_availability set physician_available_from=:startDate , physician_available_till=:endDate where physician_email=:email",nativeQuery = true)
    public void updateStartAndEndDate(@Param(value = "startDate")String startDate,@Param(value = "endDate")String endDate,@Param(value = "email")String email);

    public PhysicianAvailability findByEmail(String email);
}	
