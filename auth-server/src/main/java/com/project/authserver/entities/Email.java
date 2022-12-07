package com.project.authserver.entities;

import java.io.Serializable;

public class Email implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String to;
    private String subject;
    private String text;
    private String code;

    public Email() {
    }

    public Email(String to, String subject, String text) {
        this.to = to;
        this.subject = subject;
        this.text = text;
    }
    
    
    

    public Email(String to, String subject, String text, String code) {
		super();
		this.to = to;
		this.subject = subject;
		this.text = text;
		this.code = code;
	}

	public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Email{" +
                "to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
