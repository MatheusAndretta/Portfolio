package com.ms.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) para o usuário.
 * 
 * Esta classe é utilizada para transferir informações sobre um usuário
 * entre diferentes camadas da aplicação, especialmente para operações
 * de criação e validação.
 */
public record UserDTO(@NotBlank String name,
        @NotBlank @Email String email) {

}
