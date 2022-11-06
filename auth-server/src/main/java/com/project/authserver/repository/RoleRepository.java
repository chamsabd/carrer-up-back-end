package com.project.authserver.repository;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project.authserver.entities.ERole;
import com.project.authserver.entities.Role;



public interface RoleRepository extends MongoRepository<Role,String> {
	  Optional<Role> findByName(ERole name);
	}
