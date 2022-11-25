package com.project.sessionserver.service;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;


import com.project.sessionserver.entities.Session;
import com.project.sessionserver.entities.value_object.ResponseTemplateVO;

public interface SessionService {
	Page<Session> findAll(int page,int size);
	
	Page<Session> findByNom( String nom, int page,int size);
	Page<Session> findByIdformation(Long id, int page, int size);
	//ResponseTemplateVO getSessionWithFormation(Long id);
}
