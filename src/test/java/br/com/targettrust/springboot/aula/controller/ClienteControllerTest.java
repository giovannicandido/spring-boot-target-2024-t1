package br.com.targettrust.springboot.aula.controller;

import br.com.targettrust.springboot.aula.service.ClienteService;
import br.com.targettrust.springboot.aula.service.ExercicioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ClienteController.class)
@AutoConfigureMockMvc
public class ClienteControllerTest {

    @MockBean
    private ClienteService clienteService;

    @MockBean
    private ExercicioService exercicioService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test() {
//        mockMvc.perform()
    }

}
