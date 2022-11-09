package com.project.formation.exceptions;

public class FormationException extends Exception{
	private static final long serialVersionUID = 1L;

	public FormationException(String message) {
		super(message);
	}
	
	public static String NotFoundException(Long id) {
		return "Cette formation avec l'id  "+id+" n existe pas !";
	}
	
	public static String FormationAlreadyExists() {
		return "Cette formation existe deja ";
	}
	
}
