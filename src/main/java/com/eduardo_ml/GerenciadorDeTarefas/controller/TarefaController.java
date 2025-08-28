package com.eduardo_ml.GerenciadorDeTarefas.controller;

import com.eduardo_ml.GerenciadorDeTarefas.dto.TarefaDTO;
import com.eduardo_ml.GerenciadorDeTarefas.model.UsuarioModel;
import com.eduardo_ml.GerenciadorDeTarefas.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PostMapping
    public ResponseEntity<TarefaDTO> salvarTarefa(@Valid @RequestBody TarefaDTO tarefaDTO, @AuthenticationPrincipal UsuarioModel usuario) {
        TarefaDTO novaTarefa = tarefaService.salvarTarefa(tarefaDTO, usuario);
        return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TarefaDTO>> listarTarefasDoUsuario(@AuthenticationPrincipal UsuarioModel usuario) {
        List<TarefaDTO> tarefas = tarefaService.listarTarefasDoUsuario(usuario.getId());
        return ResponseEntity.ok(tarefas);
    }

    @GetMapping("{id}")
    public ResponseEntity<TarefaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.buscarPorId(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<TarefaDTO> atualizarTarefa(@PathVariable Long id, @Valid @RequestBody TarefaDTO tarefaDTO) {
        TarefaDTO tarefaAtualizada = tarefaService.atualizarTarefa(id, tarefaDTO);
        return ResponseEntity.ok(tarefaAtualizada);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> excluirTarefa(@PathVariable Long id) {
        tarefaService.excluirTarefa(id);
        return ResponseEntity.noContent().build();
    }
}