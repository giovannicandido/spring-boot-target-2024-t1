package br.com.targettrust.springboot.aula;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@AulaIntegTest
@Slf4j
class AulaApplicationTests {
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
