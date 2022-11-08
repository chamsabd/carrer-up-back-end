package com.project.sessionserver.repository;


import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.project.sessionserver.entities.Session;

@RepositoryRestResource
public interface SessionRepository  extends CrudRepository<Session,Long>{
	Page<Session> findAll(Pageable pageable);
	 //@Query("SELECT s FROM Session s WHERE lower(s.nom) LIKE CONCAT('%',lower(:nom),'%') ORDER BY s.id")
	Page<Session> findByNomContaining(@Param("nom") String nom, Pageable pageable);
	
}
