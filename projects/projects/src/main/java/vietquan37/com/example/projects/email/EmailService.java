package vietquan37.com.example.projects.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Autowired
    private final JavaMailSender mailSender;


    @Async("taskExecutor")
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
    @Async("taskExecutor")
    public void sendResetPasswordEmail(String url, String username) throws MessagingException, UnsupportedEncodingException {

        String subject = "Reset Password";
        String senderName = "User Registration Portal Service";
        String mailContent = "<p> Hi, " + username + ", </p>" +
                "<p>We received a request to reset your password. " +
                "Please, follow the link below to reset your password.</p>" +
                "<a href=\"" + url + "\">Reset your password</a>" +
                "<p>If you did not request a password reset, please ignore this email or contact support if you have questions.</p>" +
                "<p> Thank you <br> User Registration Portal Service</p>";

        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("bubakush20099@gmail.com", senderName);
        messageHelper.setTo(username);
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

}
