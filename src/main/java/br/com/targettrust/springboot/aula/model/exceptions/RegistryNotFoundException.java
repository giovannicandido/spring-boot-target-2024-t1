package br.com.targettrust.springboot.aula.model.exceptions;

import lombok.Value;

@Value
public class RegistryNotFoundException extends RuntimeException {
    private final int id;

}
