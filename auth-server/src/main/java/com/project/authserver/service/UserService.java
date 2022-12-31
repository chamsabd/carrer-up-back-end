package com.project.authserver.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.authserver.entities.ERole;
import com.project.authserver.entities.Role;
import com.project.authserver.entities.User;
import com.project.authserver.repository.RoleRepository;
import com.project.authserver.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class UserService implements UserDetailsService{
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	@Autowired
	public UserService(UserRepository userRepository,RoleRepository roleRepository) {
		this.userRepository = userRepository; 
		this.roleRepository=roleRepository;
		} 
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Objects.requireNonNull(username);    
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		//return user;
		Set<Role> authorities=new HashSet<>(); 
		user.getRoles().forEach(r->{
			authorities.add(new Role(r.getName())); });
		return new User(user.getUsername(),user.getPassword(),authorities); 
	}
	
	 static boolean isValid(String email) {
			String regex = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
			return email.matches(regex);
		 }
	
	public List<User> all() throws UsernameNotFoundException {
		
		List<User> u=userRepository.findAll();
		System.out.print(u.get(0).getUsername());
		return u;
	}
	
	public User saveUser( String nom,String prenom, String password, String confirmedPassword) {
		 User appUser = new User(); 
		String username=nom+prenom;
		
		 if (!isValid(username)) 
		 throw new RuntimeException("email not valid"); 
		 if (nom.equals("")) 
		 throw new RuntimeException("nom is required"); 
		 if (prenom.equals("")) 
		 throw new RuntimeException("prenom is required"); 
		 if (userRepository.findByUsername(username).isPresent() == true) 
		 throw new RuntimeException("User already exists");
		 if (password.equals("")) 
		 throw new RuntimeException("Please add your password");
		 if (!password.equals(confirmedPassword)) 
		 throw new RuntimeException("Please confirm your password"); 
		
		 Set<Role> roles = new HashSet<Role>(); 
		 if (userRepository.findAll().isEmpty()==true) {
			 Role r = new Role(ERole.ROLE_ADMIN);
			 roles.add(r);
		 }
		 else
		 {
			 Role r = new Role(ERole.ROLE_USER);
			 roles.add(r);
		 }
     	 
		 appUser.setPassword(new BCryptPasswordEncoder().encode(password));
		 appUser.setNom(nom); 
		 appUser.setPrenom(prenom);
		 
		 userRepository.save(appUser); 
		 return appUser; 
		 }
	
	
}
