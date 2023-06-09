package com.revature.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Appointment")
public class Appointment {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "appointment_id")
	private int id;
	@Column(name = "reason")
	private String reason;
	@Column(name = "date")
	private String date;
	@Column(name = "acceptance")
	private String acceptance;
	@Column(name = "patient_id")
	private int patientId;
	@Column(name = "physician_email")
	private String physicianEmail;
	@Column(name = "submission_date")
	private String submissionDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAcceptance() {
		return acceptance;
	}

	public void setAcceptance(String acceptance) {
		this.acceptance = acceptance;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getPhysicianEmail() {
		return physicianEmail;
	}

	public void setPhysicianEmail(String physicianEmail) {
		this.physicianEmail = physicianEmail;
	}

	public String getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(String submissionDate) {
		this.submissionDate = submissionDate;
	}
}
/*
 * "reason":"health checkup", "date":"2023-03-22", "acceptance":"Yes",
 * "patientId":"1", "physicianEmail":"p@gmail.com", "submissionDate":"2023-03-12
 */