package br.com.targettrust.springboot.aula.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@SequenceGenerator(name = "cliente_seq", sequenceName = "cliente_seq", allocationSize = 1)
@Builder
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq" )
    private Long id;
    @Column(length = 50, nullable = false)
    private String nome;
    @Column(length = 14, unique = true, nullable = false)
    private String cpf;
    @Column(nullable = false)
    private LocalDate dataNascimento;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "cliente", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Endereco> enderecos;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "cliente_exercicio_personal",
//            joinColumns = {
//                    @JoinColumn(name = "cliente_id", foreignKey = @ForeignKey(name = "fk_cliente_exercicio_personal_cliente_id"))
//            },
//            inverseJoinColumns = {@JoinColumn(name = "exercicio_id")
//
//            }
//    )
//    private List<Exercicio> exercicios;
    @OneToMany(mappedBy = "cliente")
    private List<ClienteExercicio> clienteExercicio;
}
