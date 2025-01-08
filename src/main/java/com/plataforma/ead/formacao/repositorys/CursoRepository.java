package com.plataforma.ead.formacao.repositorys;

import com.plataforma.ead.formacao.models.CursoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CursoRepository extends JpaRepository<CursoModel, UUID>, JpaSpecificationExecutor<CursoModel> {
    boolean existsByNome(String nome);
}
