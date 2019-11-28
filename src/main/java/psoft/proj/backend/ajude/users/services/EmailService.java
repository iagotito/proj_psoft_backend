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
        msg.setText("Bem-vindx ao AJuDE! Muito obrigado por se juntar a nós!!\n\nAgora você já pode participar da nossa plataforma.\nFaça seu login em psoft-ajude-o-grupo-13.surge.sh");
        try {
            mailSender.send(msg);
        } catch (Exception e){
            throw new ServletException();
        }
    }
}