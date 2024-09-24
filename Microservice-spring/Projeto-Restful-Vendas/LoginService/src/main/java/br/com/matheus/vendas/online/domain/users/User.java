package br.com.matheus.vendas.online.domain.users;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(value = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {

    @Id
    private String id;

    private String name;

    private String email;

    private String password;
}
