package com.github.maquina1995.rest.controller.validator;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.github.maquina1995.rest.dto.RoundDto;

class CustomValidDecimalValidator implements ConstraintValidator<CustomValidDecimal, RoundDto> {

	/**
	 * Aqui básicamente estamos verificando si un número decimal es exacto o no
	 * <p>
	 * Es decir si por ejemplo es 3.0 (Exacto) o 3.1 (No exacto) solo deberíamos
	 * poder dejar redondear número decimales que no sean exactos
	 */
	@Override
	public boolean isValid(RoundDto value, ConstraintValidatorContext context) {

		DecimalFormat df = new DecimalFormat("#");
		df.setRoundingMode(RoundingMode.FLOOR);

		Double number = value.getNumber();
		double result = Double.parseDouble(df.format(number));

		return result != number;
	}
}
