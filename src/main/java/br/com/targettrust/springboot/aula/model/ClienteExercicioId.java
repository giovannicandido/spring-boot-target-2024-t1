package br.com.targettrust.springboot.aula.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class ClienteExercicioId {
    private Long clienteId;
    private Long exercicioId;
}
