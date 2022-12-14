package com.project.formation.controllers;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.formation.exceptions.FormationException;
import com.project.formation.models.Formation;
import com.project.formation.repositories.FormationRepository;

import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/formations")
public class FormationController {
	@Autowired
	private FormationRepository frepo;
	private NotFoundException e;
	@PostMapping
	public String saveFormation(@RequestBody Formation f) {
		frepo.save(f);
		return "Formation ajouter avec succes,son id :"+ f.getId();
	}
	/*@GetMapping("/formations")
	public List <Formation> getAllFormation(
			@RequestParam(defaultValue = "0") int page,
		     @RequestParam(defaultValue = "3") int size){
		PageRequest.of(page, size);
		return frepo.findAll();
		
	}*/
	@GetMapping
	  public ResponseEntity<?> getAlFromationsPage(
	    @RequestParam(defaultValue = "0") int page,
	    @RequestParam(defaultValue = "3") int size)  {
		 Pageable paging = (Pageable) PageRequest.of(page, size);
		Page<Formation> formations=  frepo.findAll(paging);
	     
		return new ResponseEntity<>(formations, formations.getSize() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	
	}

class Variable{
	String role;
	String id;
	
}

	//@body  role  id_user 
	@GetMapping("/allformations")
	  public ResponseEntity<?> getAllFromations(@RequestBody Variable c )
	 {
		
  


		List<Formation> formations=  (List<Formation>) frepo.findAll();

	     
		return new ResponseEntity<>(formations, formations.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	
	}	


	@GetMapping("/{id}")
	public ResponseEntity<?>  getFormation(@PathVariable Long id) throws FormationException {
		Optional <Formation> fr =frepo.findById(id);
		if (fr.isPresent()) {
			return new ResponseEntity<>(frepo.findById(id), HttpStatus.OK);
		}
		else throw new FormationException(FormationException.NotFoundException(id));
		
		
	
	}
	@DeleteMapping("/{id}")
		public void deleteFormation(@PathVariable Long id)
	{
				frepo.deleteById(id);
	}
	@PutMapping("/{id}")
    public ResponseEntity<?> updateFormation(@PathVariable Long id, @RequestBody Formation f) throws  FormationException 
	{
		Formation form = frepo.findById(id).get();
			if (form !=null)
			{
				form.setId(id);
				form.setNom(f.getNom());
				form.setCategory(f.getCategory());
				form.setDescription(f.getDescription());
				form.setPrix(f.getPrix());
				form.setSessions(f.getSessions());
				frepo.save(form);
				return new ResponseEntity<>("Updated formation with id "+id+"", HttpStatus.OK);
				
			}
					else 
						throw new  FormationException(FormationException.NotFoundException(id));
	
	}
	
	/*public ResponseEntity <?> findByNom (@Param("nom") String nom,
			 @RequestParam(defaultValue = "") String sort,
			@RequestParam(defaultValue = "0") int page,
		    @RequestParam(defaultValue = "3") int size 
		   ){
		Pageable paging = PageRequest.of(page, size);
		
		List <Formation> formations =(List<Formation>) frepo.findAll(paging);
	
	Sort direction = Sort.unsorted();
		List <Formation> res =null;
		
		try {
			for (int i =0;i<formations.size();i++) {
				if (formations.get(i).getNom().contains(nom)) {
					res.add(formations.get(i));
					
				}
			}
			 if (sort == "asc") {
		            direction = Sort.by(Sort.Direction.ASC, "prix");
		        } else if (sort == "desc") {
		            direction = Sort.by(Sort.Direction.DESC, "prix");
		        }

			return  new ResponseEntity<>(res, HttpStatus.OK);
			
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		}
	
	}
	*/
	@RequestMapping("/category/{category}")
	public List <Formation> findByCategory(@PathVariable String category,Pageable pageable){
		return frepo.findByCategory(category, pageable);
	}

    @GetMapping("/categories")
    public List<String> GetAllCategories(){
        return frepo.getAllCategories();
    }
	
}
