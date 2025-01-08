package com.plataforma.ead.formacao.specifications;

import com.plataforma.ead.formacao.models.CursoModel;
import com.plataforma.ead.formacao.models.LicoesModel;
import com.plataforma.ead.formacao.models.ModuloModel;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.UUID;

public class SpecificationsTemplate {
    // Specification de curso
    @And({
            @Spec(path = "cursoStatus", spec = Equal.class),
            @Spec(path = "cursoNivel", spec = Equal.class),
            @Spec(path = "nome", spec = LikeIgnoreCase.class),
            @Spec(path = "instrutor", spec = Equal.class),
    })
    public interface CursoSpec extends Specification<CursoModel> {}

    // Specification de modulo
    @Spec(path = "titulo", spec = LikeIgnoreCase.class)
    public interface ModuloSpec extends Specification<ModuloModel> {}
    // Specification dinâmica que busca módulos por cursoId
    public static Specification<ModuloModel> moduloCursoId(final UUID cursoId) {
        return (root, query, cb) -> {
            query.distinct(true); // Evitar valores duplicados
            Root<ModuloModel> moduloModelRoot = root; // Entidade ModuloModel
            Root<CursoModel> cursoModelRoot = query.from(CursoModel.class); // Entidade CursoModel
            Expression<Collection<ModuloModel>> cursoModulos = cursoModelRoot.get("modulos"); // Coleção de módulos do CursoModel

            return cb.and(
                    cb.equal(cursoModelRoot.get("cursoId"), cursoId), // Comparação pelo cursoId
                    cb.isMember(moduloModelRoot, cursoModulos) // Verifica se o módulo pertence à coleção de módulos do curso
            );
        };
    }

    // Specification licões
    @Spec(path = "titulo", spec = LikeIgnoreCase.class)
    public interface LicaoSpec extends Specification<LicoesModel>{}
    // Specification dinâmica que busca módulos por cursoId
    public static Specification<LicoesModel> licoesModuloId(final UUID moduloId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Root<LicoesModel> licoes = root;
            Root<ModuloModel> moduloModelRoot = query.from(ModuloModel.class);
            Expression<Collection<LicoesModel>> moduloLicao = moduloModelRoot.get("licoes");

            return cb.and(
                    cb.equal(moduloModelRoot.get("moduloId"), moduloId),
                    cb.isMember(licoes, moduloLicao)
            );
        };
    }
}