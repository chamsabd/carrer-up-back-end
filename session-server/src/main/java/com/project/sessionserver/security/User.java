package com.project.sessionserver.security;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import java.util.stream.Collectors;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document(collection = "users")
public class User implements Serializable, UserDetails {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@Id
  private String id;

  @NotBlank
  private String username;

  @NotBlank
  @Size(max = 20)
  private String nom;
  
  @NotBlank
  @Size(max = 20)
  private String prenom;
  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  @DBRef
  private Set<Role> roles = new HashSet<>();
  public User() {
  }

  public User(String username, @NotBlank @Size(max = 20) String nom,
			@NotBlank @Size(max = 20) String prenom, String email, String password) {
    this.username=username ;
    this.nom = nom;
	this.prenom = prenom;
    this.email = email;
    this.password = password;
  }
	
  public User(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 120) String password, Set<Role> roles) {
	super();
	this.username=username;
	this.password = password;
	this.roles = roles;
}
  

public User(String id, @NotBlank String username, @NotBlank @Size(max = 20) String nom,
		@NotBlank @Size(max = 20) String prenom, @NotBlank @Size(max = 50) @Email String email,
		@NotBlank @Size(max = 120) String password, Set<Role> roles) {
	super();
	this.id = id;
	this.username = username;
	this.nom = nom;
	this.prenom = prenom;
	this.email = email;
	this.password = password;
	this.roles = roles;
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

public void setPrenom(String prenom) {
	this.prenom = prenom;
}



public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

 

  public void setUsername(String newusername) {
	 
    this.username=newusername;
  }

  public String getEmail() {
    return this.email;
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

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
	// TODO Auto-generated method stub
	Set<Role> roles = this.getRoles();   
	List<SimpleGrantedAuthority> authorities = new ArrayList<>();
	for (Role role : roles) { 
		authorities.add(new SimpleGrantedAuthority(role.getName().toString()
				)
				);         } 
	 return authorities; 
}

@Override
public  String getUsername() {
	return username;
}

@Override
public boolean isAccountNonExpired() {
	// TODO Auto-generated method stub
	return true;
}

@Override
public boolean isAccountNonLocked() {
	// TODO Auto-generated method stub
	return true;
}

@Override
public boolean isCredentialsNonExpired() {
	// TODO Auto-generated method stub
	return true;
}


@Override
public boolean isEnabled() {
	// TODO Auto-generated method stub
	return true;
}

@Override
public boolean equals(Object o) {
	if (this == o)
		return true;
	if (o == null || getClass() != o.getClass())
		return false;
	User user = (User) o;
	return Objects.equals(id, user.id);
}

}
