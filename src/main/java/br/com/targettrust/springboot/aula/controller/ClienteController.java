package br.com.targettrust.springboot.aula.controller;

import br.com.targettrust.springboot.aula.dto.request.AssociarExercicioRequest;
import br.com.targettrust.springboot.aula.dto.request.ClienteRequest;
import br.com.targettrust.springboot.aula.dto.response.ClienteResponse;
import br.com.targettrust.springboot.aula.model.Cliente;
import br.com.targettrust.springboot.aula.model.Endereco;
import br.com.targettrust.springboot.aula.model.Exercicio;
import br.com.targettrust.springboot.aula.service.ClienteService;
import br.com.targettrust.springboot.aula.service.ExercicioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public ClienteResponse createCliente(@RequestBody @Valid ClienteRequest cliente) {
        return ClienteResponse.fromModel(clienteService.createCliente(cliente.toModel(), cliente.getCep()));
    }

    /**
     * todo mais filtros
     * @param nome
     * @param idade
     * @return
     */
    @GetMapping
    public List<ClienteResponse> listClientes(
            @RequestParam( name = "nome", required = false) String nome,
            @RequestParam(name = "idade", required = false) Integer idade
    ) {

        return clienteService.listClientes(nome)
                .stream()
                .map(ClienteResponse::fromModel)
                .toList();
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
