package br.com.targettrust.springboot.aula.service;

import br.com.targettrust.springboot.aula.model.Cliente;
import br.com.targettrust.springboot.aula.model.Endereco;
import br.com.targettrust.springboot.aula.model.exceptions.ExercicioIdsNotFoundException;
import br.com.targettrust.springboot.aula.repository.ClienteRepository;
import br.com.targettrust.springboot.aula.repository.ExercicioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ExercicioRepository exercicioRepository;
    private final ExercicioService exercicioService;
    private final CepService cepService;

    @Transactional(readOnly = true)
    public List<Cliente> listClientes(String nome) {
//        exercicioRepository.deleteA
        var clientes =  clienteRepository.findAll();
        for (Cliente cliente : clientes) {
            List<Endereco> enderecos = cliente.getEnderecos();
            enderecos.forEach(endereco -> log.info(endereco.toString()));
        }
        return clientes;

    }

    // 1. Organization
    //   2. Policies
    //   3. Policy Options
    //      4. Calculation Methods
    //        5. Inputs
    //        5. Results

    @Transactional(noRollbackFor = ExercicioIdsNotFoundException.class)
    public Cliente createCliente(Cliente cliente, String cep) {
        Endereco endereco = cepService.searchAddress(cep);
        if(endereco != null) {
            cliente.getEnderecos().add(endereco);
        }
        var clienteSaved = clienteRepository.create(cliente);
//        exercicioService.associarExercicios(200L, List.of(3L));
        return clienteSaved;
    }

    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> editCliente(Long id, Cliente cliente) {
        return Optional.ofNullable(clienteRepository.update(cliente, id));
    }

    public Optional<Cliente> editarParcial(Long id, Cliente cliente) {
        // Edicao teria que ser parcial
        return Optional.ofNullable(clienteRepository.update(cliente, id));
    }

    public void deletarCliente(Long id) {
       clienteRepository.deleteById(id);
    }
}
