package com.plataforma.ead.formacao.services.implemente;

import com.plataforma.ead.formacao.dtos.LicoesRecordDto;
import com.plataforma.ead.formacao.exceptions.NotFoundException;
import com.plataforma.ead.formacao.models.LicoesModel;
import com.plataforma.ead.formacao.models.ModuloModel;
import com.plataforma.ead.formacao.repositorys.LicoesRepository;
import com.plataforma.ead.formacao.repositorys.ModuloRepository;
import com.plataforma.ead.formacao.services.LicoesService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Service
public class LicoesServiceImpl implements LicoesService {

   final LicoesRepository licoesRepository;
   final ModuloRepository moduloRepository;

    public LicoesServiceImpl(LicoesRepository licoesRepository,
                             ModuloRepository moduloRepository) {
        this.licoesRepository = licoesRepository;
        this.moduloRepository = moduloRepository;
    }

    @Override
    public LicoesModel saveLicoes(LicoesRecordDto licoesRecordDto, ModuloModel moduloModel) {
        var licoesModel = new LicoesModel(); // Instanciando LicoesModel
        BeanUtils.copyProperties(licoesRecordDto, licoesModel);// convertendo de DTO para Model
        licoesModel.setDataInicio(LocalDateTime.now(ZoneId.of("America/Sao_Paulo"))); // Setando data
        licoesModel.setModulo(moduloModel); // Associando lições a um modulo
        return licoesRepository.save(licoesModel); // Salvando lições cadastradas
    }

    @Override
    public Optional<LicoesModel> findLicaoDoModulo(UUID moduloId, UUID licaoId) {
        Optional<LicoesModel> licoesModelOptional = licoesRepository.findAllLicaoDoModulo(moduloId, licaoId);
        if (licoesModelOptional.isEmpty()){
            throw new NotFoundException("ERRO, essa lição não estão presentes nesse modulo!");
        }
        return licoesModelOptional;
    }

    @Override
    public void delete(LicoesModel licoesModel) {
        licoesRepository.delete(licoesModel);
    }

    @Override
    public LicoesModel updateLicoes(LicoesRecordDto licoesRecordDto, LicoesModel licoesModel) {
        // De DTO para ENTITY
        BeanUtils.copyProperties(licoesRecordDto, licoesModel);
        // Set as datas
        licoesModel.setDataAtualização(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        // Associação
        licoesModel.setModulo(licoesModel.getModulo());
        // Salvar e retornar
        return licoesRepository.save(licoesModel);
    }

    @Override
    public Page<LicoesModel> findAllLicoesDoModulo(Specification<LicoesModel> licaoSpec, Pageable pageable) {
        return licoesRepository.findAll(licaoSpec, pageable);
    }
}