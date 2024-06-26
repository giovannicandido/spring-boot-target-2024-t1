package br.com.targettrust.springboot.aula.dto.request;

import br.com.targettrust.springboot.aula.model.Endereco;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoRequest {

    private Long id;

    @NotBlank
    private String rua;
    private String estado;

    @NotNull
    @Min(1)
    private Integer numero;

    public Endereco toModel() {
        return new Endereco(id, rua, numero, "bairro", "br", estado, null);
    }

}
