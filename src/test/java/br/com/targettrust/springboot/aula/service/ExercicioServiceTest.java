package br.com.targettrust.springboot.aula.service;

import br.com.targettrust.springboot.aula.model.Exercicio;
import br.com.targettrust.springboot.aula.repository.ClienteRepository;
import br.com.targettrust.springboot.aula.repository.ExercicioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExercicioServiceTest {

    @Mock
    private ExercicioRepository exercicioRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private PlatformTransactionManager transactionManager;
    @Mock
    private TransactionTemplate transactionTemplate;

    @InjectMocks
    private ExercicioService exercicioService;

    // Forma manual
   /* @BeforeEach
    void setup() {
//        exercicioRepository = mock(ExercicioRepository.class);
//        clienteRepository = mock(ClienteRepository.class);
//        transactionManager = mock(PlatformTransactionManager.class);
//        transactionTemplate = mock(TransactionTemplate.class);
        // Usar injeção por construtor é recomendado por isso aqui:
        exercicioService = new ExercicioService(exercicioRepository, clienteRepository, transactionManager, transactionTemplate);
    }
    */


    @Test
    void given_ExercicioDataIsCorrect_when_createExercicio_should_salveWithTransaction() {
        //given
        var exercicio = new Exercicio();
        exercicio.setNome("Deve salvar");
        when(exercicioRepository.save(exercicio))
                .thenAnswer((Answer<Exercicio>) invocation -> {
                    var exercicioParametro = invocation.getArgument(0, Exercicio.class);
                    exercicioParametro.setId(99L);
                    return exercicioParametro;
                });

        TransactionStatus status = mock(TransactionStatus.class);

        when(transactionTemplate.execute(any()))
                .thenAnswer(invocation -> {
                    TransactionStatus transactionStatus = (TransactionStatus) invocation.getArgument(0, TransactionCallback.class)
                            .doInTransaction(status);
                    return transactionStatus;
                });

//        when(transactionManager.getTransaction(any()))
        // when
        exercicioService.createExercicio(exercicio);

        // should
        verify(transactionTemplate).execute(any());
        verify(exercicioRepository, times(1)).save(exercicio);
        verify(transactionManager).commit(status);

    }

    @Test
    void given_ExercicioDataIsNomeUpdate_when_createExercicio_should_rollbackTransaction() {
        //given
        var exercicio = new Exercicio();
        exercicio.setNome("Nome Updated");
        when(exercicioRepository.save(exercicio))
                .thenAnswer((Answer<Exercicio>) invocation -> {
                    var exercicioParametro = invocation.getArgument(0, Exercicio.class);
                    exercicioParametro.setId(99L);
                    return exercicioParametro;
                });

        TransactionStatus status = mock(TransactionStatus.class);

        when(status.isReadOnly())
                .thenReturn(true);

        doReturn(true)
                .when(status)
                .isReadOnly();

        when(transactionTemplate.execute(any()))
                .thenAnswer(invocation -> {
                    TransactionStatus transactionStatus = (TransactionStatus) invocation.getArgument(0, TransactionCallback.class)
                            .doInTransaction(status);
                    return transactionStatus;
                });


//        when(transactionManager.getTransaction(any()))
        // when
        exercicioService.createExercicio(exercicio);

        // should
        verify(transactionTemplate).execute(any());
        verify(exercicioRepository, times(1)).save(exercicio);
        verify(transactionManager).rollback(status);
        verifyNoMoreInteractions(transactionManager);

    }

}