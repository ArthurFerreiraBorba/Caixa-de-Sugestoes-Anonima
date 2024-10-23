package com.arthur.sugestoes.repository;

import com.arthur.sugestoes.entity.sugestaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface sugestaoRepository extends JpaRepository<sugestaoEntity,Long> {

    List<sugestaoEntity> findAllByOrderByDataAtualizacaoDesc();

    sugestaoEntity findByTitulo(String titulo);
}
