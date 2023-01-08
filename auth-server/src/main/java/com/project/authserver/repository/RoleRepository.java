package com.project.authserver.repository;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.project.authserver.entities.ERole;
import com.project.authserver.entities.Role;



public interface RoleRepository extends MongoRepository<Role,String> {
	  Optional<Role> findByName(ERole name);
	  @Query("{ 'name' : ?0 }")
	  Optional<Role>  findByNameS(String name);
	}
