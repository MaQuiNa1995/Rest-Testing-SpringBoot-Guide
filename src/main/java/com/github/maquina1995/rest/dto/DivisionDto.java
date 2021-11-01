package com.github.maquina1995.rest.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Es superfluo el mensaje que hemos puesto en las líneas 28 y 31 pero se ha
 * dado el caso que en local aparece el mensaje en español en los test de la
 * estrategia 3 y en inglés en los de la estrategia 1 y 2
 * <p>
 * Como medida temporal hasta que sepa el porque pasa eso, he decidido setearlo
 * en inglés el mensaje para que pase la pipeline
 * 
 * @author MaQuiNa1995
 *
 */
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter(value = AccessLevel.PRIVATE)
public class DivisionDto {

	@NotNull
	@Min(value = 1,
	        message = "must be greater than or equal to 1")
	private Double dividend;
	@Min(value = 1,
	        message = "must be greater than or equal to 1")
	@NotNull
	private Double divisor;
}
