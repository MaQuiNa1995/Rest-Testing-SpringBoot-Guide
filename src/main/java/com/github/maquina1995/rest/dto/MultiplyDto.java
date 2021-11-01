package com.github.maquina1995.rest.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(value = AccessLevel.PRIVATE)
public class MultiplyDto {

	private Double num1;
	private Double num2;
}
