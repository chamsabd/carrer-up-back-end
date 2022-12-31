package com.project.emailserver.service;


import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.sql.Template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.project.emailserver.model.Email;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

import com.jayway.jsonpath.Configuration;
import com.project.emailserver.model.Email;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;


import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    public JavaMailSender emailSender;

    @Autowired 
    private TemplateEngine templateEngine;

   
    public String sendEmail(Email mail) {

        
        try {

           final Context model =new Context();
        
            model.setVariable("content", mail.getText());
            //model.setVariable("logo", "/template/carrer.png");
        	MimeMessage message = emailSender.createMimeMessage();
        	MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        	helper.addInline("logo.png", new ClassPathResource("/template/carrer.png"));
        	helper.setTo(mail.getTo());
        	helper.setSubject(mail.getSubject());
        	//helper.setText(mail.getText()+mail.getCode());
            final String htmlContent = this.templateEngine.process("index", model);
            helper.setText(htmlContent, true /* isHtml */);
    
            // Send email

        	emailSender.send(message);

             return ("Email send ok!");
        } catch (Exception ex) {
            
            return ("Error sending email: " + ex.getMessage());
        }

       
    }
}
