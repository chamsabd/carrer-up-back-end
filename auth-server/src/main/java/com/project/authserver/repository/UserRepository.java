package com.project.authserver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.project.authserver.entities.User;

public interface UserRepository extends MongoRepository<User,String> {
	  Optional<User> findByUsername(String username);

	  Boolean existsByUsername(String username);
	  Boolean existsByEmail(String email);
	  @Query("SELECT id,username FROM User")
	  List <User> getAllUsers();
	  
	  @Query(value = "{}", fields = "{ id : 1, nom : 1, prenom : 1,roles:1,email:1,username:1 }" )
	  List <User> getAllUserse();
	  @Query("{ 'username' : ?0 }")
User findUserByUsername(String username);
@Query("{ 'email' : ?0 }")
User findUserByEmail(String email);
}