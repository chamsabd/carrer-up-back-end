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
	Long idReponsable;
	
	
	public Session() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Long getIdReponsable() {
		return idReponsable;
	}

	public void setIdReponsable(Long idReponsable) {
		this.idReponsable = idReponsable;
	}

	public Session( String nom, Long idFormation, Long idReponsable) {
		super();
	
		this.nom = nom;
		this.idFormation = idFormation;
		this.idReponsable = idReponsable;
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
