package com.revature.pms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.revature.pms.entity.PhysicianAvailability;
import com.revature.pms.repo.PhysicianAvailabilityRepo;

@Service
public class PhysicianAvailabiltiyService {
	@Autowired
	private PhysicianAvailabilityRepo repo;

	@Autowired
	ObjectMapper objectMapper;

	public List<PhysicianAvailability> getPhysicians() {
		return repo.findAll();
	}

	public long count() {
		return repo.count();
	}

	public List<PhysicianAvailability> getAvailablePhysicians(int page,int size,boolean isAvailable) {
		return repo.findByIsAvailable(isAvailable,PageRequest.of(page, size));
	}

	public List<PhysicianAvailability> addPhysician(List<PhysicianAvailability> physicians) {
		for (PhysicianAvailability physicianAvailability : physicians) {
			if(!repo.existsById(physicianAvailability.getEmail()))
					repo.save(physicianAvailability);
		}
		return repo.findAll();
	}

	public void schedulePhysician(String startDate, String endDate, String email) {
		repo.updateStartAndEndDate(startDate, endDate, email);
	}

	public PhysicianAvailability update(PhysicianAvailability update) {
		return repo.save(update);
	}

	public String deletePhysicianAvailabilityById(String email) {
		return repo.deleteByEmail(email);
	}

	public ArrayList<PhysicianAvailability> getPhysicianFromAuth() throws UnirestException {
		
		HttpResponse<String> response = Unirest.post("https://dev-ipfjfrntvhuq88xm.us.auth0.com/oauth/token")
				  .header("content-type", "application/json")
				  .body("{\"client_id\":\"zYOAGwF013k2DhZK0b4xuUohLV64j1TR\",\"client_secret\":\"K1NBFuYqxIZ0R5o7djsR5-RpcbuHSjXGSb8SWO_kJ7FuVXANZvd1jW0FNnVv63tf\",\"audience\":\"https://dev-ipfjfrntvhuq88xm.us.auth0.com/api/v2/\",\"grant_type\":\"client_credentials\"}")
				  .asString();
		System.out.println(response.getBody());
		JsonNode jsonNode = null;
		try {
			jsonNode = objectMapper.readTree(response.getBody());
			System.out.println(jsonNode);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		String value  = jsonNode.get("access_token").textValue();
		String request = "https://dev-ipfjfrntvhuq88xm.us.auth0.com/api/v2/users";
		HttpResponse<String> response1 = Unirest.get(request)
				.header("authorization", "Bearer "+value)
				.asString();
		ArrayList<PhysicianAvailability> physicians = new ArrayList<>();
		try {
			jsonNode = objectMapper.readTree(response1.getBody());
			System.out.println(jsonNode);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < jsonNode.size(); i++) {
			System.out.println(jsonNode.get(i));
			PhysicianAvailability physician = new PhysicianAvailability();
			String role = jsonNode.get(i).get("user_metadata").get("role").textValue();
			if (role.equalsIgnoreCase("Physician")) {
				physician.setRole(role);
				physician.setEmail(jsonNode.get(i).get("email").textValue());
				physician.setPhysicianName(jsonNode.get(i).get("name").textValue());
				physician.setSpeciality(jsonNode.get(i).get("user_metadata").get("speciality").textValue());
				physicians.add(physician);
			}
		}
		return physicians;
	}	
	
	public PhysicianAvailability getPhysicianByEmail(String email) {
		return repo.findByEmail(email);
	}
}
