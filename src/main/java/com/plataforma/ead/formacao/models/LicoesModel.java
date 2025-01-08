package com.plataforma.ead.formacao.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_LICOES")
public class LicoesModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID licaoId;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, length = 255)
    private String descricao;

    @Column(nullable = false)
    private String videoUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataInicio;

    private LocalDateTime dataAtualização;

    // Relacionamentos
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ModuloModel modulo;

    public UUID getLicaoId() {
        return licaoId;
    }

    public void setLicaoId(UUID licaoId) {
        this.licaoId = licaoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public ModuloModel getModulo() {
        return modulo;
    }

    public void setModulo(ModuloModel modulo) {
        this.modulo = modulo;
    }

    public LocalDateTime getDataAtualização() {
        return dataAtualização;
    }

    public void setDataAtualização(LocalDateTime dataAtualização) {
        this.dataAtualização = dataAtualização;
    }
}