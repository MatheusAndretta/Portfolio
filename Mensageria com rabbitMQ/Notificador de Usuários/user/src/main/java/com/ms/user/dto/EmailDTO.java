package com.ms.user.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) para Email.
 * 
 * Esta classe é utilizada para transferir informações relacionadas ao
 * envio de e-mails entre diferentes camadas da aplicação.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {

    private UUID userID;
    private String emailTo;
    private String subject;
    private String text;

}
