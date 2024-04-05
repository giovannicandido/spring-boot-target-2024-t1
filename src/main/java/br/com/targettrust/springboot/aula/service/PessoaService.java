package br.com.targettrust.springboot.aula.service;

import br.com.targettrust.springboot.aula.model.Endereco;
import br.com.targettrust.springboot.aula.model.Exercicio;
import br.com.targettrust.springboot.aula.model.Pessoa;
import br.com.targettrust.springboot.aula.model.exceptions.RegistryNotFoundException;
import br.com.targettrust.springboot.aula.repository.ExercicioRepository;
import br.com.targettrust.springboot.aula.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PessoaService {
    private final PessoaRepository pessoaRepository;
    private final ExercicioRepository exercicioRepository;

    private List<Pessoa> pessoas = new ArrayList<>();
    public List<Pessoa> listarPessoas(String nome) {
        return pessoaRepository.findAll();
//        return pessoas.stream()
//                .filter(pessoa -> (nome != null && !nome.trim().equals("")) ? pessoa.getNome().contains(nome) : true )
//                .collect(Collectors.toList());

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

    // 1. Organization
    //   2. Policies
    //   3. Policy Options
    //      4. Calculation Methods
    //        5. Inputs
    //        5. Results


//    @Transactional
    public Pessoa criarPessoa(Pessoa pessoa) {

        List<Exercicio> exercicios = new ArrayList<>();

        pessoa.getExercicios().forEach(exercicio -> {

            // busquei do banco
            Optional<Exercicio> exercicioNoBanco = exercicio.getId() == null ? Optional.empty() : exercicioRepository.findById(exercicio.getId());
//            exercicioRepository.
            if(exercicioNoBanco.isPresent()) {
                exercicios.add(exercicioNoBanco.get());
            } else {
                Exercicio exercicioNoBanco2 = exercicioRepository.save(exercicio);
                // usuario enviou
                exercicios.add(exercicioNoBanco2);
            }
        });
        pessoa.setExercicios(exercicios);
        return pessoaRepository.create(pessoa);
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
            throw new RegistryNotFoundException(Long.valueOf(id));
        }
    }
}
