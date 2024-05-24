package br.com.targettrust.springboot.aula.service;

import br.com.targettrust.springboot.aula.model.Exercicio;

import java.util.List;

public interface ExercicioService {
    Exercicio createExercicio(Exercicio exercicio);

    void associarExercicios(Long idCliente, List<Long> idExercicios);

    List<Exercicio> findAllExerciciosByClienteId(Long clienteId);
}
