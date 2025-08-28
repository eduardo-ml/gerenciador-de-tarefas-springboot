package com.eduardo_ml.GerenciadorDeTarefas.repository;

import com.eduardo_ml.GerenciadorDeTarefas.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    Optional<UsuarioModel> findByEmail(String email);
}
