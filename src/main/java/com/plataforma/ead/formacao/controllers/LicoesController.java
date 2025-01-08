package com.plataforma.ead.formacao.controllers;

import com.plataforma.ead.formacao.dtos.LicoesRecordDto;
import com.plataforma.ead.formacao.models.LicoesModel;
import com.plataforma.ead.formacao.services.LicoesService;
import com.plataforma.ead.formacao.services.ModuloService;
import com.plataforma.ead.formacao.specifications.SpecificationsTemplate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class LicoesController {

    final LicoesService licoesService;
    final ModuloService moduloService;

    public LicoesController(LicoesService licoesService, ModuloService moduloService) {
        this.licoesService = licoesService;
        this.moduloService = moduloService;
    }

    @PostMapping("/modulo/{moduloId}/licoes")
    public ResponseEntity<Object> saveLicoes(@RequestBody @Valid LicoesRecordDto licoesRecordDto,
                                             @PathVariable(value = "moduloId")UUID moduloId){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(licoesService.saveLicoes(licoesRecordDto, moduloService.findById(moduloId).get()));
    }

    @GetMapping("/modulo/{moduloId}/licoes")
    public ResponseEntity<Page<LicoesModel>> getAllLicoes(@PathVariable(value = "moduloId")UUID moduloId,
                                                          SpecificationsTemplate.LicaoSpec licaoSpec,
                                                          Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(licoesService.findAllLicoesDoModulo(SpecificationsTemplate.licoesModuloId(moduloId).and(licaoSpec), pageable));
    }

    @GetMapping("/modulo/{moduloId}/licoes/{licaoId}")
    public ResponseEntity<Object> geOnLicoes(@PathVariable(value = "moduloId")UUID moduloId,
                                             @PathVariable(value = "licaoId")UUID licaoId){
        return ResponseEntity.status(HttpStatus.OK).body((licoesService.findLicaoDoModulo(moduloId, licaoId)));
    }

    @DeleteMapping("/modulo/{moduloId}/licao/{licaoId}")
    public ResponseEntity<Object> deleteLicao(@PathVariable(value = "moduloId")UUID moduloId,
                                              @PathVariable(value = "licaoId")UUID licaoId){
        licoesService.delete(licoesService.findLicaoDoModulo(moduloId, licaoId).get());
        return ResponseEntity.status(HttpStatus.OK).body("LIÇÃO DELETADA COM SUCESSO!");
    }

    @PutMapping("/modulo/{moduloId}/licao/{licaoId}")
    public ResponseEntity<Object> uodatLicoes(@PathVariable(value = "moduloId")UUID moduloId,
                                              @PathVariable(value = "licaoId")UUID licaoId,
                                              @RequestBody @Valid LicoesRecordDto licoesRecordDto){
        return ResponseEntity.status(HttpStatus.OK).body(licoesService.updateLicoes(licoesRecordDto, licoesService.findLicaoDoModulo(moduloId, licaoId).get()));
    }

}