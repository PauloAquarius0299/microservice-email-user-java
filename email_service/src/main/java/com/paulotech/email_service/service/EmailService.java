package com.paulotech.email_service.service;

import com.paulotech.email_service.enums.StatusEmail;
import com.paulotech.email_service.models.EmailModel;
import com.paulotech.email_service.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EmailService {

     final EmailRepository emailRepository;
     final JavaMailSender emailSender;

    // Constructor
    public EmailService(EmailRepository emailRepository, JavaMailSender emailSender) {
        this.emailRepository = emailRepository;
        this.emailSender = emailSender;
    }

    @Value(value ="${spring.mail.username}")
    private String emailForm;

    @Transactional
    public EmailModel sendEmail(EmailModel emailModel) {
        try {
            // Configuração de envio
            emailModel.setSendDateEmail(LocalDateTime.now());
            emailModel.setEmailForm(emailForm);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());

            // Enviar email
            emailSender.send(message);

            // Atualizar status como enviado
            emailModel.setStatusEmail(StatusEmail.SENT);

        } catch (MailException e) {
            // Atualizar status como erro
            emailModel.setStatusEmail(StatusEmail.ERROR);

            // Logar a exceção
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();

        } finally {
            // Salvar o modelo independentemente do sucesso ou falha
            return emailRepository.save(emailModel);
        }
    }
}
