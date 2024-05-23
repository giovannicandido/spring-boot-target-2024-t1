package br.com.targettrust.springboot.aula.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@SequenceGenerator(name = "exercicio_seq", sequenceName = "exercicio_seq", allocationSize = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Exercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exercicio_seq")
    private Long id;
    @Column(length = 100, unique = true, nullable = false)
    private String nome;
    @Column(nullable = false, length = 150)
    private String parteCorpo;

    @ManyToOne
    @JoinColumn(nullable = true, name = "dia_exercicio_id", foreignKey = @ForeignKey(name = "fk_exercicio_dia"))
    private DiaExercio diaExercicio;

}
