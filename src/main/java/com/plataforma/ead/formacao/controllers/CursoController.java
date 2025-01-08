package com.plataforma.ead.formacao.controllers;

import com.plataforma.ead.formacao.dtos.CursoRecordDto;
import com.plataforma.ead.formacao.models.CursoModel;
import com.plataforma.ead.formacao.services.CursoService;
import com.plataforma.ead.formacao.specifications.SpecificationsTemplate;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/curso")
public class CursoController {

   final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @PostMapping
    public ResponseEntity<Object> saveCurso(@Valid @RequestBody CursoRecordDto cursoRecordDto){
        // Verifica se o nome do curso já existe antes de chamar o método save no corpo da requisição
        if (cursoService.existByNome(cursoRecordDto.nome())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO, ESSE CURSO JÁ ESTÁ CADASTRADO!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(cursoService.saveCurso(cursoRecordDto));
    }

    @GetMapping
    public ResponseEntity<Page<CursoModel>> getAllCursos(SpecificationsTemplate.CursoSpec spec,
                                                         Pageable pageable){
        Page<CursoModel> cursoModel = cursoService.fidAll(spec, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(cursoModel);
    }

    @GetMapping("/{cursoId}")
    public ResponseEntity<Object> getOnCurso(@PathVariable(value = "cursoId")UUID cursoId){
        //Optional<CursoModel> cursoModelOptional = cursoService.existById(idCurso);
        return ResponseEntity.status(HttpStatus.OK).body(cursoService.findById(cursoId));
    }

    @DeleteMapping("/{cursoId}")
    public ResponseEntity<Object> deleteCursos(@PathVariable(value = "cursoId")UUID cursoId){
        cursoService.deleteCurso(cursoService.findById(cursoId).get());
        return ResponseEntity.status(HttpStatus.OK).body("CURSO DELETADO COM SUCESSO!");
    }

    @PutMapping("/{idCurso}")
    public ResponseEntity<Object> updateCurso(@PathVariable(value = "idCurso")UUID idCurso,
                                              @RequestBody @Valid CursoRecordDto cursoRecordDto){
        return ResponseEntity.status(HttpStatus.OK)
                .body(cursoService.updateCurso(cursoRecordDto, cursoService.findById(idCurso).get()));
    }
}