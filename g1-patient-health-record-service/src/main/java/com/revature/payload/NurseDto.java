package com.revature.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NurseDto {
	private String nurseName;
	private String nurseEmail;
	private String nurseRole;

}
