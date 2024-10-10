package com.ms.user.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Configuração do RabbitMQ.
 * 
 * Esta classe contém a configuração necessária para o uso do RabbitMQ
 * no aplicativo. Ela define um conversor de mensagens que utiliza o
 * Jackson para serialização e desserialização de objetos JSON.
 */
@Configuration
public class RabbitMQconfig {
    /**
     * Bean que fornece um conversor de mensagens para RabbitMQ.
     * 
     * Este conversor é utilizado para converter mensagens em formato
     * JSON usando o ObjectMapper do Jackson.
     * 
     * @return Um conversor de mensagens configurado para trabalhar com JSON.
     */
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        return new Jackson2JsonMessageConverter(objectMapper);
    }

}
