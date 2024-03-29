package br.com.targettrust.springboot.aula.controller;

import br.com.targettrust.springboot.aula.dto.AssociarExercicioRequest;
import br.com.targettrust.springboot.aula.model.Endereco;
import br.com.targettrust.springboot.aula.model.Exercicio;
import br.com.targettrust.springboot.aula.model.Pessoa;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/pessoas")
public class PessoaController {
    private List<Endereco> enderecos = List.of(
            new Endereco("Attilio bilibio", 200),
            new Endereco("Antonio de Carvalho", 1000)
    );
    private List<Pessoa> pessoas = new ArrayList<>(
            List.of(
                    new Pessoa(1, "Maria", "00001",
                            LocalDate.now().minus(20, ChronoUnit.YEARS),
                            enderecos),
                    new Pessoa(2, "Joao", "00002",
                            LocalDate.now().minus(34, ChronoUnit.YEARS),
                            enderecos),
                    new Pessoa(3, "Lucia", "00003",
                            LocalDate.now().minus(77, ChronoUnit.YEARS),
                            enderecos)
            )
    );

    // jackson para json transforma o json enviado em Pessoa, isso
    // por causa do @RequestBody
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa criarPessoa(@RequestBody Pessoa pessoa) {
        // todo salvar pessoa numa lista
        pessoa.setId(pessoas.size() + 1);
        pessoas.add(pessoa);
        return pessoa;
    }

    @GetMapping
    public List<Pessoa> listarPessoas(
            @RequestParam("nome") String nome,
            @RequestParam("idade") Integer idade
    ) {
        return pessoas.stream()
                .filter(pessoa -> pessoa.getNome().contains(nome))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> findById(@PathVariable(name = "id") Integer id) {
        int index = localizarPessoa(id);
        // geralmente lancamos exceção e tratamos em um local apenas o notfound.
        // ou seja não precisa desse if - else repetindo no controlador.
        if (index != -1) {
            return ResponseEntity.ok(pessoas.get(index));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Pessoa> editarPessoa(@PathVariable(name = "id") Integer id, @RequestBody Pessoa pessoa) {

        int posicao = localizarPessoa(id);
        if (posicao != -1) {
            pessoa.setId(id);
            pessoas.set(posicao, pessoa);
            return ResponseEntity.ok(pessoa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Pessoa> editarPessoaParcial(@PathVariable(name = "id") Integer id, @RequestBody Pessoa pessoa) {

        int posicao = localizarPessoa(id);
        if (posicao != -1) {
            pessoa.setId(id);
            // fixme atualização tem que ser parcial.
            pessoas.set(posicao, pessoa);
            return ResponseEntity.ok(pessoa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private int localizarPessoa(Integer id) {
        // for
//        for (int index = 0; index < pessoas.size(); index++) {
//            Pessoa searchPessoa = pessoas.get(index);
//            if (id.equals(searchPessoa.getId())) {
//                return index;
//            }
//        }

        var pessoa = pessoas.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        return pessoas.indexOf(pessoa);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deletarPessoa(@PathVariable(name = "id") Integer id) {
        int posicao = localizarPessoa(id);
        if (posicao != -1) {
            pessoas.remove(posicao);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/{id}/exercicios")
    public void associarExercicios(
            @PathVariable("id") Integer idPessoa,
            @RequestBody @Valid AssociarExercicioRequest associarExercicioRequest) {
        System.out.println(associarExercicioRequest.getExercicios().stream()
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
