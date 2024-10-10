package com.ms.email.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.email.dto.EmailDTO;
import com.ms.email.models.EmailModel;
import com.ms.email.services.EmailService;

/**
 * Consumidor de mensagens de e-mail.
 * 
 * Esta classe é responsável por escutar a fila de e-mails e processar
 * as mensagens recebidas. Quando uma mensagem é recebida, ela é convertida
 * em um objeto EmailModel e enviada pelo serviço de e-mail.
 */
@Component
public class EmailConsumer {

    @Autowired
    EmailService emailService;

    /**
     * Escuta a fila de e-mails e processa as mensagens recebidas.
     * 
     * Este método é acionado automaticamente quando uma nova mensagem
     * é publicada na fila. A mensagem é convertida de DTO para modelo
     * e passada para o serviço de e-mail para envio.
     * 
     * @param emailDTO O objeto DTO contendo os dados do e-mail a ser enviado.
     */
    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailDTO emailDTO) {
        var emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDTO, emailModel);
        emailService.sendEmail(emailModel);
    }

}
