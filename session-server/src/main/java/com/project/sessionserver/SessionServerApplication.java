package com.project.sessionserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.project.sessionserver.entities.Session;
import com.project.sessionserver.repository.SessionRepository;

@SpringBootApplication
public class SessionServerApplication implements CommandLineRunner {
	@Autowired
	private SessionRepository srepo;
	public static void main(String[] args) {
		SpringApplication.run(SessionServerApplication.class, args);
	}
	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	@Override
	public void run(String... args) throws Exception {
		srepo.save(new Session("session1",1L));
		
	}

}
