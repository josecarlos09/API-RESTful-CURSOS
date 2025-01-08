package com.plataforma.ead.formacao.services.implemente;

import com.plataforma.ead.formacao.dtos.CursoRecordDto;
import com.plataforma.ead.formacao.exceptions.NotFoundException;
import com.plataforma.ead.formacao.models.CursoModel;
import com.plataforma.ead.formacao.models.LicoesModel;
import com.plataforma.ead.formacao.models.ModuloModel;
import com.plataforma.ead.formacao.repositorys.CursoRepository;
import com.plataforma.ead.formacao.repositorys.LicoesRepository;
import com.plataforma.ead.formacao.repositorys.ModuloRepository;
import com.plataforma.ead.formacao.services.CursoService;
import com.plataforma.ead.formacao.specifications.SpecificationsTemplate;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CursoServiceImpl implements CursoService {

    final CursoRepository cursoRepository;
    final LicoesRepository licoesRepository;
    final ModuloRepository moduloRepository;

    public CursoServiceImpl(CursoRepository cursoRepository,
                            LicoesRepository licoesRepository,
                            ModuloRepository moduloRepository) {
        this.cursoRepository = cursoRepository;
        this.licoesRepository = licoesRepository;
        this.moduloRepository = moduloRepository;
    }

    /*@Transactional
    @Override
    public void delete(CursoModel cursoModel) {
        // Lista de modulos
        List<ModuloModel> moduloModelsLista = moduloRepository.findAllModulosDoCurso(cursoModel.getCursoId());
        // Verifica se no curso especificado existem modulos
        if (!moduloModelsLista.isEmpty()){
            // Se existir modulos no curso vara a lista
            for (ModuloModel moduloModel: moduloModelsLista){
                // Lista de lições
                List<LicoesModel> licoesModelList = licoesRepository.findAllLicoesDoModulo(moduloModel.getModuloId());
                // Verifica se a lista de lições esta vazia
                if (!licoesModelList.isEmpty()){
                    licoesRepository.deleteAll(licoesModelList); // Apaga as lições
                }
            }
            moduloRepository.deleteAll(moduloModelsLista); // Apaga os Modulos
        }
        cursoRepository.delete(cursoModel); // Apaga o curso
    }

     */
    @Transactional
    @Override
    public void deleteCurso(CursoModel cursoModel) {
        List<ModuloModel> moduloModelList = moduloRepository.findAllModulosDoCurso(cursoModel.getCursoId());
        if (!moduloModelList.isEmpty()){
            for (ModuloModel moduloModel: moduloModelList){
                List<LicoesModel> licoesModelList = licoesRepository.findAllLicoesDoModulo(moduloModel.getModuloId());
                if (!licoesModelList.isEmpty()){
                    licoesRepository.deleteAll(licoesModelList);
                }
            }
            moduloRepository.deleteAll(moduloModelList);
        }
        cursoRepository.delete(cursoModel);
    }

    @Override
    public CursoModel saveCurso(CursoRecordDto cursoRecordDto) {
        var cursoModel = new CursoModel();
        //Convevnção de campo DTO para MODEL
        BeanUtils.copyProperties(cursoRecordDto, cursoModel);
        // Setando a data de criação e de atualização
        cursoModel.setDataInicio(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        cursoModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        return cursoRepository.save(cursoModel);
    }

    @Override
    public boolean existByNome(String nome) {
        return cursoRepository.existsByNome(nome);
    }


    @Override
    public Optional<CursoModel> findById(UUID idCurso) {
        Optional<CursoModel> cursoModelOptional = cursoRepository.findById(idCurso);
        // Tratativa de erro
        if (cursoModelOptional.isEmpty()){
            throw new NotFoundException("ERRO, curso não encontrado!");
        }
        return cursoModelOptional;
    }

    @Override
    public CursoModel updateCurso(CursoRecordDto cursoRecordDto, CursoModel cursoModel) {
        // Convenção de DTO para MODEL
        BeanUtils.copyProperties(cursoRecordDto, cursoModel);
        // atualizar a data
        cursoModel.setDataAtualizacao(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        // Retorna os dados salvos e atualizados
        return cursoRepository.save(cursoModel);
    }

    @Override
    public List<CursoModel> fidAll() {
        return cursoRepository.findAll();
    }

    @Override
    public Page<CursoModel> fidAll(SpecificationsTemplate.CursoSpec spec, Pageable pageable) {
        return cursoRepository.findAll(spec, pageable);
    }

}