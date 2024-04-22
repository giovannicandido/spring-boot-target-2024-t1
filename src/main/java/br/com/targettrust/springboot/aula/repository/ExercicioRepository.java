package br.com.targettrust.springboot.aula.repository;

import br.com.targettrust.springboot.aula.model.Exercicio;
import br.com.targettrust.springboot.aula.repository.projections.ClienteResumedProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {

    @Query("select e from Cliente c join c.exercicios e where c.id = :clientId")
    List<Exercicio> findAllByClientId(@Param("clientId") Long clientId);
    // where like nome = %nome%
    List<Exercicio> findAllByNomeContainingIgnoreCase(String nome);

    @Query("""
            select c.id as id, c.nome as nome, c.cpf as cpf from Cliente c join c.exercicios e where e.nome like :nome
            """
    )
    List<ClienteResumedProjection> findAllClienteMakingExerciseName(String nome, Sort sort);

    void deleteByNomeAndParteCorpo(String nome, String parteCorpo);
// confirmar como fazer update pelo nome
//    @Query("update Exercicio e set e.parteCorpo = :parteCorpo ")
//    @Modifying
//    void updateByNome(String nome, @Param("parteCorpo") String parteCorpo);

    Page<Exercicio> findExercicioByNome(String nome, Pageable unpaged);

//    @Query("update Exercicio e set e.nome = 'New nome'")
//    @Modifying
//    void updateExercicioCustom();
}
