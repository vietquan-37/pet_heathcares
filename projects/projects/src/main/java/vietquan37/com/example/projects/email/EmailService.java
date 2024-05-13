package vietquan37.com.example.projects.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import vietquan37.com.example.projects.config.JwtService;
import vietquan37.com.example.projects.entity.User;
import vietquan37.com.example.projects.entity.VerificationToken;
import vietquan37.com.example.projects.payload.request.RegisterDTO;
import vietquan37.com.example.projects.repository.VerificationTokenRepository;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Autowired
    private final JavaMailSender mailSender;


    @Async
    public void sendVerificationEmail(String url, String username) throws MessagingException, UnsupportedEncodingException {

        String subject = "Email Verification";
        String senderName = "User Registration Portal Service";
        String mailContent = "<p> Hi, " + username + ", </p>" + "<p>Thank you for registering with us," + "Please, follow the link below to complete your registration.</p>" + "<a href=\"" + url + "\">Verify your email to activate your account</a>" + "<p> Thank you <br> Users Registration Portal Service";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("bubakush20099@gmail.com", senderName);
        messageHelper.setTo(username);
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
