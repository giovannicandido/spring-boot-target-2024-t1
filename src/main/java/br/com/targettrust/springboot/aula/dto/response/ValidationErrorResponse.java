package br.com.targettrust.springboot.aula.dto.response;

import java.util.List;
import java.util.Set;

public record ValidationErrorResponse(String field, Set<ValidationErrorsResponse> errors) {
}
