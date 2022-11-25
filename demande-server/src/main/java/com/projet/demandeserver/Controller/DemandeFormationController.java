package com.projet.demandeserver.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projet.demandeserver.Repository.DemandeFormationRepository;
import com.projet.demandeserver.entities.DemandeFormation;

@RestController
public class DemandeFormationController {

	@Autowired
	private DemandeFormationRepository repository;
	
	
	@PostMapping("/addDemande")
	public String saveDemandeFormation(@RequestBody DemandeFormation demande) {
		repository.save(demande);
		return "added demande with id :" +demande.getId();
	}
	
	
	@GetMapping("/findAllDemandes")
	public List<DemandeFormation> getDemandeFormation(){
		return repository.findAll();
	}
	
	
	@GetMapping("/findDemande/{id}") 
	public Optional<DemandeFormation> getDemandeFormationbyid(@PathVariable Long id ) { 
	return repository.findById(id); 
	}
	
	@DeleteMapping("/delete/{id}")
	public String DeleteDemandeFormation(@PathVariable Long id ) {
		repository.deleteById(id);
		return "demande deleted with id :"+id;
	}
	
	@GetMapping("/demandes")
    public ResponseEntity<?> getAlFromationsPage(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "3") int size)  {
       Pageable paging = (Pageable) PageRequest.of(page, size);
      Page<DemandeFormation> demandesfromation=  repository.findAll(paging);
       
      return new ResponseEntity<>(demandesfromation, demandesfromation.getSize() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
 
  }
}
