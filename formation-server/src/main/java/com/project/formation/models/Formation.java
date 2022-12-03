package com.project.formation.models;




import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;



@Entity
public class Formation {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	 Long id;
	@NotNull(message="name  cannot be null")
	private String nom;
	private String category;
	private String description;
	private double prix;
	@OneToMany(mappedBy = "formation",
            cascade = CascadeType.ALL)
	
	 Set<Session> sessions;
	
	public Set<Session> getSessions() {
		return sessions;
	}
	public void setSessions(Set<Session> sessions) {
		this.sessions = sessions;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public Formation(@NotNull(message = "name  cannot be null") String nom, String category, String description,
			double prix) {
		super();
		this.nom = nom;
		this.category = category;
		this.description = description;
		this.prix = prix;
	}
	public Formation() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Formation [id=" + id + ", nom=" + nom + ", category=" + category + ", description=" + description
				+ ", prix=" + prix + "]";
	}
	public Formation(Long id, @NotNull(message = "name  cannot be null") String nom, String category,
			String description, double prix) {
		super();
		this.id = id;
		this.nom = nom;
		this.category = category;
		this.description = description;
		this.prix = prix;
	}


}