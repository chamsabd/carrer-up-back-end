package com.project.formation.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.formation.models.Formation;

public interface FormationRepository extends JpaRepository<Formation, Long> {
	
	Page<Formation> findAll(Pageable paging);
	//List <String> findAllCategories();
	
	List<Formation> findByCategory(@Param("category") String category,Pageable pageable);
	 List<Formation> findByNom(@Param("nom") String nom, String sort,Pageable pageable);
	 @Query("SELECT f FROM Formation f WHERE f.id= :id")
	 Formation getById(@Param("id") Long id);
	 
	void save(Optional<Formation> form);
	@Query ("SELECT category,count (*) FROM Formation f GROUP BY category ")
	List<String> getAllCategories();
}

