package br.com.targettrust.springboot.aula.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

//@Getter
//@Setter
//@ToString
//@AllArgsConstructor
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Endereco {
    @Id
    private Long id;
    private String rua;
    private Integer numero;
}
