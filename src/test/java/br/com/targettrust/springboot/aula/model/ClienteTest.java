package br.com.targettrust.springboot.aula.model;

import br.com.targettrust.springboot.aula.model.exceptions.IllegalCountryCodeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class ClienteTest {

    private Cliente cliente = new Cliente();

    @ParameterizedTest
    @ValueSource(ints = {18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31})
    @DisplayName("Given the cliente is on legal age when is legal age is called should return true")
    public void given_ClienteIsOnLegalAge_when_isLegalAge_should_returnTrue(Integer age) {
        cliente.setDataNascimento(getDataNacimentoForAge(age));
        var result = cliente.isLegalAge(CountryCode.of("PT_BR"));
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @MethodSource(value = "ageBuilder")
    @DisplayName("Given the cliente is not on legal age when is legal age is called should return false")
    public void given_ClienteIsNotOnLegalAge_when_isLegalAge_should_returnFalse(Integer age, String countryCode) {
        cliente.setDataNascimento(getDataNacimentoForAge(age));
        var result = cliente.isLegalAge(CountryCode.of(countryCode));
        assertThat(result).isFalse();
    }

    @Test
    public void given_clienteHasDatanascimento_when_getIdadeCliente_should_returnClienteAge() {
        cliente.setDataNascimento(getDataNacimentoForAge(25));
        var result = cliente.getIdadeCliente();
        assertThat(result).isEqualTo(25);
    }


    @Test
    public void given_countryCodeDoesNotExist_when_isLegalAge_should_thrownIllegalCountryCodeException() {
        assertThatThrownBy(() -> {
            var cliente = new Cliente();
            cliente.setDataNascimento(getDataNacimentoForAge(17));
            cliente.isLegalAge(CountryCode.of("INVALID"));
        })
                .isInstanceOf(IllegalCountryCodeException.class)
                .hasMessage("Invalid country code: invalid");
    }

    @Test
    public void given_bairroIsInAddress_when_clienteLivesIn_should_returnTrue() {

    }

    @Test
    public void given_bairroIsInAddress_when_getBairros_should_returnBairros() {

        var enderecos = List.of(
                new Endereco(1L, "rua", 100, "Jardims", "br", "SP", cliente),
                new Endereco(2L, "rua", 100, "Iguatemi", "br", "SP", cliente),
                new Endereco(2L, "rua", 100, "Santo Antonio", "br", "SP", cliente)
        );

        cliente.setEnderecos(enderecos);

        var result = cliente.getBairrosCliente();

        assertThat(result)
                .map(String::toLowerCase)
                .containsExactlyInAnyOrder("jardims",  "santo antonio", "iguatemi");

    }


    public static Stream<Arguments> ageBuilder() {
        var argumentsBR =  IntStream.range(0, 18)
                .mapToObj(ints -> Arguments.of(ints, "PT_BR"));

        var argumentsUS =  IntStream.range(0, 21)
                .mapToObj(ints -> Arguments.of(ints, "US"));

        return Stream.concat(argumentsBR, argumentsUS);
    }

    private static LocalDate getDataNacimentoForAge(int age) {
        return LocalDate.now().minus(age, ChronoUnit.YEARS);
    }


    private class CaseInsensitiveComparator implements java.util.Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            if(((String) o1).equalsIgnoreCase((String) o2)) {
                return 0;
            } else {
                return ((String) o1).compareTo((String)o2);
            }
        }
    }
}