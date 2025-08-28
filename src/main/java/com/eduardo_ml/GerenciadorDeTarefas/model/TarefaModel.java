package com.eduardo_ml.GerenciadorDeTarefas.model;

import com.eduardo_ml.GerenciadorDeTarefas.model.enums.Prioridade;
import com.eduardo_ml.GerenciadorDeTarefas.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class TarefaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título da tarefa não pode estar em branco.")
    private String titulo;

    @NotBlank(message = "A descrição da tarefa não pode estar em branco.")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O status da tarefa não pode estar nulo.")
    private Status status;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "A prioridade da tarefa não pode estar nula.")
    private Prioridade prioridade;

    @NotNull(message = "A data de vencimento não pode estar nula.")
    private LocalDate dataDeVencimento;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel usuario;
}
