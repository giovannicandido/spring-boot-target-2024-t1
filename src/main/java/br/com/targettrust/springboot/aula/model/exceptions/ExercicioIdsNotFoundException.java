package br.com.targettrust.springboot.aula.model.exceptions;

import lombok.Value;

import java.util.List;

@Value
public class ExercicioIdsNotFoundException extends RuntimeException {
    private final List<Long> ids;
}
