package com.project.formation.repositories;


import org.springframework.data.domain.Pageable;

import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import com.project.formation.models.Formation;
import com.project.formation.models.Session;



@Repository
public interface SessionRepository  extends JpaRepository<Session,Long>{
	
	Page<Session> findAll(Pageable pageable);
	 //@Query("SELECT s FROM Session s WHERE lower(s.nom) LIKE CONCAT('%',lower(:nom),'%') ORDER BY s.id")
	Page<Session> findByNomContaining(@Param("nom") String nom, Pageable pageable);
	@Query("SELECT s FROM Session s WHERE s.formation_id= :id")
	Page<Session> findByFormationContaining(@Param("id") Long id, Pageable pageable);
	

//	
}
