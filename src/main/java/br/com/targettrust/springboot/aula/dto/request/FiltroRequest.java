package br.com.targettrust.springboot.aula.dto.request;

import lombok.Value;

@Value
public class FiltroRequest {
    private String name;
    private String cpf;
}
