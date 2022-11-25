package com.project.formation;

import org.springframework.boot.SpringApplication;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

import com.project.formation.models.Formation;
import com.project.formation.repositories.FormationRepository;



@SpringBootApplication
@EnableMongoRepositories
public class FormationServerApplication implements CommandLineRunner {
	@Autowired
	private FormationRepository frepo;

	public static void main(String[] args) {
		SpringApplication.run(FormationServerApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		frepo.save(new Formation((long) 1,"formation1","description1",new Date(),new Date(),1,20,100.0));
	
	}
	

}
