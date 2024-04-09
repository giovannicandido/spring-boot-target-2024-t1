package br.com.targettrust.springboot.aula.service;

import br.com.targettrust.springboot.aula.model.Cliente;
import br.com.targettrust.springboot.aula.model.Exercicio;
import br.com.targettrust.springboot.aula.model.exceptions.RegistryNotFoundException;
import br.com.targettrust.springboot.aula.repository.ExercicioRepository;
import br.com.targettrust.springboot.aula.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ExercicioRepository exercicioRepository;

    private List<Cliente> clientes = new ArrayList<>();
    public List<Cliente> listClientes(String nome) {
        return clienteRepository.findAll();

    }

    private int findCliente(Integer id) {

        var cliente = clientes.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);

        return clientes.indexOf(cliente);
    }

    // 1. Organization
    //   2. Policies
    //   3. Policy Options
    //      4. Calculation Methods
    //        5. Inputs
    //        5. Results


//    @Transactional
    public Cliente createCliente(Cliente cliente) {

        List<Exercicio> exercicios = new ArrayList<>();

        cliente.getExercicios().forEach(exercicio -> {

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
        cliente.setExercicios(exercicios);
        return clienteRepository.create(cliente);
    }

    public Optional<Cliente> findById(Integer id) {
        int index = findCliente(id);
        // geralmente lancamos exceção e tratamos em um local apenas o notfound.
        // ou seja não precisa desse if - else repetindo no controlador.
        if (index != -1) {
            return Optional.of(clientes.get(index));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Cliente> editCliente(Integer id, Cliente cliente) {
        int posicao = findCliente(id);
        if (posicao != -1) {
            cliente.setId(Long.valueOf(id));
            clientes.set(posicao, cliente);
            return Optional.of(cliente);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Cliente> editarParcial(Integer id, Cliente cliente) {
        int posicao = findCliente(id);
        if (posicao != -1) {
            cliente.setId(Long.valueOf(id));
            // fixme atualização tem que ser parcial.
            clientes.set(posicao, cliente);
            return Optional.of(cliente);
        } else {
            return Optional.empty();
        }
    }

    public void deletarCliente(Integer id) {
        int posicao = findCliente(id);
        if (posicao != -1) {
            clientes.remove(posicao);
        } else {
            throw new RegistryNotFoundException(Long.valueOf(id));
        }
    }
}
