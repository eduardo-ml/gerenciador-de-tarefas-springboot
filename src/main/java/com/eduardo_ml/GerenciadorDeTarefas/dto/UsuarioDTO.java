package com.eduardo_ml.GerenciadorDeTarefas.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {

    private Long id;
    private String nomeDoUsuario;
    private String senha;
    private String email;
}
