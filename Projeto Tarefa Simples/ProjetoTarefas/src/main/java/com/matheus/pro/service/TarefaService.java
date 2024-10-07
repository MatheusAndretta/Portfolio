package com.matheus.pro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matheus.pro.model.Tarefa;
import com.matheus.pro.repository.TarefaRepository;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

   

    public List<Tarefa> getTodasTarefas() {
        return tarefaRepository.findAll();
    }

    public Optional<Tarefa> getTarefaPorID(String id) {
        return tarefaRepository.findById(id);
    }

    public Tarefa criarTarefa(Tarefa tarefa) {
        tarefa.setCompletada(false);
        return tarefaRepository.save(tarefa);
    } 

    public Tarefa atualizarTarefa(String id, Tarefa tarefa) {
        tarefa.setId(id);
        return tarefaRepository.save(tarefa);
    }

    public void deletaTarefaPorID(String id) {
        tarefaRepository.deleteById(id);
    }




}
