package com.github.maquina1995.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.maquina1995.rest.service.CalculadoraService;

/**
 * Modo Completo, en este caso no se carga el contexto completo de spring y aqui
 * si que tendremos un servidor real
 * <p>
 * <img src=
 * "https://thepracticaldeveloper.com/images/posts/uploads/2017/07/tests_springboot_wm-1.png">
 * 
 * @see la imagen pertenece a
 *      https://thepracticaldeveloper.com/guide-spring-boot-controller-tests/#strategy-4-springboottest-example-with-a-real-web-server
 * 
 * @author MaQuiNa1995
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class Strategy3Test {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@MockBean
	private CalculadoraService calculadoraService;

	@Test
	void sumTest() {
		// given
		BDDMockito.given(calculadoraService.sum(10, 5))
		        .willReturn(15d);

		// when
		ResponseEntity<Double> response = testRestTemplate.getForEntity("/calculadora/sum?num1=10&num2=5",
		        Double.class);

		// then
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(15d, response.getBody());
	}

	@Test
	@Disabled
	void minusTest() {
	}

	@Test
	@Disabled
	void multiplyTest() {
	}

	@Test
	@Disabled
	void divideTest() {
	}

}
