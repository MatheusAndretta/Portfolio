package com.ms.email.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ms.email.models.EmailModel;
import com.ms.email.models.EmailModel.StatusEmail;
import com.ms.email.repository.EmailRepository;

/**
 * Serviço de envio de e-mails.
 * 
 * Esta classe é responsável por gerenciar o envio de e-mails no sistema.
 * Ela encapsula a lógica para criar, enviar e salvar e-mails no repositório.
 */
@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    JavaMailSender emailSender;

    @Value(value = "${spring.mail.username}")
    private String emailFrom;

    /**
     * Envia um e-mail.
     * 
     * Este método configura os detalhes do e-mail, tenta enviá-lo e
     * atualiza o status do e-mail conforme o resultado do envio.
     * 
     * @param emailModel O modelo de e-mail contendo os detalhes a serem enviados.
     * @return O modelo de e-mail atualizado com a data de envio e o status.
     */
    public EmailModel sendEmail(EmailModel emailModel) {
        try {
            emailModel.setSenDateEmail(LocalDateTime.now());
            emailModel.setEmailFrom(emailFrom);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
        } finally {
            return emailRepository.save(emailModel);
        }
    }
}
