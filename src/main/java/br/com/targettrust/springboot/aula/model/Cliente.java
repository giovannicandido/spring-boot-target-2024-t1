package br.com.targettrust.springboot.aula.model;

import br.com.targettrust.springboot.aula.model.exceptions.IllegalCountryCodeException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Endereco> enderecos;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Exercicio> exercicios;

    public boolean isLegalAge(CountryCode country) {

        var age = LegalAgeCountries.legalAges.get(country.getCode());
        if(age == null) {
            throw new IllegalCountryCodeException(country.getCode());
        }

        return getIdadeCliente() >= age;
    }

    public Long getIdadeCliente() {
        return ChronoUnit.YEARS.between(dataNascimento, LocalDate.now());
    }

    public List<String> getBairrosCliente() {
        return enderecos == null ? new ArrayList<>() : enderecos.stream()
                .map(Endereco::getBairro)
                .toList();
    }

}
