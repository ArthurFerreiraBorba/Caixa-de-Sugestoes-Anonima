package com.arthur.sugestoes.service.impl;

import com.arthur.sugestoes.controller.dto.request.comentarioRequest;
import com.arthur.sugestoes.controller.dto.response.comentarioResponse;
import com.arthur.sugestoes.entity.comentarioEntity;
import com.arthur.sugestoes.infra.exception.error.NotFoundException;
import com.arthur.sugestoes.repository.comentarioRepository;
import com.arthur.sugestoes.repository.sugestaoRepository;
import com.arthur.sugestoes.service.comentarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class comentarioServiceImpl implements comentarioService {

    private final comentarioRepository repository;
    private final sugestaoRepository sugestaoRepository;
    private final String nomeEntity;

    public comentarioServiceImpl(comentarioRepository repository, sugestaoRepository sugestaoRespository) {
        this.repository = repository;
        this.sugestaoRepository = sugestaoRespository;
        this.nomeEntity = "comentario";
    }

    protected comentarioResponse paraDto(comentarioEntity entity) {
        return new comentarioResponse(entity.getId(), entity.getSugestao().getId(), entity.getTexto(), LocalDateTime.now());
    };

    protected comentarioEntity paraComentarioEntity(comentarioRequest requestDto) {
        comentarioEntity entity = new comentarioEntity();
        entity.setTexto(requestDto.texto());
        entity.setSugestao(sugestaoRepository.findById(requestDto.sugestaoId()).orElseThrow(RuntimeException::new));
        return entity;
    };

    public comentarioResponse pegarPorId(Long id) {
        return paraDto(pegarcomentarioEntityPorId(id));
    }

    public comentarioEntity pegarcomentarioEntityPorId(Long id) {
        EntityExiste(id);

        log.info("entidade {} encontrada com sucesso", nomeEntity);

        return repository.findById(id).get();
    }

    public List<comentarioResponse> pegarTodos() {
        List<comentarioEntity> entidades= repository.findAll();

        log.info("todas as entidades {} encontradas com sucesso", nomeEntity);

        return entidades.stream().map(this::paraDto).toList();
    }

    public List<comentarioResponse> pegarTodosPorSugestao(Long sugestaoId) {
        List<comentarioEntity> entidades= repository.findAllBySugestao_idOrderByDataEnvioDesc(sugestaoId);

        log.info("todas as entidades {} com sugestaoId igual a {} encontradas com sucesso", nomeEntity, sugestaoId);

        return entidades.stream().map(this::paraDto).toList();
    }

    public comentarioResponse criar (comentarioRequest requestDto) {
        comentarioEntity entity = paraComentarioEntity(requestDto);
        entity.setId(null);

        repository.save(entity);

        log.info("entidade {} criada com sucesso", nomeEntity);

        return paraDto(entity);
    }

    public void deletar (Long id){
        EntityExiste(id);
        repository.deleteById(id);

        log.info("entidade {} deletada com sucesso", nomeEntity);
    }

    public void atualizar (comentarioRequest requestDto, Long id) {
        EntityExiste(id);
        comentarioEntity entity = paraComentarioEntity(requestDto);

        entity.setId(id);

        repository.save(entity);

        log.info("entidade {} atualizada com sucesso", nomeEntity);
    }

    public void EntityExiste(Long id) {

        log.info("verificando se {} com id {} existe", nomeEntity, id);

        if (!repository.existsById(id)) {
            throw new NotFoundException(nomeEntity + " com id " + id +" não existe");
        }

        log.info("{} com id {} existe", nomeEntity, id);
    }
}
