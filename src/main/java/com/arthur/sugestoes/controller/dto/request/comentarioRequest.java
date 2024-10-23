package com.arthur.sugestoes.controller.dto.request;

import java.time.LocalDateTime;

public record comentarioRequest(Long sugestaoId, String texto) {
}
