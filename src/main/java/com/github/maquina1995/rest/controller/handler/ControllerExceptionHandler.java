package com.github.maquina1995.rest.controller.handler;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Con este advice controlaremos las excepciones provocadas por la validación de
 * campos al entrar la petición al endpoint y teniendo el {@link Valid}
 * 
 * @author MaQuiNa1995
 *
 */
@RestControllerAdvice(basePackages = "com.github.maquina1995.rest.controller")
public class ControllerExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult()
		        .getAllErrors()
		        .forEach(error -> {
			        String fieldName = error instanceof FieldError ? ((FieldError) error).getField()
			                : error.getObjectName();
			        String errorMessage = error.getDefaultMessage();
			        errors.put(fieldName, errorMessage);
		        });
		return errors;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public Map<String, String> handleConstraintException(ConstraintViolationException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getConstraintViolations()
		        .forEach(e -> {
			        String string = e.getPropertyPath()
			                .toString();
			        int pointIndex = string.indexOf(".");
			        errors.put(string.substring(pointIndex + 1), e.getMessage());
		        });
		return errors;
	}
}
