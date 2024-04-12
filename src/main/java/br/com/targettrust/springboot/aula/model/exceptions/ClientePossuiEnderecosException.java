package br.com.targettrust.springboot.aula.model.exceptions;

import lombok.Getter;

@Getter
public class ClientePossuiEnderecosException extends RuntimeException {
    private final Long clientId;

    public ClientePossuiEnderecosException(Long clientId) {
        this.clientId = clientId;
    }
}
