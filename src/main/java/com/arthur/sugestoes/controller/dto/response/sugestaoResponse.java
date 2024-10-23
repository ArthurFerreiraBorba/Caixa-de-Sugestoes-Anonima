package com.arthur.sugestoes.controller.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record sugestaoResponse(Long id, String titulo, String descricao, LocalDateTime dataEnvio, LocalDateTime dataAtualizacao, List<comentarioResponse> listaComentarios) {
}
