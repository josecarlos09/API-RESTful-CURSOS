package com.plataforma.ead.formacao.services;

import com.plataforma.ead.formacao.dtos.CursoRecordDto;
import com.plataforma.ead.formacao.models.CursoModel;
import com.plataforma.ead.formacao.specifications.SpecificationsTemplate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CursoService {

   void deleteCurso(CursoModel cursoModel);

   CursoModel saveCurso(CursoRecordDto cursoRecordDto);

   boolean existByNome(String nome);

   Optional<CursoModel> findById(UUID idCurso);

   CursoModel updateCurso(CursoRecordDto cursoRecordDto, CursoModel cursoModel);

   List<CursoModel> fidAll();

   Page<CursoModel> fidAll(SpecificationsTemplate.CursoSpec spec, Pageable pageable);
}


