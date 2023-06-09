package com.revature.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class PatientDto {

	private int patientId;
	private String email;
	private String title;
	private String firstName;
	private String lastName;
	private String dob;
	private String contactNumber;
	private String password;
	private String gender;
	private String address;
	
}
