package com.plataforma.ead.formacao.dtos;

import com.plataforma.ead.formacao.enums.CursoNivel;
import com.plataforma.ead.formacao.enums.CursoStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CursoRecordDto(@NotBlank(message = "O nome é obrigatorio!")
                             String nome,

                             @NotBlank(message = "A descrição é obrigatorio!")
                             String descricao,

                             @NotNull(message = "O status do curso é obrigatorio!")
                             CursoStatus cursoStatus,

                             @NotNull(message = "O nivel do curso é obrigatorio!")
                             CursoNivel cursoNivel,

                             @NotNull(message = "Informe a duração do curso!")
                             String duracaoCurso,

                             @NotNull(message = "Informe quem é o instrutor!")
                             UUID instrutor,

                             String img
                             ){
}
