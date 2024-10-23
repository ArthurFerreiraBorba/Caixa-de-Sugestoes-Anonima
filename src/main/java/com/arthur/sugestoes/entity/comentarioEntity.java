package com.arthur.sugestoes.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "comentarios")
public class comentarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sugestao_id")
    private sugestaoEntity sugestao;

    private String texto;

    private LocalDateTime dataEnvio;
}
