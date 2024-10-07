package com.matheus.pro.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.matheus.pro.model.Tarefa;

public interface TarefaRepository extends MongoRepository<Tarefa,String>{

    List<Tarefa> findByCompletada(boolean completada);

    List<Tarefa> findByTituloContaining(String titulo);

}
