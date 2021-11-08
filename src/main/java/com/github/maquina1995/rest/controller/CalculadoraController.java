package com.github.maquina1995.rest.controller;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.maquina1995.rest.dto.DivisionDto;
import com.github.maquina1995.rest.dto.MultiplyDto;
import com.github.maquina1995.rest.dto.RoundDto;
import com.github.maquina1995.rest.service.CalculadoraService;

import lombok.RequiredArgsConstructor;

/**
 * Se han omitido los {@link org.springframework.http.ResponseEntity} para mayor
 * brevedad y legibidad
 * 
 * 
 * 
 * @author MaQuiNa1995
 */
@Validated
@RestController
@RequestMapping("/calculadora")
@RequiredArgsConstructor
public class CalculadoraController {

	private final CalculadoraService calculadoraService;

	/**
	 * Endpoint con: {@link RequestParam}
	 */
	@GetMapping("/sum")
	public double sum(@RequestParam double num1, @RequestParam double num2) {
		return calculadoraService.sum(num1, num2);
	}

	/**
	 * Endpoint con: {@link PathVariable}
	 */
	@GetMapping("/minus/{num1}/{num2}")
	public double minus(@PathVariable double num1, @PathVariable double num2) {
		return calculadoraService.minus(num1, num2);
	}

	/**
	 * Endpoint con: dto {@link MultiplyDto}
	 */
	@GetMapping("/multiply")
	public double multiply(MultiplyDto calculoDto) {
		return calculadoraService.multiply(calculoDto.getNum1(), calculoDto.getNum2());
	}

	/**
	 * Endpoint con: dto {@link DivisionDto} y con {@link ResponseBody}
	 */
	@PostMapping("/divide")
	public double divide(@Valid @RequestBody DivisionDto calculoDto) {
		return calculadoraService.divide(calculoDto.getDividend(), calculoDto.getDivisor());
	}

	/**
	 * Endpoint con: {@link RequestParam} y validación
	 * <p>
	 * Para que en local no esté en español y en remoto en la pipe de github en
	 * inglés se decide setear en inglés el mensaje
	 */
	@GetMapping("/square-root")
	public double squareRoot(@RequestParam @Min(value = 1,
	        message = "Need to be greater than 1") Integer number) {
		return calculadoraService.squareRoot(number);
	}

	/**
	 * Endpoint con: {@link PathVariable} y validación
	 * <p>
	 * Para que en local no esté en español y en remoto en la pipe de github en
	 * inglés se decide setear en inglés el mensaje
	 */
	@GetMapping("/absolute/{number}")
	public double absolute(@PathVariable @Min(value = 1,
	        message = "Need to be greater than 1") Integer number) {
		return calculadoraService.absolute(number);
	}

	/**
	 * Endpoint con: dto {@link RoundDto}, con {@link @RequestBody} y validación
	 */
	@PostMapping("/round")
	public double round(@Valid @RequestBody RoundDto dto) {
		return calculadoraService.round(dto.getNumber());
	}
}