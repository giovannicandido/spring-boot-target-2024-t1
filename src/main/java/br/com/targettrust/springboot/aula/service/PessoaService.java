package br.com.targettrust.springboot.aula.service;

import br.com.targettrust.springboot.aula.model.Endereco;
import br.com.targettrust.springboot.aula.model.Pessoa;
import br.com.targettrust.springboot.aula.model.exceptions.RegistryNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PessoaService {
    private List<Endereco> enderecos = List.of(
            new Endereco(1L,"Attilio bilibio", 200),
            new Endereco(2L,"Antonio de Carvalho", 1000)
    );

    private List<Pessoa> pessoas = new ArrayList<>(
            List.of(
                    new Pessoa(1L, "Maria", "00001",
                            LocalDate.now().minus(20, ChronoUnit.YEARS),
                            enderecos),
                    new Pessoa(2L, "Joao", "00002",
                            LocalDate.now().minus(34, ChronoUnit.YEARS),
                            enderecos),
                    new Pessoa(3L, "Lucia", "00003",
                            LocalDate.now().minus(77, ChronoUnit.YEARS),
                            enderecos)
            )
    );

    public List<Pessoa> listarPessoas(String nome) {

        return pessoas.stream()
                .filter(pessoa -> pessoa.getNome().contains(nome))
                .collect(Collectors.toList());

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

    public Pessoa criarPessoa(Pessoa pessoa) {
        pessoa.setId(pessoas.size() + 1L);
        pessoas.add(pessoa);
        return pessoa;
    }

    public Optional<Pessoa> findById(Integer id) {
        int index = localizarPessoa(id);
        // geralmente lancamos exceção e tratamos em um local apenas o notfound.
        // ou seja não precisa desse if - else repetindo no controlador.
        if (index != -1) {
            return Optional.of(pessoas.get(index));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Pessoa> editarPessoa(Integer id, Pessoa pessoa) {
        int posicao = localizarPessoa(id);
        if (posicao != -1) {
            pessoa.setId(Long.valueOf(id));
            pessoas.set(posicao, pessoa);
            return Optional.of(pessoa);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Pessoa> editarParcial(Integer id, Pessoa pessoa) {
        int posicao = localizarPessoa(id);
        if (posicao != -1) {
            pessoa.setId(Long.valueOf(id));
            // fixme atualização tem que ser parcial.
            pessoas.set(posicao, pessoa);
            return Optional.of(pessoa);
        } else {
            return Optional.empty();
        }
    }

    public void deletarPessoa(Integer id) {
        int posicao = localizarPessoa(id);
        if (posicao != -1) {
            pessoas.remove(posicao);
        } else {
            throw new RegistryNotFoundException(id);
        }
    }
}
