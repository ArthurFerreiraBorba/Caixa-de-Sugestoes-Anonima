package com.arthur.sugestoes.controller.dto.response;

import java.time.LocalDateTime;

public record comentarioResponse(Long id,Long sugestaoId, String texto, LocalDateTime dataEnvio) {
}
