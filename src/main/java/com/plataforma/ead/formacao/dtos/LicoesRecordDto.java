package com.plataforma.ead.formacao.dtos;

import jakarta.validation.constraints.NotBlank;

public record LicoesRecordDto(@NotBlank(message = "O titulo da lição é obrigatorio!")
                              String titulo,

                              @NotBlank(message = "A descrição da lição é obrigatorio!")
                              String descricao,

                              @NotBlank(message = "A video aula é obrigatoria!")
                              String videoUrl) {
}
