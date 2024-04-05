package br.com.targettrust.springboot.aula.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@SequenceGenerator(name = "exercicio_seq", sequenceName = "exercicio_seq", allocationSize = 1)
public class Exercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exercicio_seq")
    private Long id;
//    private UUID id;
    // UUID - V4 (Random), V1 (Ordenavel, MacAddress), V2 (Não sei o que faz), V3 (Não me lembro), V7 (Timestampted, Ordenavel)
//    private String id;
    @Column(length = 100, unique = true, nullable = false)
    private String nome;
    @Column(nullable = false, length = 150)
    private String parteCorpo;

//    @ManyToMany(mappedBy = "exercicios")
//    private List<Pessoa> pessoas;
}
