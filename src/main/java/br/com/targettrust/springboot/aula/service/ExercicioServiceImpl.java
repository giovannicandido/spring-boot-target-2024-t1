package br.com.targettrust.springboot.aula.service;

import br.com.targettrust.springboot.aula.model.Exercicio;
import br.com.targettrust.springboot.aula.model.exceptions.ExercicioIdsNotFoundException;
import br.com.targettrust.springboot.aula.model.exceptions.RegistryNotFoundException;
import br.com.targettrust.springboot.aula.repository.ClienteRepository;
import br.com.targettrust.springboot.aula.repository.ExercicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExercicioServiceImpl implements ExercicioService {

    private final ExercicioRepository exercicioRepository;
    private final ClienteRepository clienteRepository;
    private final PlatformTransactionManager transactionManager;
    private final TransactionTemplate transactionTemplate;

//    @PostConstruct
//    private void init() {
//        transactionTemplate = new TransactionTemplate(transactionManager);
//    }

    @Override
    @Transactional
    public Exercicio createExercicio(Exercicio exercicio) {
        return exercicioRepository.save(exercicio);
//        exercicio.setId(99L);
//        return exercicio;
    }

    @Override
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

    @Override
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
