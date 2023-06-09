package com.revature.service;

import java.util.List;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.revature.entity.Nurse;

public interface NurseService {
	
    public List<Nurse> getNurseFromAuth() throws UnirestException;
	
	public List<Nurse> addNurse(List<Nurse> nurses);
	
	public long getCount();

}
