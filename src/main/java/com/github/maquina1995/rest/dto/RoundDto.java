package com.github.maquina1995.rest.dto;

import com.github.maquina1995.rest.controller.validator.CustomValidDecimal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Aqui aunque es un poco sobrepasarse el hacer un objeto solo para envolver 1
 * campo se ha hecho con fines didácticos porque no se me ocurría ningún ejemplo
 * <p>
 * validaremos el Dto con {@link CustomValidDecimal}
 * 
 * @author MaQuiNa1995
 */
@CustomValidDecimal
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter(value = AccessLevel.PRIVATE)
public class RoundDto {
	private Double number;
}
