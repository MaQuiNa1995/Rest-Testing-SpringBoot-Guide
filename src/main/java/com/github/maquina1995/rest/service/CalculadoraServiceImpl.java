package com.github.maquina1995.rest.service;

import org.springframework.stereotype.Service;

@Service
public class CalculadoraServiceImpl implements CalculadoraService {

	@Override
	public double sum(double number1, double number2) {
		return number1 + number2;
	}

	@Override
	public double minus(double number1, double number2) {
		return number1 - number2;
	}

	@Override
	public double multiply(double number1, double number2) {
		return number1 * number2;
	}

	@Override
	public double divide(double dividend, double divisor) {
		return dividend / divisor;
	}

	@Override
	public double squareRoot(double number) {
		return Math.sqrt(number);
	}

	@Override
	public double absolute(double number) {
		return Math.abs(number);
	}

	@Override
	public double round(double number) {
		return Math.round(number);
	}

}
