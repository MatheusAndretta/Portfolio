package com.ms.email.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe de configuração do RabbitMQ.
 * 
 * Esta classe é responsável por definir a fila utilizada para envio de
 * mensagens
 * de e-mail e configurar o conversor de mensagens para o formato JSON.
 */
@Configuration
public class RabbitMQConfig {
    /**
     * Nome da fila de e-mails, injetado a partir das propriedades da aplicação.
     */
    @Value("${broker.queue.email.name}")
    private String queue;

    /**
     * Cria uma nova fila com o nome especificado.
     * 
     * A fila é configurada como durável, ou seja, não será perdida após
     * uma reinicialização do broker.
     * 
     * @return Uma instância da fila.
     */
    @Bean
    public Queue queue() {
        return new Queue(queue, true);
    }

    /**
     * Configura um conversor de mensagens que utiliza o ObjectMapper do Jackson
     * para converter objetos Java em JSON e vice-versa.
     * 
     * @return Um conversor de mensagens JSON.
     */
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        return new Jackson2JsonMessageConverter(objectMapper);
    }

}
