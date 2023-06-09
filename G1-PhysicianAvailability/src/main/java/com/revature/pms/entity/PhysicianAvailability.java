package com.revature.pms.entity;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PHYSICIAN_AVAILABILITY")
public class PhysicianAvailability {
	@Id
	@Column(name = "PHYSICIAN_EMAIL")
	private String email;
	
	@Column(name = "PHYSICIAN_NAME")
	private String physicianName;
	
	@Column(name = "PHYSICIAN_AVAILABLE_FROM",nullable=false,updatable = true)
	private String startDate = DateTimeFormatter.ofPattern("dd-MMM-yyyy").format(LocalDateTime.now());
	
	@Column(name = "PHYSICIAN_AVAILABLE_TILL")
	private String endDate = DateTimeFormatter.ofPattern("dd-MMM-yyyy").format(LocalDateTime.now().plusDays(7));;
	
	@Column(name = "IS_AVAILABLE"	)
	private boolean isAvailable=true;
	
	@Column(name = "SPECIALITY")
	private String speciality;
	
	@Transient
	private String role;
}
