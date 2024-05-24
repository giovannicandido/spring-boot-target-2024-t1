package br.com.targettrust.springboot.aula.service;

import br.com.targettrust.springboot.aula.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    List<Cliente> listClientes(String nome);

    Cliente createCliente(Cliente cliente, String cep);

    Optional<Cliente> findById(Long id);

    Optional<Cliente> editCliente(Long id, Cliente cliente);

    Optional<Cliente> editarParcial(Long id, Cliente cliente);

    void deletarCliente(Long id);
}
