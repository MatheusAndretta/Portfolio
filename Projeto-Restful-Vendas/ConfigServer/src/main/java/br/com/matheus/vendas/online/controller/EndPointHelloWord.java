package br.com.matheus.vendas.online.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EndPointHelloWord {

    @GetMapping(value = "/hello")
    public String getHello(){
        return "Olá mundo";
    }
}
