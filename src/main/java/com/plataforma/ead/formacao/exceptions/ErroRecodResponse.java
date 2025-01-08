package com.plataforma.ead.formacao.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErroRecodResponse(int codigoErro,
                                String mensagemErro,
                                Map<String, String> errosDetalhados){}
