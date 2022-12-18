package com.project.formation;

import org.springframework.boot.SpringApplication;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.project.formation.models.Formation;
import com.project.formation.models.Session;
import com.project.formation.repositories.FormationRepository;
import com.project.formation.repositories.SessionRepository;



@SpringBootApplication

public class FormationServerApplication implements CommandLineRunner {
	@Autowired
	private FormationRepository frepo;
	
	@Autowired
	private SessionRepository srepo;

	public static void main(String[] args) {
		SpringApplication.run(FormationServerApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		Formation f=new Formation("formation1","informatique","description1",100.0);
		frepo.save(f);
	
//		Session s=new Session("session1",1L,null);
//		s.setFormation(f);
//		srepo.save(s);
//	
	}
	

}
