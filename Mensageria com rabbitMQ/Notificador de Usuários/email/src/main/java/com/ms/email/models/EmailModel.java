package com.ms.email.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Modelo de e-mail.
 * 
 * Esta classe representa um e-mail no sistema. Ela contém informações
 * sobre o e-mail, como remetente, destinatário, assunto, corpo do e-mail
 * e o status do envio. É uma entidade persistente mapeada para a tabela
 * "TB_EMAILS".
 */
@Entity
@Table(name = "TB_EMAILS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailModel implements Serializable {

    public enum StatusEmail {
        SENT,
        ERROR
    }

    private static final long serialVersionUID = 1l;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID emailID;
    private UUID userID;
    private String emailFrom;
    private String emailTo;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String text;
    private LocalDateTime senDateEmail;
    private StatusEmail statusEmail;

}
