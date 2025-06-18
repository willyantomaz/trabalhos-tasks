package com.example.minitrello.service;

import com.example.minitrello.model.Tarefa;
import com.example.minitrello.repository.TarefaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    @Transactional(readOnly = true)
    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    @Transactional
    public Tarefa cadastrar(Tarefa tarefa) {
        tarefa.setCreatedAt(LocalDateTime.now());
        return tarefaRepository.save(tarefa);
    }

    @Transactional
    public Tarefa atualizarStatus(Long id, Tarefa.Status novoStatus) {
        Tarefa tarefaExistente = tarefaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada com o id: " + id));

        tarefaExistente.setStatus(novoStatus);
        tarefaExistente.setUpdatedAt(LocalDateTime.now());

        return tarefaRepository.save(tarefaExistente);
    }

    @Transactional
    public void remover(Long id) {
        if (!tarefaRepository.existsById(id)) {
            throw new EntityNotFoundException("Tarefa não encontrada com o id: " + id);
        }
        tarefaRepository.deleteById(id);
    }
}