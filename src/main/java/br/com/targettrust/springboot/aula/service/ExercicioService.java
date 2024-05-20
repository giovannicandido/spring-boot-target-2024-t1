package br.com.targettrust.springboot.aula.service;

import br.com.targettrust.springboot.aula.model.Exercicio;
import br.com.targettrust.springboot.aula.model.exceptions.ExercicioIdsNotFoundException;
import br.com.targettrust.springboot.aula.model.exceptions.RegistryNotFoundException;
import br.com.targettrust.springboot.aula.repository.ClienteRepository;
import br.com.targettrust.springboot.aula.repository.ExercicioRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ExercicioService {

    private final ExercicioRepository exercicioRepository;
    private final ClienteRepository clienteRepository;
    private final PlatformTransactionManager transactionManager;
    private final TransactionTemplate transactionTemplate;

//    @PostConstruct
//    private void init() {
//        transactionTemplate = new TransactionTemplate(transactionManager);
//    }

    public Exercicio createExercicio(Exercicio exercicio) {
        final AtomicReference<Exercicio> exercicioSaved = new AtomicReference<>();
        transactionTemplate.execute((TransactionStatus status) -> {
            var exer = exercicioRepository.save(exercicio);
            exercicioSaved.set(exer);
            if(exer.getNome().equals("Nome Updated")) {
                status.setRollbackOnly();
                transactionManager.rollback(status);
                return status;
            }

            transactionManager.commit(status);

            return status;
        });

        return exercicioSaved.get();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void associarExercicios(Long idCliente, List<Long> idExercicios) {
        // ir no banco localizar a Cliente
        var cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RegistryNotFoundException(idCliente));
        // localizar os ids dos exercicios
        List<Exercicio> exercicios = exercicioRepository.findAllById(idExercicios);
        // regra de negocio: se id não exitir no banco não opera a transação

        var idExerciciosNoBanco = exercicios.stream()
                .map(Exercicio::getId)
                .toList();

        List<Long> idsNotExisting = new ArrayList<>();

        for (Long id : idExercicios) {
            if (!idExerciciosNoBanco.contains(id)) {
                idsNotExisting.add(id);
            }
        }

        if(!idsNotExisting.isEmpty()) {
           throw new ExercicioIdsNotFoundException(idsNotExisting);
        }

        // crio cada associação (nova tabela de associacao) uma para cada id passado
        cliente.setExercicios(exercicios);
//        clientRepository.save()
        // salva a associacao
        clienteRepository.update(cliente, idCliente);
        throw new ExercicioIdsNotFoundException(idsNotExisting);
    }

    public List<Exercicio> findAllExerciciosByClienteId(Long clienteId) {
//        return clienteRepository.findById(clienteId)
//                .stream()
//                .map(Cliente::getExercicios)
//                .flatMap(List::stream)
//                .toList();
        var clientes = exercicioRepository.findAllClienteMakingExerciseName("%" + "ros" + "%", Sort.by("nome").descending());

        Pageable page = PageRequest.of(2, 10, Sort.by("nome").descending());
        Page<Exercicio> exerciciosPage = exercicioRepository.findExercicioByNome("%" + "ros" + "%", page);
        return exercicioRepository.findAllByClientId(clienteId);
    }
}
