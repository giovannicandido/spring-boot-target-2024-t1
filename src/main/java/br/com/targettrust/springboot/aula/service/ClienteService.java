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

    public List<Cliente> listClientes(String nome) {
//        exercicioRepository.deleteA
        return clienteRepository.findAll();

    }

    // 1. Organization
    //   2. Policies
    //   3. Policy Options
    //      4. Calculation Methods
    //        5. Inputs
    //        5. Results


//    @Transactional
    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.create(cliente);
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
