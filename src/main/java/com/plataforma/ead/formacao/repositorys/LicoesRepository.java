package com.plataforma.ead.formacao.repositorys;

import com.plataforma.ead.formacao.models.LicoesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LicoesRepository extends JpaRepository<LicoesModel, UUID>, JpaSpecificationExecutor<LicoesModel> {

    @Query(value = """
                   SELECT * FROM tb_licoes
                   WHERE modulo_modulo_id= :moduloId
                   """,nativeQuery = true)
    List<LicoesModel> findAllLicoesDoModulo(@Param("moduloId") UUID moduloId);

    @Query(value = """
                   SELECT * FROM tb_licoes
                   WHERE modulo_modulo_id= :moduloId
                   AND
                   licoes_id= :licaoId
            """, nativeQuery = true)
    Optional<LicoesModel> findAllLicaoDoModulo(@Param("moduloId") UUID moduloId, @Param("licaoId") UUID licaoId);
}
