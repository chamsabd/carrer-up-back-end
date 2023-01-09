package com.project.formation.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;






@Entity
public class Session {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	String nom;
	@FutureOrPresent
Date dateDebut;
	
	@Future
Date dateFin;
	@Max(20)
int nbrPlace;
	
	Long idReponsable;
	Long idFormateur;
	Long formation_id;
	
	
	
	
	
	public Long getFormation_id() {
		return formation_id;
	}

	public void setFormation_id(Long formation_id) {
		this.formation_id = formation_id;
	}

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
	



	
	public Session(String nom, @FutureOrPresent Date dateDebut, @Future Date dateFin, @Max(20) int nbrPlace,
			Long idReponsable, Long formation_id, Long idFormateur) {
		super();
		this.nom = nom;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.nbrPlace = nbrPlace;
		this.idReponsable = idReponsable;
		this.formation_id = formation_id;
		this.idFormateur=idFormateur;
	}

	public Session(String nom, @FutureOrPresent Date dateDebut, @Future Date dateFin, @Max(20) int nbrPlace,
			Long idReponsable,Long idFormateur) {
		super();
		this.nom = nom;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.nbrPlace = nbrPlace;
		this.idReponsable = idReponsable;;
		this.idFormateur=idFormateur;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public int getNbrPlace() {
		return nbrPlace;
	}

	public void setNbrPlace(int nbrPlace) {
		this.nbrPlace = nbrPlace;
	}

	public Session( String nom, Long idReponsable) {
		super();
		this.nom = nom;
		
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
	
	public Long getIdFormateur() {
		return idFormateur;
	}
	public void setIdFormateur(Long idFormateur) {
		this.idFormateur = idFormateur;
	}
	
}
