package br.com.targettrust.springboot.aula.dto.response;

import br.com.targettrust.springboot.aula.model.Exercicio;
import lombok.Builder;

@Builder
public record ExercicioResponse(
        Long id,
        String nome,
        String parteCorpo
) {

    public static ExercicioResponse fromModel(Exercicio exercicio) {
//        return new ExercicioResponse(exercicio.getId(), exercicio.getNome(), exercicio.getParteCorpo());
        return ExercicioResponse.builder()
                .id(exercicio.getId())
                .nome(exercicio.getNome())
                .parteCorpo(exercicio.getParteCorpo())
                .build();
    }
}
