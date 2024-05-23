package br.com.targettrust.springboot.aula.test;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainerProvider;

public class PostgisDBContainer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static JdbcDatabaseContainer<?> postgres;
    private final static String PASSWORD = "1234";
    private final static String USERNAME = "aula";
    static {
        postgres = (JdbcDatabaseContainer<?>) new PostgreSQLContainerProvider()
                .newInstance("15")
                .withDatabaseName(USERNAME)
                .withPassword(PASSWORD)
                .withUsername(USERNAME)
                .withReuse(false);

        postgres.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        var testPropertyValues = TestPropertyValues.of(
                "spring.datasource.username=" + USERNAME,
                "spring.datasource.password=" + PASSWORD,
                "spring.datasource.url=" + postgres.getJdbcUrl()
                );
        testPropertyValues.applyTo(applicationContext);

    }
}
