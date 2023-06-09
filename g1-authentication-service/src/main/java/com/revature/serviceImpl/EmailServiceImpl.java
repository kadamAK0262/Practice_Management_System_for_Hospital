
package com.revature.serviceImpl;


import java.util.Properties;

import org.springframework.stereotype.Service;

import com.revature.service.EmailService;

import jakarta.mail.Address;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService{
	
	public boolean sendEmail(String subject, String message, String[] toMail) {
		
		String fromMail= "pmsg1healthcareservices@gmail.com";
		
		boolean f = false;
		
		//Variable for mail
				String host="smtp.gmail.com";
				
				
				Properties properties = System.getProperties();
				System.out.println("PROPERTIES "+properties);
				
				properties.put("mail.smtp.host", host);
				properties.put("mail.smtp.port","465");
				properties.put("mail.smtp.ssl.enable","true");
				properties.put("mail.smtp.auth","true");
				
			
				
				Session session=Session.getInstance(properties, new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {				
						return new PasswordAuthentication(fromMail, "ncyaybxhpqtrlxkw");
					}			
				});
				
				session.setDebug(true);
				
				
				MimeMessage m = new MimeMessage(session);
				
				try {
				
		
				m.setFrom(fromMail);
				
				InternetAddress[] recipients = new InternetAddress[toMail.length];
				int count = 0;
				for(String res : toMail) {
					recipients[count]= new InternetAddress(res.trim());
					count++;
				}
				
				m.setRecipients(Message.RecipientType.TO, recipients);
				
				
				
				m.setSubject(subject);
			
				
				
				m.setText(message);
				
				Transport.send(m);
				
				System.out.println("Sent success...................");
				f=true;
				
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				return f;
				
		
		
	}
	
}