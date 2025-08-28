package com.eduardo_ml.GerenciadorDeTarefas.service;

import com.eduardo_ml.GerenciadorDeTarefas.dto.UsuarioDTO;
import com.eduardo_ml.GerenciadorDeTarefas.exception.ResourceNotFoundException;
import com.eduardo_ml.GerenciadorDeTarefas.mapper.UsuarioMapper;
import com.eduardo_ml.GerenciadorDeTarefas.model.UsuarioModel;
import com.eduardo_ml.GerenciadorDeTarefas.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioMapper usuarioMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    private UsuarioDTO usuarioDTO;
    private UsuarioModel usuarioModel;

    @BeforeEach
    void setUp() {
        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(1L);
        usuarioDTO.setNomeDoUsuario("Teste");
        usuarioDTO.setSenha("123");
        usuarioDTO.setEmail("teste@email.com");

        usuarioModel = new UsuarioModel();
        usuarioModel.setId(1L);
        usuarioModel.setNomeDoUsuario("Teste");
        usuarioModel.setSenha("senha_criptografada");
        usuarioModel.setEmail("teste@email.com");
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        UsuarioDTO usuarioParaSalvar = new UsuarioDTO();
        usuarioParaSalvar.setNomeDoUsuario("Teste");
        usuarioParaSalvar.setSenha("123");
        usuarioParaSalvar.setEmail("teste@email.com");

        when(usuarioMapper.toEntity(any(UsuarioDTO.class))).thenReturn(usuarioModel);
        when(passwordEncoder.encode(anyString())).thenReturn("senha_criptografada");
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuarioModel);
        when(usuarioMapper.toDTO(any(UsuarioModel.class))).thenReturn(usuarioDTO);

        UsuarioDTO resultado = usuarioService.salvarUsuario(usuarioParaSalvar);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(usuarioRepository, times(1)).save(usuarioModel);
        verify(passwordEncoder, times(1)).encode("123");
    }

    @Test
    void deveListarTodosOsUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(Collections.singletonList(usuarioModel));
        when(usuarioMapper.toDTO(usuarioModel)).thenReturn(usuarioDTO);

        List<UsuarioDTO> resultado = usuarioService.listarTodosOsUsuarios();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Teste", resultado.get(0).getNomeDoUsuario());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void deveBuscarUsuarioPorIdComSucesso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioModel));
        when(usuarioMapper.toDTO(usuarioModel)).thenReturn(usuarioDTO);

        UsuarioDTO resultado = usuarioService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void deveLancarExcecaoAoBuscarUsuarioInexistente() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            usuarioService.buscarPorId(99L);
        });
    }

    @Test
    void deveAtualizarUsuarioComSucesso() {
        UsuarioDTO dadosAtualizadosDTO = new UsuarioDTO();
        dadosAtualizadosDTO.setNomeDoUsuario("Usuario Atualizado");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioModel));
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuarioModel);
        when(usuarioMapper.toDTO(usuarioModel)).thenReturn(usuarioDTO);

        UsuarioDTO resultado = usuarioService.atualizarUsuario(1L, dadosAtualizadosDTO);

        assertNotNull(resultado);
        verify(usuarioRepository).findById(1L);
        verify(usuarioRepository).save(argThat(savedUser -> "Usuario Atualizado".equals(savedUser.getNomeDoUsuario())));
    }

    @Test
    void deveExcluirUsuarioComSucesso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioModel));
        doNothing().when(usuarioRepository).deleteById(1L);

        usuarioService.excluirUsuario(1L);

        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }
}