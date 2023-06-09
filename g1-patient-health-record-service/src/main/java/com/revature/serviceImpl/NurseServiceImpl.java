package com.revature.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.revature.entity.Nurse;
import com.revature.repository.NurseRepository;
import com.revature.service.NurseService;

@Service
public class NurseServiceImpl implements NurseService {
	
	@Autowired(required=false)
	private NurseRepository nurseRepo;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public List<Nurse> getNurseFromAuth() throws UnirestException {
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
				.header("authorization",
						"Bearer "+value)
				.asString();
		System.out.println(response.getBody());
		ArrayList<Nurse> nurses = new ArrayList<>();
		try {
			jsonNode = objectMapper.readTree(response1.getBody());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < jsonNode.size(); i++) {
			System.out.println(jsonNode.get(i) + "/n");
			Nurse nurse = new Nurse();
			String role = jsonNode.get(i).get("user_metadata").get("role").textValue();
			if (role.equalsIgnoreCase("Nurse")) {
				nurse.setNurseRole(role);
				nurse.setNurseEmail(jsonNode.get(i).get("email").textValue());
				nurse.setNurseName(jsonNode.get(i).get("name").textValue());
				nurses.add(nurse);
			}
		}
		return nurses;
	}

	@Override
	public List<Nurse> addNurse(List<Nurse> nurses) {
		return nurseRepo.saveAll(nurses);
	}

	@Override
	public long getCount() {
		return nurseRepo.count();
	}

}
