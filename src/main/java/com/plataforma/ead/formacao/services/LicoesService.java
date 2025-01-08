package com.plataforma.ead.formacao.services;

import com.plataforma.ead.formacao.dtos.LicoesRecordDto;
import com.plataforma.ead.formacao.models.LicoesModel;
import com.plataforma.ead.formacao.models.ModuloModel;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LicoesService {

    LicoesModel saveLicoes(@Valid LicoesRecordDto licoesRecordDto, ModuloModel moduloModel);

    Optional<LicoesModel> findLicaoDoModulo(UUID moduloId, UUID licaoId);

    void delete(LicoesModel licoesModel);

    LicoesModel updateLicoes(@Valid LicoesRecordDto licoesRecordDto, LicoesModel licoesModel);

    Page<LicoesModel> findAllLicoesDoModulo(Specification<LicoesModel> licaoSpec, Pageable pageable);

}





