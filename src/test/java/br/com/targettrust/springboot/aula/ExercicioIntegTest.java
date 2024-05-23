package br.com.targettrust.springboot.aula;

import br.com.targettrust.springboot.aula.dto.request.ExercicioRequest;
import br.com.targettrust.springboot.aula.model.DiaExercio;
import br.com.targettrust.springboot.aula.model.Exercicio;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AulaIntegTest
public class ExercicioIntegTest {

    @Autowired
    private MockMvc mockMvc;
    // Jackson
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private EntityManager entityManager;


    @Test
    public void when_createExercicio_should_createOneRegistry() throws Exception {
        ExercicioRequest request = new ExercicioRequest("teste 1", "braco");
        ExercicioRequest request2 = new ExercicioRequest("teste 2", "braco 2");
        String json = mapper.writeValueAsString(request);
        String json2 = mapper.writeValueAsString(request2);
        mockMvc.perform(
                        post("/exercicios")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", Matchers.is("teste 1")))
                .andExpect(jsonPath("$.parteCorpo", Matchers.is("braco")))
                .andExpect(jsonPath("$.id", Matchers.notNullValue()));

        mockMvc.perform(
                        post("/exercicios")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json2)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", Matchers.is("teste 2")))
                .andExpect(jsonPath("$.parteCorpo", Matchers.is("braco 2")))
                .andExpect(jsonPath("$.id", Matchers.notNullValue()));

        List<Exercicio> exercicios = entityManager.createQuery("select e from Exercicio e", Exercicio.class)
                .getResultList();

//        assertThat(exercicios
//                .stream()
//                .map(Exercicio::getNome)
//                .toList()
//        ).containsOnly("teste 1");

//        assertThat(exercicios
//                .stream()
//                .map(Exercicio::getParteCorpo)
//                .toList()
//        ).containsOnly("braco");


        var expected = List.of(
                new Exercicio(0L, "teste 1", "braco", new DiaExercio()),
                new Exercicio(0L, "teste 2", "braco 2", new DiaExercio())
        );

        assertThat(exercicios)
//                .usingRecursiveComparison()
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "diaExercicio")
                .containsExactlyInAnyOrderElementsOf(expected);


    }
}
