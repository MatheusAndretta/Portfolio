package com.matheus.pro.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(value = "tarefa")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tarefa {

    @Id
    private String id;
    private String titulo;
    private String descricao;
    private boolean completada;

}
