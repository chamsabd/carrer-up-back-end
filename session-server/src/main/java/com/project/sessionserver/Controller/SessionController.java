package com.project.sessionserver.Controller;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.project.sessionserver.entities.Session;

import com.project.sessionserver.service.SessionService;

@RestController
public class SessionController {
	
	@Autowired
	  SessionService sessionRepository;
	//@PreAuthorize("hasAnyAuthority('Role_user')")
	 @GetMapping("/Sessions")
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
	        pageTuts = sessionRepository.findAll(page,size);
	      else
	        pageTuts = sessionRepository.findByNom(nom, page,size);

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
}
