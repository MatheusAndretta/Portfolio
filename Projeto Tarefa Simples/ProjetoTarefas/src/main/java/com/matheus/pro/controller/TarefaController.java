package com.matheus.pro.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheus.pro.model.Tarefa;
import com.matheus.pro.repository.TarefaRepository;
import com.matheus.pro.service.TarefaService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/tarefa")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private TarefaRepository tarefaRepository;

    @GetMapping
    @Operation(summary = "Lista todas as tarefas")
    public List<Tarefa> getTodasTarefas() {
        return tarefaService.getTodasTarefas();
    }

    @PostMapping()
    @Operation(summary = "Criar uma nova tarefa")
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) {
        Tarefa tarefaCriada = tarefaService.criarTarefa(tarefa);
        return new ResponseEntity<>(tarefaCriada, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tarefa por ID")
    public ResponseEntity<Tarefa> getTarefaPorID(@PathVariable String id) {
        return tarefaService.getTarefaPorID(id)
                .map(tarefa -> new ResponseEntity<>(tarefa, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tarefa por ID")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable String id, @RequestBody Tarefa tarefa) {
        Tarefa tarefaAtualizada = tarefaService.atualizarTarefa(id, tarefa);
        return new ResponseEntity<>(tarefaAtualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta tarefa por ID")
    public ResponseEntity<Void> deletaTarefa(@PathVariable String id) {
        tarefaService.deletaTarefaPorID(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/tarefas/não-completadas")
    @Operation(summary = "Lista somente tarefas não completadas")
    public ResponseEntity<List<Tarefa>> listarTarefasNaoCompletadas() {
        List<Tarefa> tarefasNaoCompletadas = tarefaRepository.findByCompletada(false);
        return ResponseEntity.ok(tarefasNaoCompletadas);
    }

    @GetMapping("/api/tarefas/completadas")
    @Operation(summary = "Lista somente tarefas completadas")
    public ResponseEntity<List<Tarefa>> listarTarefasCompletadas() {
        List<Tarefa> tarefasCompletadas = tarefaRepository.findByCompletada(true);
        return ResponseEntity.ok(tarefasCompletadas);
    }

    @PatchMapping("/api/tarefa/{id}/completada")
    @Operation(summary = "Completa uma tarefa por ID")
    public ResponseEntity<Tarefa> tarefacompletada(@PathVariable String id) {
        Optional<Tarefa> tarefaOpt = tarefaRepository.findById(id);
        if (tarefaOpt.isPresent()) {
            Tarefa tarefa = tarefaOpt.get();
            tarefa.setCompletada(true);
            tarefaRepository.save(tarefa);
            return ResponseEntity.ok(tarefa);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
