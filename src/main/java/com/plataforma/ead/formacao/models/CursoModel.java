package com.plataforma.ead.formacao.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.plataforma.ead.formacao.enums.CursoStatus;
import com.plataforma.ead.formacao.enums.CursoNivel;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL) // Iclua apenas valores que não sejam nulos
@Entity
@Table(name = "TB_CURSOS")
public class CursoModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //
    private UUID cursoId;

    @Column(nullable = false, length = 100, unique = true)
    private String nome;

    @Column(nullable = false, length = 255)
    private String Descricao;

    @Column(nullable = false, length = 10)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy HH:mm:ss")
    private LocalDateTime dataInicio;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy HH:mm:ss")
    private String duracaoCurso;

    @Column(nullable = false, length = 10)
    private LocalDateTime dataAtualizacao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CursoStatus cursoStatus;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private CursoNivel cursoNivel;

    @Column(nullable = false)
    private UUID instrutor;

    @Column(length = 255)
    private String imagemUrl;

    // Relacionamento de Curso com Modulo
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "curso", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private Set<ModuloModel> modulos;

    public UUID getCursoId() {
        return cursoId;
    }

    public void setCursoId(UUID cursoId) {
        this.cursoId = cursoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDuracaoCurso() {
        return duracaoCurso;
    }

    public void setDuracaoCurso(String duracaoCurso) {
        this.duracaoCurso = duracaoCurso;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public CursoStatus getCursoStatus() {
        return cursoStatus;
    }

    public void setCursoStatus(CursoStatus cursoStatus) {
        this.cursoStatus = cursoStatus;
    }

    public CursoNivel getCursoNivel() {
        return cursoNivel;
    }

    public void setCursoNivel(CursoNivel cursoNivel) {
        this.cursoNivel = cursoNivel;
    }

    public UUID getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(UUID instrutor) {
        this.instrutor = instrutor;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public Set<ModuloModel> getModulos() {
        return modulos;
    }

    public void setModulos(Set<ModuloModel> modulo) {
        this.modulos = modulo;
    }
}