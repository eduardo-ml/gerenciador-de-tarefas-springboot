package com.eduardo_ml.GerenciadorDeTarefas.service;

import com.eduardo_ml.GerenciadorDeTarefas.dto.TarefaDTO;
import com.eduardo_ml.GerenciadorDeTarefas.exception.ResourceNotFoundException;
import com.eduardo_ml.GerenciadorDeTarefas.mapper.TarefaMapper;
import com.eduardo_ml.GerenciadorDeTarefas.model.TarefaModel;
import com.eduardo_ml.GerenciadorDeTarefas.model.UsuarioModel;
import com.eduardo_ml.GerenciadorDeTarefas.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarefaService {
    private final TarefaRepository repository;
    private final TarefaMapper tarefaMapper;

    public TarefaService(TarefaRepository repository, TarefaMapper tarefaMapper) {
        this.repository = repository;
        this.tarefaMapper = tarefaMapper;
    }

    public TarefaDTO salvarTarefa(TarefaDTO tarefaDTO, UsuarioModel usuario) {
        TarefaModel tarefa = tarefaMapper.toEntity(tarefaDTO);
        tarefa.setUsuario(usuario);
        TarefaModel tarefaSalva = repository.save(tarefa);

        return tarefaMapper.toDTO(tarefaSalva);
    }

    public List<TarefaDTO> listarTarefasDoUsuario(Long usuarioId) {
        List<TarefaModel> tarefas = repository.findAllByUsuarioId(usuarioId);
        return tarefas.stream()
                .map(tarefaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TarefaDTO buscarPorId(Long id) {
        TarefaModel tarefa = buscarTarefaPorId(id);
        return tarefaMapper.toDTO(tarefa);
    }

    public TarefaDTO atualizarTarefa(Long id, TarefaDTO tarefaDTO) {
        TarefaModel tarefaExistente = buscarTarefaPorId(id);

        tarefaExistente.setTitulo(tarefaDTO.getTitulo());
        tarefaExistente.setDescricao(tarefaDTO.getDescricao());
        tarefaExistente.setStatus(tarefaDTO.getStatus());
        tarefaExistente.setPrioridade(tarefaDTO.getPrioridade());
        tarefaExistente.setDataDeVencimento(tarefaDTO.getDataDeVencimento());

        TarefaModel tarefaAtualizada = repository.save(tarefaExistente);

        return tarefaMapper.toDTO(tarefaAtualizada);
    }

    public void excluirTarefa(Long id) {
        buscarTarefaPorId(id);
        repository.deleteById(id);
    }

    public TarefaModel buscarTarefaPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa com id"  + id + " não encontrada."));
    }
}