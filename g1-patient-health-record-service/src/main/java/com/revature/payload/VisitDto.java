package com.revature.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitDto {
	private int visitId;
	private int patientId;
	private float height;
	private float weight;
	private int bpSystolic;
	private int bpDiastolic;
	private float bodyTemparature;
	private int respirationRate;
	private String bloodGroup;
	private String nurseEmail;
	private String physicianEmail;
	private int appointmentId;
	private String keyNotes;
	private int allergyId;
	
	public VisitDto(int visitId) {
		this.visitId=visitId;
	}

}

//{
//    "height": 37.5,
//    "weight": 80.0,
//    "bpSystolic": 70,
//    "bpDiastolic": 125,
//    "bodyTemparature": 40.0,
//    "respirationRate": 32,
//    "bloodGroup": "O-ve",
//    "nurseEmail": "nurse3.pms@gmail.com",
//    "physicianEmail": "physician3.pms@gmail.com",
//    "appointmentId": 4,
//    "keyNotes": "qwerfvbnjuytrfdsergbnmkiuyhgfgh",
//    "allergyId": 8
//}
