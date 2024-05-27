package br.com.targettrust.springboot.aula.dto.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AgeValidationImpl implements ConstraintValidator<AgeValidation, LocalDate> {
    private int LEGAL_AGE = 18;
    @Override
    public void initialize(AgeValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.LEGAL_AGE = constraintAnnotation.legalAge();
    }

    // Context é usado para reflection
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        LocalDate now = LocalDate.now();
        long age = ChronoUnit.YEARS.between(value, now);
        return age >= LEGAL_AGE;
    }
}
