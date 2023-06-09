package com.revature.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDto {
	private int testId;
	private String testName;
	private String result;
	private String testNotes;
	private VisitDto visitId;
}

//{
// "testName" :"Blood test",
// "result" : "9 points",
// "testNotes" : "fewer red blood cells than normal"
//}