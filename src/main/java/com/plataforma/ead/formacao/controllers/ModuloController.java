package com.plataforma.ead.formacao.controllers;

import com.plataforma.ead.formacao.dtos.ModuloRecordDto;
import com.plataforma.ead.formacao.models.ModuloModel;
import com.plataforma.ead.formacao.services.CursoService;
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
public class ModuloController {

    final ModuloService moduloService;
    final CursoService cursoService;

    public ModuloController(ModuloService moduloService, CursoService cursoService) {
        this.moduloService = moduloService;
        this.cursoService = cursoService;
    }

    @PostMapping("/curso/{cursoId}/modulos")
    public ResponseEntity<Object> saveModulos(@RequestBody @Valid ModuloRecordDto moduloRecordDto,
                                              @PathVariable(value = "cursoId")UUID cursoId){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(moduloService.save(moduloRecordDto, cursoService.findById(cursoId).get()));
    }

    @GetMapping("/curso/{cursoId}/modulos")
    public ResponseEntity<Page<ModuloModel>> getAllModulos(@PathVariable(value = "cursoId")UUID cursoId,
                                                           SpecificationsTemplate.ModuloSpec spec,
                                                           Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(moduloService.findAllModulosDoCurso(SpecificationsTemplate.moduloCursoId(cursoId).and(spec), pageable));
    }

    @GetMapping("curso/{cursoId}/modulos/{moduloId}")
    public ResponseEntity<Object> getOnModulo(@PathVariable(value = "cursoId")UUID cursoId,
                                              @PathVariable(value = "moduloId")UUID moduloId){
        // Será passado o id do curso e do modulo especificado
        return ResponseEntity.status(HttpStatus.OK).body(moduloService.findModuloDoCurso(cursoId, moduloId));
    }

    @DeleteMapping("/curso/{cursoId}/modulo/{moduloId}")
    public ResponseEntity<Object> deleteMododulo(@PathVariable(value = "cursoId")UUID cursoId,
                                                 @PathVariable(value = "moduloId")UUID moduloId){
        moduloService.delete(moduloService.findModuloDoCurso(cursoId, moduloId).get());
        return ResponseEntity.status(HttpStatus.OK).body("MODULO DELETADO COM SUCESSO!");
    }

    @PutMapping("/curso/{cursoId}/modulo/{moduloId}")
    public ResponseEntity<Object> updateModulo(@PathVariable(value = "cursoId")UUID cursoId,
                                               @PathVariable(value = "moduloId")UUID moduloId,
                                               @RequestBody @Valid ModuloRecordDto moduloRecordDto){
        return ResponseEntity.status(HttpStatus.OK)
                .body(moduloService.updateModulo(moduloRecordDto, moduloService.findModuloDoCurso(cursoId,moduloId).get()));
    }
}
