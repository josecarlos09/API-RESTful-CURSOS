package com.plataforma.ead.formacao.dtos;

import jakarta.validation.constraints.NotBlank;

public record ModuloRecordDto(@NotBlank(message = "O titulo do modulo é obrigatorio!")
                              String titulo,

                              @NotBlank(message = "A descrição do modulo é obrigatoria!")
                              String descricao) {
}
