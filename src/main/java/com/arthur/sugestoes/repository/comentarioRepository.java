package com.arthur.sugestoes.repository;

import com.arthur.sugestoes.entity.comentarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface comentarioRepository extends JpaRepository<comentarioEntity,Long> {

    List<comentarioEntity> findAllBySugestao_idOrderByDataEnvioDesc(Long sugestaoId);
}
