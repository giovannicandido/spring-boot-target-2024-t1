package br.com.targettrust.springboot.aula.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnderecoEditRequest {
    private Long id;
    private String rua;
    private Integer numero;
}
