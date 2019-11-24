package psoft.proj.backend.ajude.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

import javax.servlet.ServletException;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    void sendEmail(String userEmail) throws ServletException {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(userEmail);
        msg.setSubject("Confirmação de cadastro no AJuDE");
        msg.setText("Bem-vindo(a) ao AJuDE!");
        try {
            mailSender.send(msg);
        } catch (Exception e){
            throw new ServletException();
        }


    }
}