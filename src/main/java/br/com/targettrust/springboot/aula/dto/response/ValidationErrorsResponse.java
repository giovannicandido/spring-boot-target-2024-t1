package br.com.targettrust.springboot.aula.dto.response;

public record ValidationErrorsResponse(String field, String message) {
}
