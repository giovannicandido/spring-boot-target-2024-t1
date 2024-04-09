package br.com.targettrust.springboot.aula.repository;

import br.com.targettrust.springboot.aula.model.Cliente;
import br.com.targettrust.springboot.aula.model.exceptions.RegistryNotFoundException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClienteRepository {
    private final EntityManager entityManager;

    @Transactional
    public Cliente create(Cliente cliente) {
        entityManager.persist(cliente);
        return cliente;
    }


    public void update(Cliente cliente, Long id) {
        Cliente clienteNoBanco = entityManager.find(Cliente.class, id);
        if(clienteNoBanco != null) {
            clienteNoBanco.setNome(cliente.getNome());
            clienteNoBanco.setCpf(cliente.getCpf());
            clienteNoBanco.setDataNascimento(cliente.getDataNascimento());
            // maior controle nas relacoes
            // todo ver regras de update do jpa

//            cliente.getExercicios().forEach(exercicio -> {
//                entityManager.merge(exercicio)
//            });

            clienteNoBanco.setExercicios(cliente.getExercicios());
            clienteNoBanco.setEnderecos(cliente.getEnderecos());
            entityManager.merge(clienteNoBanco);
        } else {
            throw new RegistryNotFoundException(id);
        }
    }

    public List<Cliente> findAll() {
        // JPQL
        return entityManager.createQuery("select p from Cliente p", Cliente.class)
                .getResultList();
    }
}
