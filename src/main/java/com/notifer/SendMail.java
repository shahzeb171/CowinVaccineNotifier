package com.notifer;

import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


public class SendMail {
	public JavaMailSender mailSend() {
		JavaMailSenderImpl jms = new JavaMailSenderImpl();
		jms.setHost("smtp.gmail.com");
		jms.setPort(587);
		jms.setUsername("**email**");
		jms.setPassword("****password***");
		
		Properties props = jms.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
          
        return jms;
	}
	public void doSendEmail(String recipientAddress
			,String message) {

    	String subject = "COWIN SLOT ACTIVE: ";
        JavaMailSender mailSender = mailSend(); 
        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
        System.out.println(message);
        System.out.println(email);
        System.out.println(mailSender);
        mailSender.send(email);
    }
}
