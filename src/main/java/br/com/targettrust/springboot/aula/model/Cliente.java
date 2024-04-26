package br.com.targettrust.springboot.aula.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

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

    @Column(length = 3)
    private String countryCode;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Endereco> enderecos;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Exercicio> exercicios;

    public boolean isLegalAge(CountryCode country) {
        return getIdadeCliente() >= LegalAgeCountries.legalAges.get(country.getCode());
    }

    public Long getIdadeCliente() {
        return ChronoUnit.YEARS.between(dataNascimento, LocalDate.now());
    }

}
