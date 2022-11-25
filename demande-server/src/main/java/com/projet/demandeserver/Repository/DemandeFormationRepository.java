package com.projet.demandeserver.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.projet.demandeserver.entities.DemandeFormation;

public interface DemandeFormationRepository extends MongoRepository<DemandeFormation,Long>{

}
