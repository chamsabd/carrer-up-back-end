package com.projet.demandeserver;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.projet.demandeserver.Repository.DemandeFormationRepository;
import com.projet.demandeserver.entities.DemandeFormation;

@SpringBootApplication
public class DemandeServerApplication implements CommandLineRunner{

	@Autowired
	DemandeFormationRepository repo;
	public static void main(String[] args) {
		SpringApplication.run(DemandeServerApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		repo.save(new DemandeFormation((long) 1,new Date(),1L));
	}
	
}
