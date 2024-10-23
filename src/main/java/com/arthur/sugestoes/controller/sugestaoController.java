package com.arthur.sugestoes.controller;

import com.arthur.sugestoes.controller.dto.request.sugestaoRequest;
import com.arthur.sugestoes.controller.dto.response.comentarioResponse;
import com.arthur.sugestoes.service.sugestaoService;
import com.arthur.sugestoes.controller.dto.response.sugestaoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("sugestoes")
public class sugestaoController {
    
    private final sugestaoService sugestaoService;

    private final String nomeEntity;

    protected sugestaoController(sugestaoService sugestaoService) {
        this.sugestaoService = sugestaoService;
        this.nomeEntity = "sugestao";
    }

    @Operation(summary = "buscar sugestão por id da sugestão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sugestão encontrada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = comentarioResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "sugestão não encontrada", content = @Content)
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<sugestaoResponse> buscarId(@PathVariable Long id) {

        log.info("buscando {} com id {}", nomeEntity, id);

        return ResponseEntity.status(200).body(sugestaoService.pegarPorId(id));
    }

    @Operation(summary = "buscar sugestão por id do titulo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sugestão encontrada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = comentarioResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "sugestão não encontrada", content = @Content)
    })
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<sugestaoResponse> buscarTitulo(@PathVariable String titulo) {

        log.info("buscando {} com titulo {}", nomeEntity, titulo);

        return ResponseEntity.status(200).body(sugestaoService.pegarPorTitulo(titulo));
    }

    @Operation(summary = "buscar todas as sugestões")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sugestões encontradas", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = comentarioResponse.class))
            }),
    })
    @GetMapping()
    public ResponseEntity<List<sugestaoResponse>> buscarTodas() {

        log.info("buscando todas as entidades {}", nomeEntity);

        return ResponseEntity.status(200).body(sugestaoService.pegarTodos());
    }

    @Operation(summary = "criar nova sugestão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "sugestão criada", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = comentarioResponse.class))
            }),
    })
    @PostMapping()
    public ResponseEntity<sugestaoResponse> criar(@RequestBody sugestaoRequest requestDto) {

        log.info("criando {}", nomeEntity);

        return ResponseEntity.status(201).body(sugestaoService.criar(requestDto));
    }

    @Operation(summary = "deletar sugestão por id da sugestão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "sugestão delatada", content = @Content),
            @ApiResponse(responseCode = "404", description = "sugestão não encontrada", content = @Content)
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleter(@PathVariable Long id) {

        log.info("deletando {} com id {}", nomeEntity, id);

        sugestaoService.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "atualizar sugestão por id da sugestão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "sugestão atualizada", content = @Content),
            @ApiResponse(responseCode = "404", description = "sugestão não encontrada", content = @Content)
    })
    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@RequestBody sugestaoRequest requestDto, @PathVariable Long id) {

        log.info("atualizando {} com id {}", nomeEntity, id);

        sugestaoService.atualizar(requestDto, id);
        return ResponseEntity.status(204).build();
    }
}