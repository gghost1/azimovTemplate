package com.example.azimovTemplate.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class SenderEmails {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("azimovlab00@mail.ru");
        message.setSubject(subject);
        message.setText(text);

        try {
            javaMailSender.send(message);
        } catch (MailException e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
            System.out.println("troubles");
        }
    }

    public String generateVerificationText (String code, String name) {
        name = Base64.getEncoder().encodeToString(name.getBytes());
        String text = String.format("Ваш код: %s\n\nТакже вы можете воспользоваться ссылкой: localhost:8080/register/%s_%s",code,code,name);
        return text;
    }

}
