package br.com.targettrust.springboot.aula.repository;

import br.com.targettrust.springboot.aula.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
