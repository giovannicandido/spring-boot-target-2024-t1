package br.com.targettrust.springboot.aula.model.exceptions;

import lombok.Value;

@Value
public class RegistryNotFoundException extends DomainException {
    private final Long id;

    public RegistryNotFoundException(Long id) {
        super("Registro %s não encontrado", ExceptionType.CLIENT_VALIDATION);
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format(super.getMessage(), id);
    }
}
