package com.projet.demandeserver.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.projet.demandeserver.entities.DemandeFormation;

public interface DemandeFormationRepository extends MongoRepository<DemandeFormation,Long>{

	Page<DemandeFormation> findAll(Pageable paging);
}
