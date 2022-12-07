package com.project.authserver.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTAuthorizationFiler extends OncePerRequestFilter { 
	@Override 
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,                               
			FilterChain filterChain) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*"); 
		response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method,"
				+ " Access-Control-Request-Headers,authorization");
				response.addHeader("Access-Control-Expose-Headers","Access-Control-Allow-Origin, Access-Control-Allow-Credentials, authorization"); 
		if(request.getMethod().equals("OPTIONS")){ 
			response.setStatus(HttpServletResponse.SC_OK);    
		} 
	

else if(request.getRequestURI().equals("/signin")) { 
	filterChain.doFilter(request, response); return;     
	} else {          
		String jwtToken = request.getHeader(SecurityParam.JWT_HEADER_NAME);         
		System.out.println("Token="+jwtToken); 
		if (jwtToken == null || !jwtToken.startsWith(SecurityParam.HEADER_PREFIX)) {
			filterChain.doFilter(request, response); return;             }          
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SecurityParam.SECRET)).build();  
		String jwt = jwtToken.substring(SecurityParam.HEADER_PREFIX.length());      
		DecodedJWT decodedJWT = verifier.verify(jwt);          
		System.out.println("JWT="+jwt);   
		String username = decodedJWT.getSubject();    
		String roles = decodedJWT.getClaims().get("roles").asString();
		System.out.println("username="+username);     
		System.out.println("roles="+roles);        
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(roles)); 
		       
		UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(username, null, authorities);   
		SecurityContextHolder.getContext().setAuthentication(user);
		request.setAttribute("username", username);
		request.setAttribute("authorities", authorities);
		request.setAttribute("jwt", jwtToken);
		filterChain.doFilter(request, response);       
		}     } }