package com.github.maquina1995.rest.service;

import org.springframework.stereotype.Service;

@Service
public class CalculadoraServiceImpl implements CalculadoraService {

	@Override
	public double sum(double num1, double num2) {
		return num1 + num2;
	}

	@Override
	public double minus(double num1, double num2) {
		return num1 - num2;
	}

	@Override
	public double multiply(double num1, double num2) {
		return num1 * num2;
	}

	@Override
	public double divide(double dividend, double quotient) {
		return dividend / quotient;
	}

}
