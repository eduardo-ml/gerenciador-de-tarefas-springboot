package com.eduardo_ml.GerenciadorDeTarefas.controller;

import com.eduardo_ml.GerenciadorDeTarefas.dto.LoginRequestDTO;
import com.eduardo_ml.GerenciadorDeTarefas.dto.LoginResponseDTO;
import com.eduardo_ml.GerenciadorDeTarefas.model.UsuarioModel;
import com.eduardo_ml.GerenciadorDeTarefas.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getSenha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var usuario = (UsuarioModel) auth.getPrincipal();
        var token = jwtService.generateToken(usuario);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}