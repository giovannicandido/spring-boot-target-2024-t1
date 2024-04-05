package br.com.targettrust.springboot.aula.repository;

import br.com.targettrust.springboot.aula.model.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {
}
