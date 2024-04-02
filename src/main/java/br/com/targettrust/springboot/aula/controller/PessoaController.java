package br.com.targettrust.springboot.aula.controller;

import br.com.targettrust.springboot.aula.dto.AssociarExercicioRequest;
import br.com.targettrust.springboot.aula.model.Exercicio;
import br.com.targettrust.springboot.aula.model.Pessoa;
import br.com.targettrust.springboot.aula.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/pessoas")
@Slf4j
@RequiredArgsConstructor // Cria um construtor com todos as propriedades final
public class PessoaController {
//    private Logger logger = LoggerFactory.getLogger(PessoaController.class);

//    @Autowired // injeção por propriedade
    private final PessoaService pessoaService;

    // Injeção por construção é a recomendada por causa de testes unitários
//    public PessoaController(PessoaService pessoaService) {
//        this.pessoaService = pessoaService;
//    }

    // injecao por setter - ninguem usa
//    @Autowired
//    public void setPessoaService(PessoaService pessoaService) {
//        this.pessoaService = pessoaService;
//    }


    // jackson para json transforma o json enviado em Pessoa, isso
    // por causa do @RequestBody
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa criarPessoa(@RequestBody Pessoa pessoa) {
        return pessoaService.criarPessoa(pessoa);
    }

    @GetMapping
    public List<Pessoa> listarPessoas(
            @RequestParam( name = "nome", required = false) String nome,
            @RequestParam(name = "idade", required = false) Integer idade
//            @ FiltroRequest filtro
    ) {

        return pessoaService.listarPessoas(nome);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> findById(@PathVariable(name = "id") Integer id) {
//        Pessoa pessoa = pessoaService.findById(id);
//        return pessoa == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(pessoa);
        return pessoaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Pessoa> editarPessoa(@PathVariable(name = "id") Integer id, @RequestBody Pessoa pessoa) {

        return pessoaService.editarPessoa(id, pessoa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Pessoa> editarPessoaParcial(@PathVariable(name = "id") Integer id, @RequestBody Pessoa pessoa) {

        return pessoaService.editarParcial(id, pessoa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }




    @DeleteMapping(path = "/{id}")
    public void deletarPessoa(@PathVariable(name = "id") Integer id) {
        pessoaService.deletarPessoa(id);
    }

    @PostMapping(path = "/{id}/exercicios")
    public void associarExercicios(
            @PathVariable("id") Integer idPessoa,
            @RequestBody @Valid AssociarExercicioRequest associarExercicioRequest) {

        log.info("Associar exercicio para pessoa de id " + idPessoa);
        log.info(associarExercicioRequest.getExercicios().stream()
                .map(Object::toString)
                .collect(Collectors.joining(",")));
        // todo implementar logica de associacao
        // ir no banco localizar a pessoa
        // localizar os ids dos exercicios
        // crio cada associação (nova tabela de associacao) uma para cada id passado
        // salva a associacao
        // retorna ok
    }

    @GetMapping(path = "/{id}/exercicios")
    public List<Exercicio> listarExercicios(
            @PathVariable("id") Integer idPessoa) {

        // todo implementar lista de exercicio
        return new ArrayList<>();
    }


}
