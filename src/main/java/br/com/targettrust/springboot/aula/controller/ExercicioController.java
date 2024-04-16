package br.com.targettrust.springboot.aula.controller;

import br.com.targettrust.springboot.aula.dto.request.ExercicioRequest;
import br.com.targettrust.springboot.aula.dto.response.ExercicioResponse;
import br.com.targettrust.springboot.aula.service.ExercicioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/exercicios")
@RequiredArgsConstructor
public class ExercicioController {

    private final ExercicioService exercicioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExercicioResponse createExercicio(@RequestBody @Valid ExercicioRequest exercicioRequest,
                                             Pageable page) {
        return ExercicioResponse.fromModel(
               exercicioService.createExercicio(exercicioRequest.toModel())
        );
    }
}
