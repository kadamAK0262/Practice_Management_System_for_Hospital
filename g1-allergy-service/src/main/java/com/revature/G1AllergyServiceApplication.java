package com.revature;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;


@SpringBootApplication
//@EnableDiscoveryClient
//@EnableSwagger2
public class G1AllergyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(G1AllergyServiceApplication.class, args);
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
}
