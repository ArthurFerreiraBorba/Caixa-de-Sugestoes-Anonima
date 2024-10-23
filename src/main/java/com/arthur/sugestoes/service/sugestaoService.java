package com.arthur.sugestoes.service;

import com.arthur.sugestoes.controller.dto.request.sugestaoRequest;
import com.arthur.sugestoes.controller.dto.response.sugestaoResponse;

import java.util.List;

public interface sugestaoService {
    sugestaoResponse pegarPorId(Long id);

    List<sugestaoResponse> pegarTodos();

    sugestaoResponse criar (sugestaoRequest requestDto);

    void deletar (Long id);

    void atualizar (sugestaoRequest requestDto, Long id);

    sugestaoResponse pegarPorTitulo(String titulo);
}
