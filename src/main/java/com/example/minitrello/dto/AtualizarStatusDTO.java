package com.example.minitrello.dto;

import com.example.minitrello.model.Tarefa;
import jakarta.validation.constraints.NotNull;


public record AtualizarStatusDTO(
        @NotNull(message = "O novo status não pode ser nulo.")
        Tarefa.Status novoStatus
) {}