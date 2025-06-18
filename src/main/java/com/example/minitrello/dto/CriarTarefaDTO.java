package com.example.minitrello.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriarTarefaDTO(
        @NotBlank(message = "O título não pode ser vazio.")
        @Size(max = 100, message = "O título deve ter no máximo 100 caracteres.")
        String titulo,

        @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres.")
        String descricao
) {}