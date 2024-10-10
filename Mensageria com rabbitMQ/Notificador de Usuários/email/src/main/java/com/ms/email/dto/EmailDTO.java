package com.ms.email.dto;

import java.util.UUID;

/**
 * Data Transfer Object (DTO) para e-mails.
 * 
 * Esta classe é usada para transferir dados relacionados ao envio de e-mails.
 * Ela encapsula as informações necessárias para compor um e-mail, incluindo
 * o identificador do usuário, o destinatário, o assunto e o corpo da mensagem.
 * 
 * @param userID  O identificador único do usuário que está enviando o e-mail.
 * @param emailTo O endereço de e-mail do destinatário.
 * @param subject O assunto do e-mail.
 * @param text    O corpo do e-mail.
 */
public record EmailDTO(UUID userID, String emailTo, String subject, String text) {

}
