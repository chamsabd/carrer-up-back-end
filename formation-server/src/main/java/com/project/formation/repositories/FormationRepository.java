package com.project.formation.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import com.project.formation.models.Formation;

public interface FormationRepository extends MongoRepository<Formation, Long> {
	
	Page<Formation> findAll(Pageable paging);
	
	 List<Formation> findByNom(@Param("nom") String nom, @Param("nom") String sort,Pageable pageable);
	 Page<Formation> findByEtat(@Param("etat") int etat, Pageable pageable);
	void save(Optional<Formation> form);
}

