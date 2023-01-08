package com.project.formation.controllers;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.formation.models.Formation;
import com.project.formation.models.Session;
import com.project.formation.repositories.FormationRepository;
import com.project.formation.repositories.SessionRepository;
import com.project.formation.service.SessionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/sessions")
public class SessionController {
	@Autowired
	  SessionService sessionService;
	@Autowired
	  SessionRepository sessionRepository1;
	@Autowired
	  FormationRepository formationRepository;
	
	
//    @GetMapping
//    public List<Session> getSessions(){
//        return (List<Session>) sessionRepository1.findAll();
//    }
    @GetMapping("/{id}")
    public Optional<Session> getSession(@PathVariable Long id){
        return sessionRepository1.findById(id);
    }
    @PostMapping
    public Session addSession(@RequestBody Session session){
        return sessionRepository1.save(session);
    }
    @PutMapping("/{id}")
    public Session addSession(@PathVariable Long id,@RequestBody Session session){
        return sessionRepository1.save(session);
    }
    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable Long id){
        sessionRepository1.deleteById(id);
    }
	
	
	 @GetMapping
	  public ResponseEntity<Map<String, Object>> getAllSessions(
	        @RequestParam(required = false,name="nom") String nom,
	        @RequestParam(defaultValue = "0",name="page") int page,
	        @RequestParam(defaultValue = "3",name="size") int size
	      ) {

	    try {
	      List<Session> Sessions = new ArrayList<Session>();
	      //Pageable paging =(Pageable) PageRequest.of(page, size);
	      
	      Page<Session> pageTuts;
	      if (nom == null && nom == "")
	        pageTuts = sessionService.findAll(page,size);
	      else
	        pageTuts = sessionService.findByNom(nom, page,size);
	      
	      Sessions = pageTuts.getContent();
	      Map<String, Object> response = new HashMap<>();
	      response.put("Sessions", Sessions);
	      response.put("currentPage", pageTuts.getNumber());
	      response.put("totalItems", pageTuts.getTotalElements());
	      response.put("totalPages", pageTuts.getTotalPages());
	      return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	 
	 
	 
	 
	 @GetMapping("/{id}/formations")
	  public ResponseEntity<Map<String, Object>> getSessionsparFormation(
			  @PathVariable("id") Long id,
	            @RequestParam(defaultValue = "0",name="page") int page,
	        @RequestParam(defaultValue = "3",name="size") int size
	      ) {
		 Logger logger
	        = LoggerFactory.getLogger(SessionController.class);
		 logger.info(id.toString());
		// logger.info("erreur "+sessionRepository1.findByIdFormationContaining(1L,PageRequest.of(page, size)).toString());
		   
	     
	    try {
	      
	      //Pageable paging =(Pageable) PageRequest.of(page, size);
	     // logger.info(sessionRepository.findByIdformation(Long.valueOf(id),page,size).toString());
	      List<Session> Sessions = new ArrayList<Session>();
	      Page<Session> pageTuts;
	      Formation f=formationRepository.getById(Long.valueOf(id));  
	        pageTuts = sessionService.findByFormation(f.getId(),page,size);

	      Sessions = pageTuts.getContent();
	      
	      Map<String, Object> response = new HashMap<>();
	      response.put("Sessions", Sessions);
	      response.put("currentPage", pageTuts.getNumber());
	      response.put("totalItems", pageTuts.getTotalElements());
	      response.put("totalPages", pageTuts.getTotalPages());
	      return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	    	   return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }}
		@PostMapping("/affecter")
		public void  affecterFormateur (Session s,Long idF){
			 this.sessionService.affecterFormateur(s,idF);
			 this.sessionRepository1.save(s);
		}
		

	 
}
