package com.project.formation.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.project.formation.models.Formation;

public interface FormationRepository extends CrudRepository<Formation, Long> {
	
	Page<Formation> findAll(Pageable paging);
	
	 List<Formation> findByNom(@Param("nom") String nom, @Param("nom") String sort,Pageable pageable);
	 @Query("SELECT f FROM Formation f WHERE f.id= :id")
	 Formation getById(@Param("id") Long id);
	void save(Optional<Formation> form);
}

