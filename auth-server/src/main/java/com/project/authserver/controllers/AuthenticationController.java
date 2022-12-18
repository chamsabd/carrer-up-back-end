package com.project.authserver.controllers;


import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.project.authserver.entities.ERole;
import com.project.authserver.entities.Email;
import com.project.authserver.entities.Role;
import com.project.authserver.entities.User;
import com.project.authserver.repository.RoleRepository;
import com.project.authserver.repository.UserRepository;
import com.project.authserver.request.LoginRequest;
import com.project.authserver.request.SignupRequest;
import com.project.authserver.response.ConnValidationResponse;
import com.project.authserver.response.JwtResponse;
import com.project.authserver.response.MessageResponse;

import com.project.authserver.security.SecurityParam;


@RestController
public class AuthenticationController {
	
	private final RestTemplate restTemplate=new RestTemplate();
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;


	
	Boolean in ;
	String rolefin;
	String code="-1";
	
	String randomcode() {
		
		 // create a string of uppercase and lowercase characters and numbers
	    String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	    String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
	    String numbers = "0123456789";

	    // combine all strings
	    String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

	    // create random string builder
	    StringBuilder sb = new StringBuilder();

	    // create an object of Random class
	    Random random = new Random();

	    // specify length of random string
	    int length = 5;

	    for(int i = 0; i < length; i++) {

	      // generate random index number
	      int index = random.nextInt(alphaNumeric.length());

	      // get character specified by index
	      // from the string
	      char randomChar = alphaNumeric.charAt(index);

	      // append the character to string builder
	      sb.append(randomChar);
	    }

	    String randomString = sb.toString();
	    
		return randomString;
	}
	
	
	
	
	
	
	@PostMapping("/code")
	public ResponseEntity<?> verifUser(@Valid @RequestBody SignupRequest signupRequest,HttpServletRequest request) throws URISyntaxException {
		
		
		byte[] array = new byte[7]; // length is bounded by 7
	    new Random().nextBytes(array);
	    code = randomcode();

		if (userRepository.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
	
	

	if (userRepository.existsByEmail(signupRequest.getEmail())) {
		return ResponseEntity
				.badRequest()
				.body(new MessageResponse("Error: Email is already in use!"));
	}
		
	User user = new User(signupRequest.getUsername(), 
			signupRequest.getNom(),
			signupRequest.getPrenom(),
						 signupRequest.getEmail(),
						 
						 encoder.encode(signupRequest.getPassword()));

	Set<String> strRoles = signupRequest.getRoles();
	Set<Role> roles = new HashSet<>();

	if (strRoles == null) {
		Role userRole = roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);
	} else {
		strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(adminRole);

				break;
			case "mod":
				Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(modRole);

				break;
			default:
				Role userRole = roleRepository.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(userRole);
			}
		});
	}

	user.setRoles(roles);
	
	
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
headers.setAccessControlAllowOrigin("*");
		
		
		URI uri = new URI("http://localhost:8085/EMAIL-SERVER/code");
		Email email = new Email();
		email.setTo(signupRequest.getEmail());
		email.setSubject("verif");
		email.setText("please pass this code to sign up  ");
		email.setCode(code);

		HttpEntity<Email> httpEntity = new HttpEntity<>(email, headers);

		RestTemplate restTemplate = new RestTemplate();
		String res= restTemplate.postForObject(uri, httpEntity,String.class);
			
			//restTemplate.postForObject("http://localhost:8085/EMAIL-SERVER/code/", String.class, null, null);
		
		return ResponseEntity.ok(new MessageResponse(code));
		
		
	}
	

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,HttpServletRequest request) {

		
	
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername().split("@")[0], loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
		
		User userDetails =  (User) authentication.getPrincipal();
	
		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
	
	  in=false;
	  //admin 
		String usrole = loginRequest.getUsername().split("@")[1].split(".com")[0];
		
		
		
			if( usrole.toLowerCase().equals("admin") ) {		
				roles.forEach(role -> {
					if(role == ERole.ROLE_ADMIN.name()) {
						rolefin=ERole.ROLE_ADMIN.name();
						in = true;
					}
				});
				
				
			}
			if( usrole.toLowerCase().equals("user")) {		
				roles.forEach(role -> {
					if(role == ERole.ROLE_USER.name()) {
						rolefin=ERole.ROLE_USER.name();
						in = true;
					}
				});
				
				
			}
		if(in==false) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: you don''t have permission"));
		}
		else {
		
		
		      String jwt= JWT.create() 
				.withIssuer(request.getRequestURI())   
				.withSubject(userDetails.getUsername())         
				.withClaim("roles",rolefin)    
				.withExpiresAt(new Date(System.currentTimeMillis()+SecurityParam.EXPIRATION))
				.sign(Algorithm.HMAC256(SecurityParam.SECRET));
		      System.out.println(userDetails.getEmail());
		//ResponseEntity.addHeader("test test","inn");
		//ResponseEntity.addHeader(SecurityParam.JWT_HEADER_NAME,jwt);
		    
		return ResponseEntity.ok(new JwtResponse(jwt, 
				 userDetails.getId(), 
				 userDetails.getUsername(), 
				 userDetails.getEmail(), 
				 rolefin));
		}
		

	}
	
	


	
	
	

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
	
		if (!signupRequest.getCode().equals(code)) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error:code incorrecte!"));
		}
		code="-1";
		
		
		if (userRepository.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}
	
	

	if (userRepository.existsByEmail(signupRequest.getEmail())) {
		return ResponseEntity
				.badRequest()
				.body(new MessageResponse("Error: Email is already in use!"));
	}
		
	User user = new User(signupRequest.getUsername(), 
			signupRequest.getNom(),
			signupRequest.getPrenom(),
						 signupRequest.getEmail(),
						 
						 encoder.encode(signupRequest.getPassword()));

	Set<String> strRoles = signupRequest.getRoles();
	Set<Role> roles = new HashSet<>();

	if (strRoles == null) {
		Role userRole = roleRepository.findByName(ERole.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		roles.add(userRole);
	} else {
		strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(adminRole);

				break;
			case "mod":
				Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(modRole);

				break;
			default:
				Role userRole = roleRepository.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(userRole);
			}
		});
	}

	user.setRoles(roles);
	
	
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	
	
	}
@GetMapping("/user")
	public ResponseEntity<?> user() {
		List<User>u=userRepository.findAll();
		return ResponseEntity.ok(u);
	}




	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request) throws ServletException {
		request.logout(); 
		return new ResponseEntity<String>(HttpStatus.CREATED);}
	

//@PostMapping(value = "/api/v1/validateToken", produces = {MediaType.APPLICATION_JSON_VALUE})
//   public ResponseEntity<ConnValidationResponse> validatePost() {
//	 
//       return ResponseEntity.ok(
//       		new ConnValidationResponse("ok",true,HttpMethod.POST.name()));
//   }

   @GetMapping(value = "/api/v1/validateToken", produces = {MediaType.APPLICATION_JSON_VALUE})
   public ResponseEntity<ConnValidationResponse> validateGet(HttpServletRequest request) {
       String username = (String) request.getAttribute("username");
       String token = (String) request.getAttribute("jwt");
       List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) request.getAttribute("authorities");
       return ResponseEntity.ok(
       		new ConnValidationResponse("ok",HttpMethod.GET.name(),username,token,grantedAuthorities));
       		
       		
   }
	
	
}







