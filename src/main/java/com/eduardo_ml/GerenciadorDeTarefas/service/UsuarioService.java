package com.eduardo_ml.GerenciadorDeTarefas.service;

import com.eduardo_ml.GerenciadorDeTarefas.dto.UsuarioDTO;
import com.eduardo_ml.GerenciadorDeTarefas.exception.ResourceNotFoundException;
import com.eduardo_ml.GerenciadorDeTarefas.mapper.UsuarioMapper;
import com.eduardo_ml.GerenciadorDeTarefas.model.UsuarioModel;
import com.eduardo_ml.GerenciadorDeTarefas.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, UsuarioMapper usuarioMapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        UsuarioModel usuario = usuarioMapper.toEntity(usuarioDTO);
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        UsuarioModel usuarioSalvo = repository.save(usuario);

        return usuarioMapper.toDTO(usuarioSalvo);
    }

    public List<UsuarioDTO> listarTodosOsUsuarios() {
        List<UsuarioModel> usuarios = repository.findAll();
        List<UsuarioDTO> dtos = new ArrayList<>();

        for (UsuarioModel usuario : usuarios) {
            dtos.add(usuarioMapper.toDTO(usuario));
        }

        return dtos;
    }

    public UsuarioDTO buscarPorId(Long id) {
        UsuarioModel usuario = buscarUsuarioPorId(id);

        return usuarioMapper.toDTO(usuario);
    }

    public UsuarioDTO atualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        UsuarioModel usuarioExistente = buscarUsuarioPorId(id);

        usuarioExistente.setNomeDoUsuario(usuarioDTO.getNomeDoUsuario());
        if (usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().isEmpty()) {
            usuarioExistente.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        }
        usuarioExistente.setEmail(usuarioDTO.getEmail());

        UsuarioModel usuarioAtualizado = repository.save(usuarioExistente);

        return usuarioMapper.toDTO(usuarioAtualizado);
    }

    public void excluirUsuario(Long id) {
        buscarUsuarioPorId(id);
        repository.deleteById(id);
    }

    public UsuarioModel buscarUsuarioPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario com id " + id + " não encontrado."));
    }
}
