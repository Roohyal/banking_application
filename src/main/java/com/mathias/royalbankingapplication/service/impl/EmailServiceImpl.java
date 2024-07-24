package com.mathias.royalbankingapplication.service.impl;

import com.mathias.royalbankingapplication.payload.request.EmailDetails;
import com.mathias.royalbankingapplication.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;


    @Override
    public void sendEmailAlert(EmailDetails emailDetails) {
      try {
          SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

          simpleMailMessage.setFrom(senderEmail);
          simpleMailMessage.setTo(emailDetails.getRecipient());
          simpleMailMessage.setText(emailDetails.getMessageBody());
          simpleMailMessage.setSubject(emailDetails.getSubject());

          mailSender.send(simpleMailMessage);
      } catch (MailException e) {
          throw new RuntimeException(e);
      }
    }
}
