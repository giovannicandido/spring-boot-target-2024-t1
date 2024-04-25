package br.com.targettrust.springboot.aula.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class ClienteExercicioId implements Serializable {
    private Long clienteId;
    private Long exercicioId;
}
