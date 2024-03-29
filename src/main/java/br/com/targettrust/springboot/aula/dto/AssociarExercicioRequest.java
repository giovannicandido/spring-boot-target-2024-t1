package br.com.targettrust.springboot.aula.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class AssociarExercicioRequest {

    @NotNull
    @Size(min = 1)
    private List<Integer> exercicios;

    public List<Integer> getExercicios() {
        return exercicios;
    }

    public void setExercicios(List<Integer> exercicios) {
        this.exercicios = exercicios;
    }
}
