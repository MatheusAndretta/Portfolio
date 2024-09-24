package br.com.matheus.vendas.online.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.com.matheus.vendas.online.infra.security.TokenService;
import br.com.matheus.vendas.online.usercase.UserValidation;
import io.swagger.v3.oas.annotations.Operation;
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserValidation validation;

    private final TokenService tokenService;

    
    @Autowired
    public UserController(UserValidation validation, TokenService tokenService) {
        this.validation = validation;
        this.tokenService = tokenService;
    }



    @GetMapping("/{email}/{token}")
    @Operation(summary = "/user so pode ser permitido se o usuario passa pela validação de email e token")
    public ResponseEntity<Boolean> isPermitido( 
        @PathVariable String email,
        @PathVariable String token){
        String tokenSubject = tokenService.validateToken(token);    
        boolean isvalid = tokenSubject != null && tokenSubject.equals(email) && validation.isValidado(email);
        return ResponseEntity.ok(isvalid);
    }

    @GetMapping
    public ResponseEntity<String> getUser(){
        return ResponseEntity.ok("sucesso!");
    }
}
