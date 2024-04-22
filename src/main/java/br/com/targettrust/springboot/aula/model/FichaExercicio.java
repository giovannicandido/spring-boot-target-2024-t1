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
public class FichaExercicio {
    @Id
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(fetch = FetchType.LAZY)
    private List<DiaExercio> diasExercicios;
}
