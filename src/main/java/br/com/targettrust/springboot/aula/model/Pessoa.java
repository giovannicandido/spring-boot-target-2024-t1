package br.com.targettrust.springboot.aula.model;

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
public class Pessoa {
    private Integer id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private List<Endereco> enderecos;
}
