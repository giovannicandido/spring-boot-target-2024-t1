package br.com.targettrust.springboot.aula.repository;

import br.com.targettrust.springboot.aula.model.Pessoa;
import br.com.targettrust.springboot.aula.model.exceptions.RegistryNotFoundException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PessoaRepository {
    private final EntityManager entityManager;

    @Transactional
    public Pessoa create(Pessoa pessoa) {
        entityManager.persist(pessoa);
        return pessoa;
    }


    public void update(Pessoa pessoa, Long id) {
        Pessoa pessoaNoBanco = entityManager.find(Pessoa.class, id);
        if(pessoaNoBanco != null) {
//            pessoa.setId(pessoaNoBanco.getId());
//            entityManager.merge(pessoa);
            pessoaNoBanco.setNome(pessoa.getNome());
            pessoaNoBanco.setCpf(pessoa.getCpf());
            pessoaNoBanco.setDataNascimento(pessoa.getDataNascimento());
            // maior controle nas relacoes
            // todo ver regras de update do jpa

//            pessoa.getExercicios().forEach(exercicio -> {
//                entityManager.merge(exercicio)
//            });

            pessoaNoBanco.setExercicios(pessoa.getExercicios());
            pessoaNoBanco.setEnderecos(pessoa.getEnderecos());
            entityManager.merge(pessoaNoBanco);
        } else {
            throw new RegistryNotFoundException(id);
        }
    }

    public List<Pessoa> findAll() {
        // JPQL
        return entityManager.createQuery("select p from Pessoa p", Pessoa.class)
                .getResultList();
    }
}
