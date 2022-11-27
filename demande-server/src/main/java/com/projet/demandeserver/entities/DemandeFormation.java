package com.projet.demandeserver.entities;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="DemandeFormation")
public class DemandeFormation {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Date date;
	private Long idSession;
	
	public DemandeFormation(Long id, Date date, Long idSession) {
		super();
		this.id = id;
		this.date = date;
		this.idSession = idSession;
	}

	public DemandeFormation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getidSession() {
		return idSession;
	}

	public void setidSession(Long idSession) {
		this.idSession = idSession;
	}

	@Override
	public String toString() {
		return "DemandeFormation [id=" + id + ", date=" + date + ", nomSession=" + idSession + ", toString()="
				+ super.toString() + "]";
	}
	
	

}
