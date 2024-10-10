package com.ms.user.dto;

import java.util.UUID;
/**
 * Data Transfer Object (DTO) para a exclusão de um usuário.
 * 
 * Esta classe é utilizada para transferir informações necessárias para
 * a remoção de um usuário da aplicação.
 */
public record UserDTODel(UUID userID, String name, String email) {

}
