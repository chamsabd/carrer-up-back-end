package com.project.formation.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import com.project.formation.models.Formation;
import com.project.formation.models.Session;
import com.project.formation.repositories.SessionRepository;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class SessionServiceImp  implements SessionService{
	
	private final  SessionRepository sessionRepository;
	
	  @Autowired
	    public SessionServiceImp(SessionRepository repository
	                      ) {
	        this.sessionRepository = repository;
	       
	    }
	@Override
	public Page<Session> findAll(int page, int size) {
		// TODO Auto-generated method stub
		return this.sessionRepository.findAll(PageRequest.of(page, size));
	}

	@Override
	public Page<Session> findByNom(String nom, int page, int size) {
		// TODO Auto-generated method stub
		return this.sessionRepository.findByNomContaining(nom, PageRequest.of(page, size));
	}

	 
	 public Page<Session> findByFormation(Long f, int page, int size) {
			// TODO Auto-generated method stub
			return this.sessionRepository.findByFormationContaining(f,PageRequest.of(page, size));
		}
	 
	 
		 public Session getById(Long id) {
		        return this.sessionRepository.findById(id).orElse(null);
		    }
		

	
}
