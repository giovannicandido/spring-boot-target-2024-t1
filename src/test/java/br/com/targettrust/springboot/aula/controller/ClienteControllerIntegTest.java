package br.com.targettrust.springboot.aula.controller;

import br.com.targettrust.springboot.aula.AulaIntegTest;
import br.com.targettrust.springboot.aula.dto.request.ClienteRequest;
import br.com.targettrust.springboot.aula.model.Cliente;
import br.com.targettrust.springboot.aula.model.Endereco;
import br.com.targettrust.springboot.aula.service.CepService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityManager;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AulaIntegTest
class ClienteControllerIntegTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @MockBean
    private CepService cepService;


    private ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());


    @Test
    @Transactional(readOnly = false)
    void given_validCep_when_createCliente_then_shouldSaveClientWithAddress() throws Exception {

        when(cepService.searchAddress(any()))
                .thenReturn(new Endereco(null,  "Praça da Sé", null, "Sé", "SP", "BR", null));

        var clienteRequest = new ClienteRequest("joao", "743.842.560-60",
                LocalDate.now().minus(22, ChronoUnit.YEARS),
                new ArrayList<>(),
                "9999939430"
        );

        var json = objectMapper.writeValueAsString(clienteRequest);

        mockMvc.perform(post(
                        "/clientes"
                ).contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", Matchers.is("joao")));

        List<Cliente> clientes = entityManager.createQuery("select c from Cliente c", Cliente.class)
                .getResultList();

        assertThat(clientes).hasSize(1);

        assertThat(clientes.get(0).getNome()).isEqualTo("joao");
        assertThat(clientes.get(0).getCpf()).isEqualTo("743.842.560-60");

        assertThat(clientes.get(0).getEnderecos()).hasSize(1);
        assertThat(clientes.get(0).getEnderecos().get(0).getRua()).isEqualTo("Praça da Sé");
        assertThat(clientes.get(0).getEnderecos().get(0).getBairro()).isEqualTo("Sé");
        assertThat(clientes.get(0).getEnderecos().get(0).getEstado()).isEqualTo("SP");

    }
}