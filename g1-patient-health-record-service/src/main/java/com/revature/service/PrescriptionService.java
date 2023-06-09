package com.revature.service;

import java.util.List;

import com.revature.payload.PrescriptionDto;

public interface PrescriptionService {

	public List<PrescriptionDto> savePrescription(List<PrescriptionDto> prescriptionDto);

	public List<PrescriptionDto> getPrescriptionDetails(int visitId);

//	public void deletePrescription(int prescriptionId);
}