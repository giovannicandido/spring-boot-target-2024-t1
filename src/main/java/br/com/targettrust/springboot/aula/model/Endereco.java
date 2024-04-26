package br.com.targettrust.springboot.aula.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

//@Getter
//@Setter
//@ToString
//@AllArgsConstructor
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SequenceGenerator(name = "endereco_seq", sequenceName = "endereco_seq", allocationSize = 1)
@ToString(exclude = "cliente")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco_seq")
    private Long id;
    private String rua;
    private Integer numero;
    private String bairro;

    @Column(length = 3)
    private String countryCode;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", foreignKey = @ForeignKey(name = "fk_endereco_id"), nullable = false)
    @JsonIgnore
    private Cliente cliente;
}
