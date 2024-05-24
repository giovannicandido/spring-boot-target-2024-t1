package br.com.targettrust.springboot.aula.service;

import br.com.targettrust.springboot.aula.model.Endereco;

public interface CepService {
    Endereco searchAddress(String cep);
}
