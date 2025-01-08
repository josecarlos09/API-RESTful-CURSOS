package com.plataforma.ead.formacao.repositorys;

import com.plataforma.ead.formacao.models.ModuloModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuloRepository extends JpaRepository<ModuloModel, UUID>, JpaSpecificationExecutor<ModuloModel>{
    @Query(value = """
                   SELECT * FROM tb_modulos
                   WHERE curso_curso_id = :cursoId
                   """, nativeQuery = true)
    List<ModuloModel> findAllModulosDoCurso(@Param("cursoId") UUID cursoId);

    @Query(value = """
                   SELECT * FROM tb_modulos
                   WHERE curso_curso_id= :cursoId
                   AND
                   modulo_id= :moduloId
                   """, nativeQuery = true)
    Optional<ModuloModel> findAllModuloDoCurso(@PathVariable("cursoId") UUID cursoId, @PathVariable("moduloId") UUID moduloId);
}