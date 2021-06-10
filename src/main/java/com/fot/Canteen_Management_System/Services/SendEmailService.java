package com.fot.Canteen_Management_System.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;

@Service
public class SendEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmial(String to,String body,String topic){
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom("lahirusampathsadaruwan@gmail.com");
        mailMessage.setTo(to);
        mailMessage.setSubject(topic);
        mailMessage.setText(body);
        javaMailSender.send(mailMessage);

    }
}
