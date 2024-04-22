package br.com.targettrust.springboot.aula.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class DiaExercio {
    @Id
    private Long id;

    @Column(name = "dia_semana")
    private DiaSemana diaSemana;

    @OneToMany(mappedBy = "diaExercio")
    private List<Exercicio> exercicio;
}
