package br.com.targettrust.springboot.aula.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ClienteRepositoryTest {
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EntityManager entityManager;

    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        clienteRepository = new ClienteRepository(entityManager);
    }


    @Test
    void test() {
        assertThat(enderecoRepository.findAll())
                .isEmpty();
    }
}
