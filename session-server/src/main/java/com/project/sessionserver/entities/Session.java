package com.project.sessionserver.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.bson.types.ObjectId;




@Entity
public class Session {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	String nom;
	Long idFormation;
	
	
	public Session() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Session( String nom,Long idFormation) {
		
		this.nom = nom;
		this.idFormation = idFormation;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Long getIdFormation() {
		return idFormation;
	}
	public void setIdFormation(Long idFormation) {
		this.idFormation = idFormation;
	}
	
}
