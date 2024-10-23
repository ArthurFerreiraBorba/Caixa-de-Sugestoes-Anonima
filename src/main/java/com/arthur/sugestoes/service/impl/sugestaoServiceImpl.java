package com.arthur.sugestoes.service.impl;

import com.arthur.sugestoes.controller.dto.request.sugestaoRequest;
import com.arthur.sugestoes.controller.dto.response.comentarioResponse;
import com.arthur.sugestoes.controller.dto.response.sugestaoResponse;
import com.arthur.sugestoes.entity.comentarioEntity;
import com.arthur.sugestoes.entity.sugestaoEntity;
import com.arthur.sugestoes.infra.exception.error.NotFoundException;
import com.arthur.sugestoes.repository.sugestaoRepository;
import com.arthur.sugestoes.service.comentarioService;
import com.arthur.sugestoes.service.sugestaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class sugestaoServiceImpl implements sugestaoService {
    
    private final sugestaoRepository repository;
    private final comentarioService comentarioService;
    private final String nomeEntity;

    protected sugestaoServiceImpl(sugestaoRepository repository, com.arthur.sugestoes.service.comentarioService comentarioService) {
        this.repository = repository;
        this.comentarioService = comentarioService;
        this.nomeEntity = "sugestao";
    }

    protected sugestaoResponse paraDto(sugestaoEntity entity, List<comentarioResponse> listaComentarios) {
        return new sugestaoResponse(entity.getId(), entity.getTitulo(), entity.getDescricao(), entity.getDataEnvio(), entity.getDataAtualizacao(), listaComentarios);
    };

    protected sugestaoEntity parasugestaoEntity(sugestaoRequest requestDto) {
        sugestaoEntity entity = new sugestaoEntity();
        BeanUtils.copyProperties(requestDto, entity);
        return entity;
    };

    public sugestaoResponse pegarPorId(Long id) {
        return paraDto(pegarSugestaoEntityPorId(id), comentarioService.pegarTodosPorSugestao(id));
    }

    public sugestaoEntity pegarSugestaoEntityPorId(Long id) {
        EntityExiste(id);

        log.info("entidade {} encontrada com sucesso", nomeEntity);

        return repository.findById(id).get();
    }

    public List<sugestaoResponse> pegarTodos() {
        List<sugestaoEntity> entidades= repository.findAllByOrderByDataAtualizacaoDesc();

        log.info("todas as entidades {} encontradas com sucesso", nomeEntity);

        return entidades.stream().map((entity) -> paraDto(entity, new ArrayList<>())).toList();
    }

    public sugestaoResponse criar (sugestaoRequest requestDto) {
        sugestaoEntity entity = parasugestaoEntity(requestDto);

        entity.setId(null);
        entity.setDataAtualizacao(LocalDateTime.now());
        entity.setDataEnvio(LocalDateTime.now());

        repository.save(entity);

        log.info("entidade {} criada com sucesso", nomeEntity);

        return paraDto(entity, new ArrayList<>());
    }

    public void deletar (Long id){
        EntityExiste(id);
        repository.deleteById(id);

        log.info("entidade {} deletada com sucesso", nomeEntity);
    }

    public void atualizar (sugestaoRequest requestDto, Long id) {
        EntityExiste(id);

        sugestaoEntity entity = pegarSugestaoEntityPorId(id);
        BeanUtils.copyProperties(entity, parasugestaoEntity(requestDto));

        entity.setId(id);
        entity.setDataAtualizacao(LocalDateTime.now());

        repository.save(entity);

        log.info("entidade {} atualizada com sucesso", nomeEntity);
    }

    @Override
    public sugestaoResponse pegarPorTitulo(String titulo) {

        sugestaoEntity entity = repository.findByTitulo(titulo);

        if (entity == null) {
            throw new NotFoundException(nomeEntity + " com titulo " + titulo +" não existe");
        }

        log.info("entidade {} encontrada com sucesso", nomeEntity);

        return paraDto(entity, comentarioService.pegarTodosPorSugestao(entity.getId()));
    }

    public void EntityExiste(Long id) {

        log.info("verificando se {} com id {} existe", nomeEntity, id);

        if (!repository.existsById(id)) {
            throw new NotFoundException(nomeEntity + " com id " + id +" não existe");
        }

        log.info("{} com id {} existe", nomeEntity, id);
    }
}
