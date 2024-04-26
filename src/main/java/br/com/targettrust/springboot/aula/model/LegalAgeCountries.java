package br.com.targettrust.springboot.aula.model;

import java.util.Map;

public class LegalAgeCountries {
    public static Map<String, Integer> legalAges = Map.of(
            "pt_br",
            18,
            "us", 21
    );
}
