package com.project.authserver.request;


import java.util.Set;

import javax.validation.constraints.*;

import com.project.authserver.entities.Role;
 
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String nom;
    @NotBlank
    @Size(min = 3, max = 20)
    private String prenom;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
	
	private String code;
	
	
	

    
    private Set<String> roles;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
  
    @NotBlank
    @Size(min = 6, max = 40)
    private String confirmpassword;    
    
    
    
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
   
	public String getConfirmpassword() {
		return confirmpassword;
	}

	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}


 
    public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}
	public String getUsername() {
		return email.split("@")[0];
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setRoles(Set<String> roles) {
		this.roles=roles;
       // this.roles.addAll();
	}

	public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRoles() {
      return this.roles;
    }
    
    
}
