package com.revature.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Allergy")
public class Allergy {
@Column(name="Allergy_id")
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
	public int allergyId;
@Column(name="Allergy_name")
	public String allergyName;
@Column(name="Allergy_description")
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
public Allergy(int allergyId, String allergyName, String allergyDescription) {
	super();
	this.allergyId = allergyId;
	this.allergyName = allergyName;
	this.allergyDescription = allergyDescription;
}
public Allergy() {
	super();
	// TODO Auto-generated constructor stub
}
@Override
public String toString() {
	return "Allergy [allergyId=" + allergyId + ", allergyName=" + allergyName + ", allergyDescription="
			+ allergyDescription + ", getAllergyId()=" + getAllergyId() + ", getAllergyName()=" + getAllergyName()
			+ ", getAllergyDescription()=" + getAllergyDescription() + ", getClass()=" + getClass() + ", hashCode()="
			+ hashCode() + ", toString()=" + super.toString() + "]";
}
}
