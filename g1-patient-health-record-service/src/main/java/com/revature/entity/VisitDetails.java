package com.revature.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "visit_details")
public class VisitDetails {

	@Id
	@Column(name = "visit_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int visitId;

	@Column(name = "patient_id")
	private int patientId;

	@Column(name = "height")
	private float height;

	@Column(name = "weight")
	private float weight;

	@Column(name = "blood_pressure_systolic")
	private int bpSystolic;

	@Column(name = "blood_pressure_diastolic")
	private int bpDiastolic;

	@Column(name = "body_temparature")
	private float bodyTemparature;

	@Column(name = "respiration_rate")
	private int respirationRate;

	@Column(name = "blood_group")
	private String bloodGroup;

	@Column(name = "nurse_email", nullable = false)
	private String nurseEmail;

	@Column(name = "physician_email", nullable = false)
	private String physicianEmail;

	@Column(name = "appointment_id")
	private int appointmentId;

	@Column(name = "key_notes")
	private String keyNotes;

	@Column(name = "allergy_id")
	private int allergyId;

	public VisitDetails(int visitId) {
		this.visitId = visitId;
	}

}
