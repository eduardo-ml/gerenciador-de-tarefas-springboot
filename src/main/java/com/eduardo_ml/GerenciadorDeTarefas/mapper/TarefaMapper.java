package com.eduardo_ml.GerenciadorDeTarefas.mapper;

import com.eduardo_ml.GerenciadorDeTarefas.dto.TarefaDTO;
import com.eduardo_ml.GerenciadorDeTarefas.model.TarefaModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TarefaMapper {

    TarefaMapper INSTANCE = Mappers.getMapper(TarefaMapper.class);

    TarefaModel toEntity(TarefaDTO tarefaDTO);

    TarefaDTO toDTO(TarefaModel tarefaModel);
}