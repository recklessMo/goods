package com.recklessmo.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * created 04/21 2017
 *
 **/
@Service
public class MailService {

    @Resource
    private MailSender mailSender;

    public void sendSimpleEmail(String title, String text, String to) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("13088063013@163.com");
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(title);
            simpleMailMessage.setText(text);
            mailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
