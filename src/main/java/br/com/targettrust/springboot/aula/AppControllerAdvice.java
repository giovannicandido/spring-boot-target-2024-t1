package br.com.targettrust.springboot.aula;

import br.com.targettrust.springboot.aula.dto.ErrorResponse;
import br.com.targettrust.springboot.aula.model.exceptions.RegistryNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AppControllerAdvice {

    @ExceptionHandler(RegistryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleExeception(RegistryNotFoundException exception) {
        log.info("Registro não encontrado ");
        return new ErrorResponse("Registro não encontrado " + exception.getId());
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(RuntimeException exception) {
        log.error(exception.getMessage(), exception);
        return new ErrorResponse("Ocorreu um erro");
    }
}
