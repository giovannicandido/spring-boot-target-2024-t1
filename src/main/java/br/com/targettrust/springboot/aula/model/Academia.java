package br.com.targettrust.springboot.aula.model;

public record Academia(
        Integer id,
        String nome,
        Endereco endereco
) {
}
