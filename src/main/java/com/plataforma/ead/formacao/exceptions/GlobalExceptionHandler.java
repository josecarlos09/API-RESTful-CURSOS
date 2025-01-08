package com.plataforma.ead.formacao.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Tratamento de erro NOT FUND
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErroRecodResponse> trataNotFoundException(NotFoundException ex){
        // Trata exceptions de NOT FUND (Trata dados não encontrados na base de dados, EX. Id, nome inexistente, usuarios não cadastrados...)
        var erroRecordResponse = new ErroRecodResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroRecordResponse);
    }

    // Manipulação de exception de validações (Tratamento de BAD REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroRecodResponse> manipulaExceptionDeValidacao(MethodArgumentNotValidException ex){
        Map<String, String> erros = new HashMap<>();

        // Extrai os erros de validação e coloca no mapa (Uso de forEach )
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String nomeCampo = ((FieldError) error).getField();
            String mensagemErro = error.getDefaultMessage();
            erros.put(nomeCampo, mensagemErro);
           }
        );
        var erroResponse = new ErroRecodResponse(HttpStatus.BAD_REQUEST.value(), "Erro de validação nos campos enviados", erros);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponse);
    }

    // Tratamento de erros para os ENUMS
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroRecodResponse> handleInvalidFormatException(HttpMessageNotReadableException exception){
        Map<String, String> erros = new HashMap<>();

        if (exception.getCause() instanceof InvalidFormatException){
            InvalidFormatException invalidFormatException = (InvalidFormatException) exception.getCause();

            if (invalidFormatException.getTargetType()!=null && invalidFormatException.getTargetType().isEnum()){
                String fieldName = invalidFormatException.getPath().get(invalidFormatException.getPath().size()-1).getFieldName();
                String erroMensagem = String.format("O valor fornecido para '%s' não é válido. Valores permitidos: %s",
                        fieldName,
                        Arrays.toString(invalidFormatException.getTargetType().getEnumConstants()));
                erros.put(fieldName, erroMensagem);
            }
        }

        var erroResponse = new ErroRecodResponse(HttpStatus.BAD_REQUEST.value(), "ERRO, Enum ivalido!", erros);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroResponse);
    }
}