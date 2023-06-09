package com.revature;



import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.revature.entity.Email;
import com.revature.service.EmailService;
import com.revature.serviceImpl.EmailServiceImpl;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;




@SpringBootApplication
//@EnableDiscoveryClient
public class G1AuthenticationServiceApplication {
    
	public static void main(String[] args) {
		SpringApplication.run(G1AuthenticationServiceApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public TimedAspect timedAspect(MeterRegistry registry) {
		return new TimedAspect(registry);
	}
		
	@Bean
    public MeterRegistry meterRegistry() {
        return new SimpleMeterRegistry();
    }	
	
	@Bean
	public EmailService emailService() {
		return new EmailServiceImpl();
	}
}
