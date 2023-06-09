package com.revature.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "patient")
public class Patient {
	@Column(name  = "patient_id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int patientId;
	
	
	@Column(name="email",nullable=false,unique=true)
	private String email;
	
	@Column(name="title")
	private String title;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="dob")
	private String dob;
	
	@Column(name="contact_number")
	private String contactNumber;
	
	@Column(name="password")
	private String password;
	
	@Column(name="gender")
	private String gender;

	@Column(name="address")
	private String address;
	
	
}
