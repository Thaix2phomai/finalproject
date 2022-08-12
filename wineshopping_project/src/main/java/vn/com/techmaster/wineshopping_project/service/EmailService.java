package vn.com.techmaster.wineshopping_project.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {
    private JavaMailSender emailSender;

    public void sendEmail(String email, String text){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Your verification code from cheese-alcoholic website");
//        message.setText("http://localhost:8080/login/validate/"+text);
        message.setText("http://18.141.211.156/login/validate/"+text);

        // Send Message!
        emailSender.send(message);
    }

    public void replyMessage(String email, String replyMessage) {
        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Reply from cheese-alcoholic website");
        message.setText(replyMessage);

        // Send Message!
        emailSender.send(message);
    }


    public void newPassword (String email, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("your new Password");
        message.setText("we send you your new Password: " + newPassword);

        emailSender.send(message);

    }

    public void verifyOrder (String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("From cheese-alcoholic website");
        message.setText("your order is success!, we will call you soon!");

        emailSender.send(message);

    }
}
