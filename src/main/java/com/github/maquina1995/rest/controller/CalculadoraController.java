package com.github.maquina1995.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.maquina1995.rest.service.CalculadoraService;

import lombok.RequiredArgsConstructor;

/**
 * Se han omitido los {@link org.springframework.http.ResponseEntity} para mayor
 * brevedad y legibidad
 * 
 * @author MaQuiNa1995
 *
 */
@RestController
@RequestMapping("/calculadora")
@RequiredArgsConstructor
public class CalculadoraController {

	private final CalculadoraService calculadoraService;

	@GetMapping("/sum")
	public double sum(double num1, double num2) {
		return calculadoraService.sum(num1, num2);
	}

	@GetMapping("/minus")
	public double minus(double num1, double num2) {
		return calculadoraService.minus(num1, num2);
	}

	@GetMapping("/multiply")
	public double multiply(double num1, double num2) {
		return calculadoraService.multiply(num1, num2);
	}

	@GetMapping("/divide")
	public Double divide(double dividend, double quotient) {
		return calculadoraService.divide(dividend, quotient);
	}

}
