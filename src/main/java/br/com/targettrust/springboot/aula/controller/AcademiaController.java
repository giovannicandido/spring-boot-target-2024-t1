package br.com.targettrust.springboot.aula.controller;

import br.com.targettrust.springboot.aula.model.Academia;
import br.com.targettrust.springboot.aula.model.Endereco;

public class AcademiaController {
    public Academia salvar() {
        Academia academia = new Academia(1, "Malhacao", new Endereco("attilio", 200));
        Integer id = academia.id();
        return academia;
    }
}
