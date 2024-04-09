package br.com.targettrust.springboot.aula.controller;

import br.com.targettrust.springboot.aula.dto.AssociarExercicioRequest;
import br.com.targettrust.springboot.aula.model.Cliente;
import br.com.targettrust.springboot.aula.model.Exercicio;
import br.com.targettrust.springboot.aula.service.ClienteService;
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
    public Cliente createCliente(@RequestBody Cliente cliente) {
        return clienteService.createCliente(cliente);
    }

    /**
     * todo mais filtros
     * @param nome
     * @param idade
     * @return
     */
    @GetMapping
    public List<Cliente> listClientes(
            @RequestParam( name = "nome", required = false) String nome,
            @RequestParam(name = "idade", required = false) Integer idade
    ) {

        return clienteService.listClientes(nome);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable(name = "id") Integer id) {
        return clienteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Cliente> editCliente(@PathVariable(name = "id") Integer id, @RequestBody Cliente cliente) {

        return clienteService.editCliente(id, cliente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Cliente> editClienteParcial(@PathVariable(name = "id") Integer id, @RequestBody Cliente cliente) {

        return clienteService.editarParcial(id, cliente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }




    @DeleteMapping(path = "/{id}")
    public void deleteCliente(@PathVariable(name = "id") Integer id) {
        clienteService.deletarCliente(id);
    }

    @PostMapping(path = "/{id}/exercicios")
    public void associateExercicios(
            @PathVariable("id") Integer idCliente,
            @RequestBody @Valid AssociarExercicioRequest associarExercicioRequest) {

        log.info("Associar exercicio para Cliente de id " + idCliente);
        log.info(associarExercicioRequest.getExercicios().stream()
                .map(Object::toString)
                .collect(Collectors.joining(",")));
        // todo implementar logica de associacao
        // ir no banco localizar a Cliente
        // localizar os ids dos exercicios
        // crio cada associação (nova tabela de associacao) uma para cada id passado
        // salva a associacao
        // retorna ok
    }

    @GetMapping(path = "/{id}/exercicios")
    public List<Exercicio> listarExercicios(
            @PathVariable("id") Integer idCliente) {

        // todo implementar lista de exercicio
        return new ArrayList<>();
    }


}
