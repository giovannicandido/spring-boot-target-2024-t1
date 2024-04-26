package br.com.targettrust.springboot.aula.model.exceptions;

public class IllegalCountryCodeException extends RuntimeException {
    public IllegalCountryCodeException(String countryCode) {
        super("Invalid country code: %s".formatted(countryCode));
    }
}
