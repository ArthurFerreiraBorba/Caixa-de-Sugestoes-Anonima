package com.arthur.sugestoes.infra.exception.error;

public class NotFoundException extends RuntimeException{

    public NotFoundException (String mensagem) {
        super(mensagem);
    }
}
