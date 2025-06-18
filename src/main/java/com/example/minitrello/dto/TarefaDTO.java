package com.example.minitrello.dto;

import com.example.minitrello.model.Tarefa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TarefaDTO(
        Long id,
        @NotBlank @Size(max = 100) String titulo,
        @Size(max = 500) String descricao,
        Tarefa.Status status
) {
    public Tarefa toEntity() {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(this.id);
        tarefa.setTitulo(this.titulo);
        tarefa.setDescricao(this.descricao);
        tarefa.setStatus(this.status != null ? this.status : Tarefa.Status.PENDENTE);
        return tarefa;
    }
}