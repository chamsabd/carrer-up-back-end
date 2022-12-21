package com.project.emailserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.emailserver.model.Email;

import com.project.emailserver.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
@RestController
public class EmailController {

	
    private JavaMailSender javaMailSender=new JavaMailSenderImpl();
	
//	@GetMapping("/sendcode/{email}/{code}")
//	String sendverificationEmail(@PathVariable String email,@PathVariable int code) throws MessagingException {
//		MimeMessage message = javaMailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message);
//        
//        helper.setTo(email);
//        helper.setSubject("Testing from Spring Boot");
//        helper.setText("Hello World \n "+code);
//
//        javaMailSender.send(message);
//return email;
//    }
	
	
	 @Autowired
	    private EmailService emailService;

	    @PostMapping("/send")
	    public String sendMailSimple(@RequestBody Email mail) {
	        return emailService.sendEmail(mail);
	    }
	
	    @PostMapping("/")
	    public String sendMail(@RequestBody Email mail) {
	        return emailService.sendEmail(mail);
	    }

	
}
