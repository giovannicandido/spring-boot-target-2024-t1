package br.com.targettrust.springboot.aula.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ClienteExercicio {
    @EmbeddedId
    private ClienteExercicioId id;

    private LocalDateTime updateDate;
    private LocalDateTime createdDate;

    @ManyToOne
    @MapsId
    private Cliente cliente;

    @ManyToOne
    @MapsId
    private Exercicio exercicio;
}
