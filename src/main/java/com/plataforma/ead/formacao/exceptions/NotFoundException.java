package com.plataforma.ead.formacao.exceptions;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String mensagem){
        super(mensagem);
    }

}
