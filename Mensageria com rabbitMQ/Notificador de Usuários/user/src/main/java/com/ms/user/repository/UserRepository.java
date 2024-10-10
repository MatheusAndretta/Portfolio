package com.ms.user.repository;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.user.model.UserModel;

/**
 * Repositório para gerenciamento das operações de persistência da entidade UserModel.
 * Esta interface estende JpaRepository, permitindo operações CRUD e consultas personalizadas.
 */
public interface UserRepository extends JpaRepository<UserModel, UUID> {

}
