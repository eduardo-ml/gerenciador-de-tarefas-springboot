package com.eduardo_ml.GerenciadorDeTarefas.dto;

import com.eduardo_ml.GerenciadorDeTarefas.model.enums.Prioridade;
import com.eduardo_ml.GerenciadorDeTarefas.model.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TarefaDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private Status status;
    private Prioridade prioridade;
    private LocalDate dataDeVencimento;
}