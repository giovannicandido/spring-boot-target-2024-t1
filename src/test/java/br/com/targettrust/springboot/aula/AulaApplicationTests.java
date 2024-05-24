package br.com.targettrust.springboot.aula;

import br.com.targettrust.springboot.aula.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@AulaIntegTest
@Slf4j
class AulaApplicationTests {

    @Autowired
    private ClienteService clienteService;
//    private static PostgreSQLContainer postgres;
    // Refatorar para reaproveitar codigo na proxima aula com uma anotação do Junit.
//    @BeforeAll
//    public static void setup() {
//        var postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16"));
//        AulaApplicationTests.postgres = postgres;
//        postgres.start();
//    }

//    @BeforeEach
//    void setUp() {
//        log.info(postgres.getJdbcUrl());
//        log.info(postgres.getUsername());
//        log.info(postgres.getPassword());
//
//
//    }
//
//    @DynamicPropertySource
//    static void databaseProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.username", postgres::getUsername);
//        registry.add("spring.datasource.password", postgres::getPassword);
//    }

    @Test
    void contextLoads() {
    }

}
