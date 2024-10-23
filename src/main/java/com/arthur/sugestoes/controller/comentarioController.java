package com.arthur.sugestoes.controller;

import com.arthur.sugestoes.controller.dto.request.comentarioRequest;
import com.arthur.sugestoes.controller.dto.response.comentarioResponse;
import com.arthur.sugestoes.entity.comentarioEntity;
import com.arthur.sugestoes.infra.exception.error.NotFoundException;
import com.arthur.sugestoes.service.comentarioService;
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
@RequestMapping("comentarios")
public class comentarioController {

    private final comentarioService comentarioService;

    private final String nomeEntity;

    protected comentarioController(comentarioService comentarioService) {
        this.comentarioService = comentarioService;
        this.nomeEntity = "comentario";
    }

    @Operation(summary = "buscar comentário por id do comentário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "comentário encontrado", content = {
                    @Content(mediaType = "application/json",
                    schema = @Schema(implementation = comentarioResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "comentário não encontrado", content = @Content)
    })
    @GetMapping("{id}")
    public ResponseEntity<comentarioResponse> buscarId(@PathVariable Long id) {

        log.info("buscando {} com id {}", nomeEntity, id);

        return ResponseEntity.status(200).body(comentarioService.pegarPorId(id));
    }


    @Operation(summary = "buscar todos os comentários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "comentários encontrados", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = comentarioResponse.class))
            }),
    })
    @GetMapping()
    public ResponseEntity<List<comentarioResponse>> buscarTodas() {

        log.info("buscando todas as entidades {}", nomeEntity);

        return ResponseEntity.status(200).body(comentarioService.pegarTodos());
    }

    @Operation(summary = "criar novo comentário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "comentário criado", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = comentarioResponse.class))
            }),
    })
    @PostMapping()
    public ResponseEntity<comentarioResponse> criar(@RequestBody comentarioRequest requestDto) {

        log.info("criando {}", nomeEntity);

        return ResponseEntity.status(201).body(comentarioService.criar(requestDto));
    }

    @Operation(summary = "deletar comentário por id do comentário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "cometário atualizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "comentário não encontrado", content = @Content)
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleter(@PathVariable Long id) {

        log.info("deletando {} com id {}", nomeEntity, id);

        comentarioService.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "atualizar comentário por id do comentário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "cometário atualizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "comentário não encontrado", content = @Content)
    })
    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@RequestBody comentarioRequest requestDto, @PathVariable Long id) {

        log.info("atualizando {} com id {}", nomeEntity, id);

        comentarioService.atualizar(requestDto, id);
        return ResponseEntity.status(204).build();
    }
}