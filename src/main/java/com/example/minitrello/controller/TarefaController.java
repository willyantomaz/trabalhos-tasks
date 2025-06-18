package com.example.minitrello.controller;

import com.example.minitrello.dto.AtualizarStatusDTO;
import com.example.minitrello.dto.CriarTarefaDTO;
import com.example.minitrello.dto.TarefaResponseDTO;
import com.example.minitrello.model.Tarefa;
import com.example.minitrello.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PostMapping
    public ResponseEntity<TarefaResponseDTO> cadastrarTarefa(@Valid @RequestBody CriarTarefaDTO dto) {
        Tarefa novaTarefa = new Tarefa();
        novaTarefa.setTitulo(dto.titulo());
        novaTarefa.setDescricao(dto.descricao());

        Tarefa tarefaSalva = tarefaService.cadastrar(novaTarefa);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tarefaSalva.getId())
                .toUri();

        return ResponseEntity.created(location).body(new TarefaResponseDTO(tarefaSalva));
    }

    @GetMapping
    public ResponseEntity<List<TarefaResponseDTO>> listarTarefas() {
        List<Tarefa> tarefas = tarefaService.listarTodas();

        // Converte a lista de entidades para uma lista de DTOs
        List<TarefaResponseDTO> dtos = tarefas.stream()
                .map(TarefaResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TarefaResponseDTO> atualizarStatusTarefa(@PathVariable Long id, @Valid @RequestBody AtualizarStatusDTO dto) {
        Tarefa tarefaAtualizada = tarefaService.atualizarStatus(id, dto.novoStatus());
        return ResponseEntity.ok(new TarefaResponseDTO(tarefaAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerTarefa(@PathVariable Long id) {
        tarefaService.remover(id);
        return ResponseEntity.noContent().build();
    }
}