package com.project.sessionserver.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.project.sessionserver.entities.Session;
import com.project.sessionserver.entities.value_object.Formation;
import com.project.sessionserver.entities.value_object.ResponseTemplateVO;
import com.project.sessionserver.repository.SessionRepository;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class SessionServiceImp  implements SessionService{
	
	private final  SessionRepository sessionRepository;
	private final RestTemplate restTemplate;
	  @Autowired
	    public SessionServiceImp(SessionRepository repository,
	                       RestTemplate restTemplate) {
	        this.sessionRepository = repository;
	        this.restTemplate = restTemplate;
	    }
	@Override
	public Page<Session> findAll(int page, int size) {
		// TODO Auto-generated method stub
		return this.sessionRepository.findAll(PageRequest.of(page, size));
	}

	@Override
	public Page<Session> findByNom(String nom, int page, int size) {
		// TODO Auto-generated method stub
		return this.sessionRepository.findByNomContaining(nom, PageRequest.of(page, size));
	}

	 
	 public Page<Session> findByIdformation(Long id, int page, int size) {
			// TODO Auto-generated method stub
			return this.sessionRepository.findByIdFormationContaining(id,PageRequest.of(page, size));
		}
	 
		 public Session getById(Long id) {
		        return this.sessionRepository.findById(id).orElse(null);
		    }
		
	 
//	public ResponseTemplateVO getSessionWithFormation(Long id) {
//		
//		//if(this.sessionRepository.findById(new ObjectId(id)) != null)
//			Session s = this.getById(id);
//
//        Formation f = restTemplate.getForObject("http://formation-server/formations/" + s.getIdFormation(), Formation.class);
//        return new ResponseTemplateVO(s,f);
//    }
	
}
