package com.example.minitrello.dto;

import com.example.minitrello.model.Tarefa;
import java.time.LocalDateTime;


public record TarefaResponseDTO(
        Long id,
        String titulo,
        String descricao,
        Tarefa.Status status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public TarefaResponseDTO(Tarefa tarefa) {
        this(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getStatus(),
                tarefa.getCreatedAt(),
                tarefa.getUpdatedAt()
        );
    }
}