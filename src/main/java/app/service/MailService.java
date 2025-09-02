package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import app.model.entity.UserAuthDetails;
import app.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    @Async
    public void sendEmail(String toUsername, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        UserAuthDetails details = userRepository.findDetailsUsername(toUsername);

        helper.setTo(details.getEmail());
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        helper.setFrom("agmerramadhan@gmail.com"); // nanti ganti sama env dan jangan harcoded
        mailSender.send(message);
        System.out.println("email berhasil dikirim ke " + toUsername);
    }

}
