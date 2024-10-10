package com.ms.user.controller;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ms.user.dto.UserDTO;
import com.ms.user.dto.UserDTODel;
import com.ms.user.model.UserModel;
import com.ms.user.service.UserService;

import jakarta.validation.Valid;

/**
 * Controlador de usuários.
 * 
 * Esta classe é responsável por gerenciar as operações relacionadas a usuários,
 * incluindo a criação e a exclusão de usuários através de endpoints REST.
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Endpoint para criar um novo usuário.
     * 
     * Este método recebe os dados do usuário em formato JSON, mapeia-os para
     * um modelo de usuário e o salva no sistema.
     * 
     * @param userDTO O objeto que contém os dados do novo usuário.
     * @return Uma resposta contendo o usuário criado e o status HTTP 201 (Criado).
     */
    @PostMapping("/create")
    public ResponseEntity<UserModel> criarUser(@RequestBody @Valid UserDTO userDTO) {
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDTO, userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
    }

    /**
     * Endpoint para deletar um usuário.
     * 
     * Este método recebe os dados do usuário a ser deletado e, se encontrado,
     * remove-o do sistema.
     * 
     * @param userDTO O objeto que contém os dados do usuário a ser deletado.
     * @return Uma resposta contendo o usuário deletado e o status HTTP 200 (OK).
     * @throws UserPrincipalNotFoundException Se o usuário não for encontrado.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<UserModel> deleteUser(@RequestBody @Valid UserDTODel userDTO)
            throws UserPrincipalNotFoundException {
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDTO, userModel);

        UserModel deleteUser = userService.delete(userModel);

        return ResponseEntity.ok(deleteUser);

    }

}
