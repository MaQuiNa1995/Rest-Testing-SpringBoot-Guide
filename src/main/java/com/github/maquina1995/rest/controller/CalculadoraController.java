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
import org.springframework.web.bind.annotation.RestController;

import com.github.maquina1995.rest.dto.DivisionDto;
import com.github.maquina1995.rest.dto.MultiplyDto;
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

	@GetMapping("/sum")
	public double sum(@RequestParam @Min(1) double num1, @RequestParam double num2) {
		return calculadoraService.sum(num1, num2);
	}

	@GetMapping("/minus/{num1}/{num2}")
	public double minus(@PathVariable @Min(1) Double num1, @PathVariable Double num2) {
		return calculadoraService.minus(num1, num2);
	}

	@GetMapping("/multiply")
	public double multiply(MultiplyDto calculoDto) {
		return calculadoraService.multiply(calculoDto.getNum1(), calculoDto.getNum2());
	}

	@PostMapping("/divide")
	public double divide(@Valid @RequestBody DivisionDto calculoDto) {
		return calculadoraService.divide(calculoDto.getDividend(), calculoDto.getDivisor());
	}
}