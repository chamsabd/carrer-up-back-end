package com.project.formation.service;

import java.awt.print.Pageable;

import org.springframework.data.domain.Page;

import com.project.formation.models.Formation;
import com.project.formation.models.Session;



public interface SessionService {
	Page<Session> findAll(int page,int size);
	Page<Session> findByNom( String nom, int page,int size);
	Page<Session> findByFormation(Long f, int page, int size);
	//ResponseTemplateVO getSessionWithFormation(Long id);
	void affecterFormateur (Session s,Long idF);
	void affecterReponsable (Session s,Long idR);
}
