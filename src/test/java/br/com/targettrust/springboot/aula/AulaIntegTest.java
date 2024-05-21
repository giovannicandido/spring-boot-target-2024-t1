package br.com.targettrust.springboot.aula;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY, connection = EmbeddedDatabaseConnection.H2)
// configurar um H2 ou derby, ou 3
@ActiveProfiles("test")
public @interface AulaIntegTest {
}
