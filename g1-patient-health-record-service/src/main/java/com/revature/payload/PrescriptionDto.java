package com.revature.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDto {
	private int prescriptionId;
	private String prescriptionName;
	private String dosage;
	private String prescriptionNotes;
	private VisitDto visitId;
}

//{
//"prescriptionName" :"fludrocortisone",
//"dosage" :"1-0-1",
//"prescriptionNotes" : "after eat"
//}