package br.com.targettrust.springboot.aula.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;


class ClienteTest {

    @Test
    @DisplayName("Given the cliente is on legal age when is legal age is called should return true")
    public void given_ClienteIsOnLegalAge_when_isLegalAge_should_returnTrue() {
        var cliente = new Cliente();
        cliente.setDataNascimento(LocalDate.now().minus(19, ChronoUnit.YEARS));
        var result = cliente.isLegalAge(CountryCode.of("PT_BR"));
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Given the cliente is not on legal age when is legal age is called should return false")
    public void given_ClienteIsNotOnLegalAge_when_isLegalAge_should_returnFalse() {
        var cliente = new Cliente();
        cliente.setDataNascimento(LocalDate.now().minus(17, ChronoUnit.YEARS));
        var result = cliente.isLegalAge(CountryCode.of("PT_BR"));
        assertThat(result).isFalse();
    }

    @Test
    public void given_clienteHasDatanascimento_when_getIdadeCliente_should_returnClienteAge() {
        var cliente = new Cliente();
        cliente.setDataNascimento(LocalDate.now().minus(25, ChronoUnit.YEARS));
        var result = cliente.getIdadeCliente();
        assertThat(result).isEqualTo(25);
    }

}