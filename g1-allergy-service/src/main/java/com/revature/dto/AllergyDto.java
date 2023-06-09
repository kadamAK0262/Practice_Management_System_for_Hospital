package com.revature.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllergyDto {
public int allergyId;
public String allergyName;
public String allergyDescription;
public int getAllergyId() {
	return allergyId;
}
public void setAllergyId(int allergyId) {
	this.allergyId = allergyId;
}
public String getAllergyName() {
	return allergyName;
}
public void setAllergyName(String allergyName) {
	this.allergyName = allergyName;
}
public String getAllergyDescription() {
	return allergyDescription;
}
public void setAllergyDescription(String allergyDescription) {
	this.allergyDescription = allergyDescription;
}
//public Object thenReturn(AllergyDto a) {
//	// TODO Auto-generated method stub
//	return null;
//}
}
