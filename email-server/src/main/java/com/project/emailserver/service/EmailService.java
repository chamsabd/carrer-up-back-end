package com.project.emailserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.project.emailserver.model.Email;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    public JavaMailSender emailSender;

   
    public String sendEmail(Email mail) {

        
        try {
        	
        	MimeMessage message = emailSender.createMimeMessage();
        	MimeMessageHelper helper = new MimeMessageHelper(message);
        	 

        	helper.setTo(mail.getTo());
        	helper.setSubject(mail.getSubject());
        	helper.setText(mail.getText()+mail.getCode());
        	 
        	emailSender.send(message);

             return ("Email send ok!");
        } catch (Exception ex) {
            
            return ("Error sending email: " + ex.getMessage());
        }

       
    }
}
