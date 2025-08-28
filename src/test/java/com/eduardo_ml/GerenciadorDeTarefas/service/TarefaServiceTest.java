package com.eduardo_ml.GerenciadorDeTarefas.service;

import com.eduardo_ml.GerenciadorDeTarefas.dto.TarefaDTO;
import com.eduardo_ml.GerenciadorDeTarefas.exception.ResourceNotFoundException;
import com.eduardo_ml.GerenciadorDeTarefas.mapper.TarefaMapper;
import com.eduardo_ml.GerenciadorDeTarefas.model.TarefaModel;
import com.eduardo_ml.GerenciadorDeTarefas.model.UsuarioModel;
import com.eduardo_ml.GerenciadorDeTarefas.model.enums.Status;
import com.eduardo_ml.GerenciadorDeTarefas.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private TarefaMapper tarefaMapper;

    @InjectMocks
    private TarefaService tarefaService;

    private TarefaDTO tarefaDTO;
    private TarefaModel tarefaModel;
    private UsuarioModel usuarioModel;

    @BeforeEach
    void setUp() {
        usuarioModel = new UsuarioModel();
        usuarioModel.setId(1L);
        usuarioModel.setNomeDoUsuario("Usuario Teste");
        usuarioModel.setEmail("teste@email.com");

        tarefaDTO = new TarefaDTO();
        tarefaDTO.setId(1L);
        tarefaDTO.setTitulo("Teste Tarefa");
        tarefaDTO.setDescricao("Descrição da tarefa");
        tarefaDTO.setStatus(Status.PENDENTE);

        tarefaModel = new TarefaModel();
        tarefaModel.setId(1L);
        tarefaModel.setTitulo("Teste Tarefa");
        tarefaModel.setDescricao("Descrição da tarefa");
        tarefaModel.setStatus(Status.PENDENTE);
        tarefaModel.setUsuario(usuarioModel);
    }

    @Test
    void deveSalvarTarefaComSucesso() {
        when(tarefaMapper.toEntity(any(TarefaDTO.class))).thenReturn(tarefaModel);
        when(tarefaRepository.save(any(TarefaModel.class))).thenReturn(tarefaModel);
        when(tarefaMapper.toDTO(any(TarefaModel.class))).thenReturn(tarefaDTO);

        TarefaDTO resultado = tarefaService.salvarTarefa(new TarefaDTO(), usuarioModel);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(tarefaRepository, times(1)).save(argThat(
                savedTarefa -> savedTarefa.getUsuario() != null && savedTarefa.getUsuario().getId().equals(1L)
        ));
    }

    @Test
    void deveListarTarefasDoUsuario() {
        when(tarefaRepository.findAllByUsuarioId(1L)).thenReturn(Collections.singletonList(tarefaModel));
        when(tarefaMapper.toDTO(tarefaModel)).thenReturn(tarefaDTO);

        List<TarefaDTO> resultado = tarefaService.listarTarefasDoUsuario(1L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Teste Tarefa", resultado.get(0).getTitulo());
        verify(tarefaRepository, times(1)).findAllByUsuarioId(1L);
    }

    @Test
    void deveBuscarTarefaPorIdComSucesso() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefaModel));
        when(tarefaMapper.toDTO(tarefaModel)).thenReturn(tarefaDTO);

        TarefaDTO resultado = tarefaService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void deveLancarExcecaoAoBuscarTarefaInexistente() {
        when(tarefaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            tarefaService.buscarPorId(99L);
        });
    }

    @Test
    void deveAtualizarTarefaComSucesso() {
        TarefaDTO dadosAtualizadosDTO = new TarefaDTO();
        dadosAtualizadosDTO.setTitulo("Tarefa Atualizada");
        dadosAtualizadosDTO.setStatus(Status.EM_PROGRESSO);

        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefaModel));
        when(tarefaRepository.save(any(TarefaModel.class))).thenReturn(tarefaModel);
        when(tarefaMapper.toDTO(tarefaModel)).thenReturn(tarefaDTO);

        TarefaDTO resultado = tarefaService.atualizarTarefa(1L, dadosAtualizadosDTO);

        assertNotNull(resultado);
        verify(tarefaRepository).findById(1L);
        verify(tarefaRepository).save(argThat(savedTarefa ->
                "Tarefa Atualizada".equals(savedTarefa.getTitulo()) &&
                        Status.EM_PROGRESSO.equals(savedTarefa.getStatus())
        ));
    }

    @Test
    void deveExcluirTarefaComSucesso() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefaModel));
        doNothing().when(tarefaRepository).deleteById(1L);

        tarefaService.excluirTarefa(1L);

        verify(tarefaRepository, times(1)).findById(1L);
        verify(tarefaRepository, times(1)).deleteById(1L);
    }
}