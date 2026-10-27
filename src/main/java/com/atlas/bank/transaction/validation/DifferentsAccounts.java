package com.atlas.bank.transaction.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy =   DefferentAccountValidation.class)
public @interface DifferentsAccounts {

    String message() default "La cuenta de destino no puede ser la misma";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
