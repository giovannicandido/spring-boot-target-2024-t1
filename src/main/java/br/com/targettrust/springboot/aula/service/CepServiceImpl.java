package br.com.targettrust.springboot.aula.service;

import br.com.targettrust.springboot.aula.model.Endereco;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CepServiceImpl implements CepService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final static String URL = "https://viacep.com.br/ws/%s/json/";

    @Override
    public Endereco searchAddress(String cep) {

        var responseDTO = restTemplate.getForObject(String.format(URL, cep), EnderecoViaCepResponseDTO.class);

        if(responseDTO == null) {
            return null;
        }

        return new Endereco(
                null,
                responseDTO.getLogradouro(),
                null,
                responseDTO.getBairro(),
                responseDTO.getUf(),
                "BR",
                null
        );

    }
}
