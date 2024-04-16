package br.com.targettrust.springboot.aula.dto.request;

import br.com.targettrust.springboot.aula.model.Exercicio;
import jakarta.validation.constraints.NotBlank;

public record ExercicioRequest(
        @NotBlank
        String nome,
        @NotBlank
        String parteCorpo
) {
    public Exercicio toModel() {
        return Exercicio.builder()
                .nome(nome)
                .parteCorpo(parteCorpo)
                .build();
    }
}
