package com.plataforma.ead.formacao.services;

import com.plataforma.ead.formacao.dtos.ModuloRecordDto;
import com.plataforma.ead.formacao.models.CursoModel;
import com.plataforma.ead.formacao.models.ModuloModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuloService {

    void delete(ModuloModel moduloModel);

    ModuloModel save(ModuloRecordDto moduloRecordDto, CursoModel cursoModel);

    List<ModuloModel> findAll();

    List<ModuloModel> findAllModulosDoCurso(UUID cursoId);

    Object updateModulo(ModuloRecordDto moduloRecordDto, ModuloModel moduloModel);

    Optional<ModuloModel> findById(UUID moduloId);

    Optional<ModuloModel> findModuloDoCurso(UUID cursoId, UUID moduloId);

    Page<ModuloModel> findAllModulosDoCurso(Specification<ModuloModel> spec, Pageable pageable);
}


