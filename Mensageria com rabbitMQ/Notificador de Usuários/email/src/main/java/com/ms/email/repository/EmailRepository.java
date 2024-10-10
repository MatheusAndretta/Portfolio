package com.ms.email.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.email.models.EmailModel;

/**
 * Repositório de e-mails.
 * 
 * Esta interface é responsável por fornecer operações de acesso a dados
 * para a entidade EmailModel. Ela estende JpaRepository, que fornece
 * métodos prontos para realizar operações de CRUD e consulta.
 */
public interface EmailRepository extends JpaRepository<EmailModel, UUID> {

}
