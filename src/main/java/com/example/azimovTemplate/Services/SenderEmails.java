package com.example.azimovTemplate.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class SenderEmails {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMessage(String to, String subject, String text) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setFrom("azimovlab00@mail.ru");
        mimeMessageHelper.setText(text, true);
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
        String link = String.format("http://10.91.49.245:8080/register/%s_%s", code,name);
        String text = String.format("<html><body>"
                + "<h2> Ваш код: %s </h2> <h4>Также вы можете воспользоваться ссылкой: <a href='%s'> %s </a> </h4>"
                + "</body></html>",code, link, link);
        return text;
    }

}
