package br.com.targettrust.springboot.aula.dto.response;

import br.com.targettrust.springboot.aula.model.Cliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteResponse {
    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private List<EnderecoResponse> enderecos;

    public Long getIdade() {
        return ChronoUnit.YEARS.between(dataNascimento, LocalDate.now());
    }

    public static ClienteResponse fromModel(Cliente cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .dataNascimento(cliente.getDataNascimento())
                .enderecos(
                        cliente.getEnderecos().stream()
                                .map(EnderecoResponse::fromModel)
                                .toList()
                )
                .build();
    }
}
