package com.projet.demandeserver.entities;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="DemandeFormation")
public class DemandeFormation {

	private Long id;
	private Date date;
	private int idSession;
	
	public DemandeFormation(Long id, Date date, int idSession) {
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

	public int getidSession() {
		return idSession;
	}

	public void setidSession(int idSession) {
		this.idSession = idSession;
	}

	@Override
	public String toString() {
		return "DemandeFormation [id=" + id + ", date=" + date + ", nomSession=" + idSession + ", toString()="
				+ super.toString() + "]";
	}
	
	

}
