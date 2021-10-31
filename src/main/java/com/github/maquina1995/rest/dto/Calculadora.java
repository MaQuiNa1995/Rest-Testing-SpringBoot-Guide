package com.github.maquina1995.rest.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(value = AccessLevel.PRIVATE)
public class Calculadora {

	private Integer numero1;
	private Integer numero2;
}
