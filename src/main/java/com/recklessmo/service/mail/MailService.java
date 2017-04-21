package com.recklessmo.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

/**
 * Created by hpf on 4/21/17.
 */
@Service
public class MailService {

    @Autowired
    private MailSender mailSender;

    public void sendSimpleEmail(String title, String text, String to) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("school-cloud");
            simpleMailMessage.setSubject(title);
            simpleMailMessage.setText(text);
            mailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
