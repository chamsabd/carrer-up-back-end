package com.project.formation.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;





@Entity
public class Session {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	Long id;
	String nom;
	@ManyToOne
    @JoinColumn(name = "formation_id", nullable = false)
	Formation formation;
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
	

	public Formation getFormation() {
		return formation;
	}

	public void setFormation(Formation formation) {
		formation = formation;
	}

	public Session( String nom, Long idReponsable,Formation f) {
		super();
	
		this.nom = nom;
		this.formation=f;
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
	
	
}
