package com.arthur.sugestoes.service;

import com.arthur.sugestoes.controller.dto.request.comentarioRequest;
import com.arthur.sugestoes.controller.dto.response.comentarioResponse;

import java.util.List;

public interface comentarioService {

    comentarioResponse pegarPorId(Long id);

    List<comentarioResponse> pegarTodos();

    comentarioResponse criar (comentarioRequest requestDto);

    void deletar (Long id);

    void atualizar (comentarioRequest requestDto, Long id);

    List<comentarioResponse> pegarTodosPorSugestao(Long sugestaoId);
}
