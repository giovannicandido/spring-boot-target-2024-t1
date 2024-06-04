package br.com.targettrust.springboot.aula.controller;

import br.com.targettrust.springboot.aula.dto.request.AssociarExercicioRequest;
import br.com.targettrust.springboot.aula.dto.request.ClienteRequest;
import br.com.targettrust.springboot.aula.dto.response.ClienteResponse;
import br.com.targettrust.springboot.aula.model.Cliente;
import br.com.targettrust.springboot.aula.model.Endereco;
import br.com.targettrust.springboot.aula.model.Exercicio;
import br.com.targettrust.springboot.aula.service.ClienteService;
import br.com.targettrust.springboot.aula.service.ExercicioService;
import com.c4_soft.springaddons.security.oidc.OpenidClaimSet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping(path = "/clientes")
@Slf4j
@RequiredArgsConstructor // Cria um construtor com todos as propriedades final
@Tag(name = "Cliente Feature", description = "Operacoes relacionadas a uma Cliente no sistema (cliente pessoa fisica ou juridica)")
public class ClienteController {
    private final ClienteService clienteService;
    private final ExercicioService exercicioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar uma Cliente", description = "Cria uma registro de Cliente no sistema", tags = {"Cliente"},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Cliente criada com sucesso",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "Cliente",
                                            description = "Cliente registrada no banco",
                                            // todo verificar como passar um arquivo externo de exemplo
                                            externalValue = "swagger-examples/cliente.json"
                                    )
                            )

                    )
            }
    )
    @PreAuthorize("hasRole('MANAGER')")
    public ClienteResponse createCliente(@RequestBody @Valid ClienteRequest cliente, @AuthenticationPrincipal OpenidClaimSet token) {
        log.info(token.getEmail());
        return new ClienteResponse(1L,"nome", "", LocalDate.now(), null);
//        return ClienteResponse.fromModel(clienteService.createCliente(cliente.toModel(), cliente.getCep()));
    }

    /**
     * todo mais filtros
     *
     * @param nome
     * @param idade
     * @return
     */
    @GetMapping
    public List<ClienteResponse> listClientes(
            @RequestParam(name = "nome", required = false) String nome,
            @RequestParam(name = "idade", required = false) Integer idade
    ) {

        Map<Long, ClienteResponse> somaIdades = clienteService.listClientes(nome)
                .stream() // pode ser infinito RxJava
                .parallel()
                .map(ClienteResponse::fromModel)
                .filter(this.testFilter(false, true))
                .filter(clienteResponse -> clienteResponse.getCpf().startsWith("0"))
//                .map(client -> client.getNome())
                .skip(5)
//                .map(ClienteResponse::getIdade)
//                .reduce(Long::sum);
                .collect(Collectors.toMap(ClienteResponse::getIdade, Function.identity()));
        // Faixas Etarias
        List<ClienteResponse> de2A10Anos = new ArrayList<>();
        List<ClienteResponse> finalDe2A10Anos = de2A10Anos;

        List<ClienteResponse> de18A32Anos = new ArrayList<>();
        List<ClienteResponse> finalDe18A32Anos = de18A32Anos;

        IntStream.range(2, 11)
                .forEach(idadeRange -> {
                    finalDe2A10Anos.add(somaIdades.get(idadeRange));
                });

        de2A10Anos = de2A10Anos.stream().filter(Objects::isNull).collect(Collectors.toList());

        IntStream.range(18, 32)
                .forEach(idadeRange -> {
                    finalDe18A32Anos.add(somaIdades.get(idadeRange));
                });
        // pacote -> versao -> politicas -> opcoes -> metodos de calculo -> valores
        //                                         -> resultados
        // parallel stream
        // Collectors
        // flatMap e Map
        // reduce

        return new ArrayList<>();
    }

    private Predicate<ClienteResponse> testFilter(boolean filterByName, boolean filterByCpf) {
        if (filterByName) {
            return (cliente) -> cliente.getNome().startsWith(cliente.getNome());
        } else if (filterByCpf) {
            return (cliente) -> cliente.getCpf().equalsIgnoreCase(cliente.getCpf());
        }
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> findById(@PathVariable(name = "id") Long id) {
        return clienteService.findById(id)
                .map(ClienteResponse::fromModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ClienteResponse> editCliente(@PathVariable(name = "id") Long id, @RequestBody @Valid ClienteRequest cliente) {

        return clienteService.editCliente(id, cliente.toModel())
                .map(ClienteResponse::fromModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClienteResponse> editClienteParcial(@PathVariable(name = "id") Long id, @RequestBody @Valid ClienteRequest cliente) {

        return clienteService.editarParcial(id, cliente.toModel())
                .map(ClienteResponse::fromModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping(path = "/{id}")
    public void deleteCliente(@PathVariable(name = "id") Long id) {
        clienteService.deletarCliente(id);
    }

    @PostMapping(path = "/{id}/exercicios")
    public void associateExercicios(
            @PathVariable("id") Long idCliente,
            @RequestBody @Valid AssociarExercicioRequest associarExercicioRequest) {

        log.info("Associar exercicio para Cliente de id " + idCliente);
        log.info(associarExercicioRequest.getExercicios().stream()
                .map(Object::toString)
                .collect(Collectors.joining(",")));
        exercicioService.associarExercicios(idCliente, associarExercicioRequest.getExercicios());
    }

    @GetMapping(path = "/{id}/exercicios")
    public List<Exercicio> listarExercicios(
            @PathVariable("id") Long idCliente) {

        return exercicioService.findAllExerciciosByClienteId(idCliente);
    }

    @GetMapping(path = "/get-client-open-session")
//    @Transactional(readOnly = true)
    public List<Cliente> getClientOpenSession() {
        var clientes = clienteService.listClientes("ignorado");
        clientes.forEach(cliente -> {
                    List<Endereco> endereco = cliente.getEnderecos();
                    endereco.forEach(e -> log.info(e.toString()));
                }
        );
        return clientes;
    }


}
