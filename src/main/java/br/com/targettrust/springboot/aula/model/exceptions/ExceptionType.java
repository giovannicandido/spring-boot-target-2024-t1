package br.com.targettrust.springboot.aula.model.exceptions;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ExceptionType {
    BUSINESS_ERROR(
            "BUSSINESS-0001",
            "Business"
    ),
    CLIENT_VALIDATION(
            "CLIENTVALIDATION-00001",
            "Client"
    ),
    SERVICE_ERROR(
            "SERVICE-ERROR-0002",
            "Service"
    );

    private final String code;
    private final String exceptionType;

}
