package com.plataforma.ead.formacao.services.implemente;

import com.plataforma.ead.formacao.dtos.ModuloRecordDto;
import com.plataforma.ead.formacao.exceptions.NotFoundException;
import com.plataforma.ead.formacao.models.CursoModel;
import com.plataforma.ead.formacao.models.LicoesModel;
import com.plataforma.ead.formacao.models.ModuloModel;
import com.plataforma.ead.formacao.repositorys.LicoesRepository;
import com.plataforma.ead.formacao.repositorys.ModuloRepository;
import com.plataforma.ead.formacao.services.ModuloService;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModuloServicImpl implements ModuloService {

    final ModuloRepository moduloRepository;
    final LicoesRepository licoesRepository;

    public ModuloServicImpl(ModuloRepository moduloRepository, LicoesRepository licoesRepository) {
        this.moduloRepository = moduloRepository;
        this.licoesRepository = licoesRepository;
    }

    @Transactional
    @Override
    public void delete(ModuloModel moduloModel) {
        // Método para deletar em CASCATA os modulos com suas respctivas atividades
        List<LicoesModel> licoesModelList = licoesRepository.findAllLicoesDoModulo(moduloModel.getModuloId());
        if (!licoesModelList.isEmpty()){
            licoesRepository.deleteAll(licoesModelList);
        }
        moduloRepository.delete(moduloModel);
    }

    @Override
    public ModuloModel save(ModuloRecordDto moduloRecordDto, CursoModel cursoModel) {
        // instância de um objeto de classeModuloModel.
        var moduloModel = new ModuloModel();
        // Transforma de DTO para ENTITY
        BeanUtils.copyProperties(moduloRecordDto,moduloModel);
        // Populando o campo data de criação
        moduloModel.setDataCriacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        // Setando o curso que o modolo vai estár associado.
        moduloModel.setCurso(cursoModel);
        // Salvar modulo
        return moduloRepository.save(moduloModel);
    }

    @Override
    public List<ModuloModel> findAll() {
        return moduloRepository.findAll();
    }

    @Override
    public List<ModuloModel> findAllModulosDoCurso(UUID cursoId) {
        return moduloRepository.findAllModulosDoCurso(cursoId);
    }

    @Override
    public Optional<ModuloModel> findModuloDoCurso(UUID cursoId, UUID moduloId) {
        Optional<ModuloModel> moduloModelOptional = moduloRepository.findAllModuloDoCurso(cursoId, moduloId);
        if (moduloModelOptional.isEmpty()){

        }
        return moduloModelOptional;
    }

    @Override
    public Page<ModuloModel> findAllModulosDoCurso(Specification<ModuloModel> spec, Pageable pageable) {
        return moduloRepository.findAll(spec, pageable);
    }

    @Override
    public Object updateModulo(ModuloRecordDto moduloRecordDto, ModuloModel moduloModel) {

        BeanUtils.copyProperties(moduloRecordDto, moduloModel);
        moduloModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        moduloModel.setCurso(moduloModel.getCurso());
        return moduloRepository.save(moduloModel);
    }

    @Override
    public Optional<ModuloModel> findById(UUID moduloId) {
        Optional<ModuloModel> moduloModelOptional = moduloRepository.findById(moduloId);

        if (moduloModelOptional.isEmpty()){
          throw new NotFoundException("ERRO, esse modulo não pertence a esse curso");
        }
        return moduloRepository.findById(moduloId);
    }
}