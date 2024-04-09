package br.com.targettrust.springboot.aula.dto.response;

import br.com.targettrust.springboot.aula.model.Endereco;
import lombok.Builder;

@Builder
public class EnderecoResponse {
    private Long id;
    private String rua;
    private Integer numero;

    public static EnderecoResponse fromModel(Endereco endereco) {
        return EnderecoResponse
                .builder()
                .id(endereco.getId())
                .numero(endereco.getNumero())
                .rua(endereco.getRua())
                .build();
    }
}
