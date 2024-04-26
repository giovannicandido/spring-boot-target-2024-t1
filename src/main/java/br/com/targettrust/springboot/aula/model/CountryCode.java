package br.com.targettrust.springboot.aula.model;

import lombok.Value;

@Value
public class CountryCode {
    private String code;
    private String name;

    public static CountryCode of(String countryCode) {
        return new CountryCode(countryCode.toLowerCase(), "");
    }
}
