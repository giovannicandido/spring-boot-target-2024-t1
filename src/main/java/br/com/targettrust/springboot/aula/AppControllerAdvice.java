package br.com.targettrust.springboot.aula;

import br.com.targettrust.springboot.aula.dto.response.ErrorResponse;
import br.com.targettrust.springboot.aula.dto.response.ValidationErrorResponse;
import br.com.targettrust.springboot.aula.dto.response.ValidationErrorsResponse;
import br.com.targettrust.springboot.aula.model.exceptions.ClientePossuiEnderecosException;
import br.com.targettrust.springboot.aula.model.exceptions.DomainException;
import br.com.targettrust.springboot.aula.model.exceptions.ExercicioIdsNotFoundException;
import br.com.targettrust.springboot.aula.model.exceptions.RegistryNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class AppControllerAdvice extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(RegistryNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ErrorResponse handleExeception(RegistryNotFoundException exception) {
//        log.info("Registro não encontrado ");
//        return new ErrorResponse("Registro não encontrado " + exception.getId());
//    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(RuntimeException exception) {
        log.error(exception.getMessage(), exception);
        return new ErrorResponse("Ocorreu um erro");
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleException(DomainException exception) {
       if(exception.getExceptionType().getExceptionType().equalsIgnoreCase("Client")) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                   new ErrorResponse(exception.getMessage())
           );
       } else {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                   new ErrorResponse(exception.getMessage())
           );
       }
    }

    @ExceptionHandler(ClientePossuiEnderecosException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(ClientePossuiEnderecosException exception) {
        return new ErrorResponse("O cliente %s possui enderecos e não pode ser removido".formatted(exception.getClientId()));
    }

    @ExceptionHandler(ExercicioIdsNotFoundException.class)
    public ErrorResponse handleException(ExercicioIdsNotFoundException exception) {
        var ids = exception.getIds().stream().map(Object::toString).collect(Collectors.joining(","));
        return new ErrorResponse(
                "Não é possivel associar exercicios de ids %s, não foram encontrados na base de dados".formatted(ids)
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var problemDetails =  ProblemDetail.forStatus(HttpStatus.BAD_REQUEST.value());
        problemDetails.setType(URI.create("/problem-details/validation-error"));
        Map<String, Object> properties = extractValidationErrors(ex);
        problemDetails.setProperties(properties);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                problemDetails
        );
    }

    private static Map<String, Object> extractValidationErrors(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        Set<ValidationErrorsResponse> errors = new HashSet<>();
        var objectTargetName = ex.getBindingResult().getObjectName();
        for (FieldError fieldError : fieldErrors) {
            errors.add(new ValidationErrorsResponse(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        Map<String, Object> properties = Map.of(
                "validations",
                new ValidationErrorResponse(objectTargetName, errors)
        );
        return properties;
    }


}
