package com.example.minitrello.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "tarefas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(length = 500)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDENTE;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    public enum Status {
        PENDENTE,
        EM_ANDAMENTO,
        CONCLUIDA
    }
}