package com.github.maquina1995.rest.controller.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Anotación para poner en un Objeto para validar según la lógica de
 * {@link CustomValidDecimalValidator#isValid(com.github.maquina1995.rest.dto.RoundDto, javax.validation.ConstraintValidatorContext)}
 * <p>
 * Depende que {@link ElementType} Elijamos en el {@link Target#value()}
 * tendremos limitado su uso a diferentes localizaciones
 * 
 * @author MaQuiNa1995
 *
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomValidDecimalValidator.class)
public @interface CustomValidDecimal {

	String message() default "Para redondear se tiene que introducir un valor decimal no exacto";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
