package br.com.targettrust.springboot.aula.model.exceptions;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {
    private final String message;
    private final ExceptionType exceptionType;

    public DomainException(String message, ExceptionType exceptionType) {
        this.message = message;
        this.exceptionType = exceptionType;
    }
}
