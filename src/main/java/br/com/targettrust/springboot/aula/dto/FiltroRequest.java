package br.com.targettrust.springboot.aula.dto;

import lombok.Value;

@Value
public class FiltroRequest {
    private String name;
    private String cpf;
}
