package br.com.targettrust.springboot.aula.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class DiaExercio {
    @Id
    private Long id;

    @Column(name = "dia_semana")
    private DiaSemana diaSemana;

    @OneToMany(mappedBy = "diaExercicio")
    private List<Exercicio> exercicio;
}
