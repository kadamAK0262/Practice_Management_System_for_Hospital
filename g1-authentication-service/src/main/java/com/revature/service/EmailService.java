package com.revature.service;

public interface EmailService {

	public boolean sendEmail(String subject, String message, String[] toMail);
}
