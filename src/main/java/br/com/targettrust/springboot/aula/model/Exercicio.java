package br.com.targettrust.springboot.aula.model;

import ch.qos.logback.core.net.server.Client;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@SequenceGenerator(name = "exercicio_seq", sequenceName = "exercicio_seq", allocationSize = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private DiaExercio diaExercio;


}
