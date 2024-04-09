package br.com.targettrust.springboot.aula.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.List;

@Value
public class AssociarExercicioRequest {

    @NotNull
    @Size(min = 1)
    private List<Integer> exercicios;
}
