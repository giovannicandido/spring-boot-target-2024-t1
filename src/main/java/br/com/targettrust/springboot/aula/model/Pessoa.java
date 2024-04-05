package br.com.targettrust.springboot.aula.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

//@Getter
//@Setter
//@AllArgsConstructor
//@ToString
//@EqualsAndHashCode
//@Value // Imutável
@Data // Mutável
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "enderecos")
@ToString(exclude = "enderecos")
@Entity
@SequenceGenerator(name = "pessoa_seq", sequenceName = "pessoa_seq", allocationSize = 1)
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_seq" )
    private Long id;
    @Column(length = 50)
    private String nome;
    @Column(length = 14, unique = true, nullable = false)
    private String cpf;
    @Column(nullable = false)
    private LocalDate dataNascimento;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    //@ManyToOne
    //@OneToOne
    //@ManyToMany
    @JoinColumn(name = "pessoa_id", foreignKey = @ForeignKey(name = "fk_endereco_id"))
    //@ManyToOne
    private List<Endereco> enderecos;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Exercicio> exercicios;
}
