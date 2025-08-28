package com.eduardo_ml.GerenciadorDeTarefas.mapper;

import com.eduardo_ml.GerenciadorDeTarefas.dto.UsuarioDTO;
import com.eduardo_ml.GerenciadorDeTarefas.model.UsuarioModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    UsuarioModel toEntity(UsuarioDTO usuarioDTO);

    UsuarioDTO toDTO(UsuarioModel usuarioModel);
}