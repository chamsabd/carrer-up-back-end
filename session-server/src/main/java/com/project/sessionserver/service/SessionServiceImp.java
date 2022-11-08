package com.project.sessionserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.project.sessionserver.entities.Session;
import com.project.sessionserver.repository.SessionRepository;
@Service
public class SessionServiceImp  implements SessionService{
	@Autowired
	  SessionRepository sessionRepository;
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

}
