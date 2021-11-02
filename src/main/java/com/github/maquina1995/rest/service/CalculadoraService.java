package com.github.maquina1995.rest.service;

public interface CalculadoraService {

	public double sum(double number1, double number2);

	public double minus(double number1, double number2);

	public double multiply(double number1, double number2);

	public double divide(double dividend, double divisor);

	double round(double number);

	double absolute(double number);

	double squareRoot(double number);

}