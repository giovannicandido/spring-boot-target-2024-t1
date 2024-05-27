package br.com.targettrust.springboot.aula.dto.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = AgeValidationImpl.class)
public @interface AgeValidation {

    String message() default "{targettrust.legalAge.validation.message}";

    Class<?>[] groups() default { };

    int legalAge() default 18;

    Class<? extends Payload>[] payload() default { };
}
