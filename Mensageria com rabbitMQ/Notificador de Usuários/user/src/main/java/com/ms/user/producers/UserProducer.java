package com.ms.user.producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ms.user.dto.EmailDTO;
import com.ms.user.model.UserModel;

/**
 * Componente responsável por produzir mensagens relacionadas a usuários,
 * enviando notificações por e-mail através do RabbitMQ.
 */
@Component
public class UserProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    /**
     * Publica uma mensagem de e-mail para o usuário que foi cadastrado.
     *
     * @param userModel O modelo do usuário que foi cadastrado.
     */
    public void publishMessageEmail(UserModel userModel) {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setUserID(userModel.getUserID());
        emailDTO.setEmailTo(userModel.getEmail());
        emailDTO.setSubject("Cadastro realizado com sucesso!");
        emailDTO.setText(userModel.getName()
                + ", seja bem vindo(a)! \nAgradecemos o seu cadastro, aproveite agora todos os recursos da nossa plataforma!");

        rabbitTemplate.convertAndSend("", routingKey, emailDTO);
    }

    /**
     * Publica uma mensagem de e-mail para o usuário informando que sua conta foi
     * desativada.
     *
     * @param userModel O modelo do usuário cuja conta foi desativada.
     */
    public void publishMessageEmailDelete(UserModel userModel) {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setUserID(userModel.getUserID());
        emailDTO.setEmailTo(userModel.getEmail());
        emailDTO.setSubject("Sua Conta foi desativada");
        emailDTO.setText(userModel.getName()
                + ", sua conta sera desativada em até 24H! ");

        rabbitTemplate.convertAndSend("", routingKey, emailDTO);
    }

}
