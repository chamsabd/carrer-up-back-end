package com.project.sessionserver.service;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;


import com.project.sessionserver.entities.Session;

public interface SessionService {
	Page<Session> findAll(int page,int size);
	
	Page<Session> findByNom( String nom, int page,int size);
	
}
