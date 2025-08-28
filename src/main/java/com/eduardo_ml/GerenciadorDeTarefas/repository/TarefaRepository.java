package com.eduardo_ml.GerenciadorDeTarefas.repository;

import com.eduardo_ml.GerenciadorDeTarefas.model.TarefaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefaRepository extends JpaRepository<TarefaModel, Long> {
    List<TarefaModel> findAllByUsuarioId(Long usuarioId);
}
