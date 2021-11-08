package com.github.maquina1995.rest.unitary;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.maquina1995.rest.controller.CalculadoraController;
import com.github.maquina1995.rest.dto.DivisionDto;
import com.github.maquina1995.rest.dto.RoundDto;
import com.github.maquina1995.rest.service.CalculadoraService;

/**
 * Modo Completo, en este caso no se carga el contexto completo de spring y aqui
 * si que tendremos un servidor real
 * <p>
 * <img src=
 * "https://thepracticaldeveloper.com/images/posts/uploads/2017/07/tests_springboot_wm-1.png">
 * <p>
 * para el testeo de los endpoint puedes usar:
 * {@link TestRestTemplate#exchange(java.net.URI, HttpMethod, org.springframework.http.HttpEntity, Class)}
 * <p>
 * 
 * Pero es mas cómodo:
 * <table border="1">
 * <thead>
 * <tr>
 * <th>Verbo Http</th>
 * <th>Método TestRestTemplate<br>
 * </th>
 * </tr>
 * </thead> <tbody>
 * <tr>
 * <td>Get</td>
 * <td>{@link TestRestTemplate#getForEntity(String, Class, Object...)}</td>
 * </tr>
 * <tr>
 * <td>Post</td>
 * <td>{@link TestRestTemplate#postForEntity(java.net.URI, Object, Class)}</td>
 * </tr>
 * </tbody>
 * </table>
 * 
 * @see https://thepracticaldeveloper.com/guide-spring-boot-controller-tests/#strategy-4-springboottest-example-with-a-real-web-server
 * 
 * @author MaQuiNa1995
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class Strategy3Test {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@MockBean
	private CalculadoraService calculadoraService;

	/**
	 * Test de: {@link CalculadoraController#sum(double, double)}
	 */
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

	/**
	 * Test de: {@link CalculadoraController#sum(double, double)}
	 */
	@Test
	void sumExchangeTest() {
		// given
		BDDMockito.given(calculadoraService.sum(10, 5))
		        .willReturn(15d);

		// when
		ResponseEntity<Double> response = testRestTemplate.exchange("/calculadora/sum?num1=10&num2=5", HttpMethod.GET,
		        null, Double.class);

		// then
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(15d, response.getBody());
	}

	/**
	 * Test de: {@link CalculadoraController#minus(double, double)}
	 */
	@Test
	void minusTest() {
		// given
		BDDMockito.given(calculadoraService.minus(10, 5))
		        .willReturn(5d);

		// when
		ResponseEntity<Double> response = testRestTemplate.getForEntity("/calculadora/minus/10/5", Double.class);

		// then
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(5d, response.getBody());
	}

	/**
	 * Test de: {@link CalculadoraController#minus(double, double)}
	 */
	@Test
	void minusExchangeTest() {
		// given
		BDDMockito.given(calculadoraService.minus(10, 5))
		        .willReturn(5d);

		// when
		ResponseEntity<Double> response = testRestTemplate.exchange("/calculadora/minus/10/5", HttpMethod.GET, null,
		        Double.class);

		// then
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(5d, response.getBody());
	}

	/**
	 * Test de:
	 * {@link CalculadoraController#multiply(com.github.maquina1995.rest.dto.MultiplyDto)}
	 */
	@Test
	void multiplyTest() {
		// given
		BDDMockito.given(calculadoraService.multiply(10, 5))
		        .willReturn(50d);

		// when
		ResponseEntity<Double> response = testRestTemplate.getForEntity("/calculadora/multiply?num1=10&num2=5",
		        Double.class);

		// then
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(50d, response.getBody());
	}

	/**
	 * Test de:
	 * {@link CalculadoraController#multiply(com.github.maquina1995.rest.dto.MultiplyDto)}
	 */
	@Test
	void multiplyExchangeTest() {
		// given
		BDDMockito.given(calculadoraService.multiply(10, 5))
		        .willReturn(50d);

		// when
		ResponseEntity<Double> response = testRestTemplate.exchange("/calculadora/multiply?num1=10&num2=5",
		        HttpMethod.GET, null, Double.class);

		// then
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(50d, response.getBody());
	}

	/**
	 * Test de: {@link CalculadoraController#divide(DivisionDto)}
	 */
	@Test
	void divideTest() {
		// given
		BDDMockito.given(calculadoraService.divide(10, 5))
		        .willReturn(2d);

		DivisionDto dto = DivisionDto.builder()
		        .dividend(10d)
		        .divisor(5d)
		        .build();

		// when
		ResponseEntity<Double> response = testRestTemplate.postForEntity("/calculadora/divide", dto, Double.class);

		// then
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(2d, response.getBody());
	}

	/**
	 * Test de: {@link CalculadoraController#divide(DivisionDto)}
	 */
	@Test
	void divideExchangeTest() {
		// given
		BDDMockito.given(calculadoraService.divide(10, 5))
		        .willReturn(2d);

		DivisionDto dto = DivisionDto.builder()
		        .dividend(10d)
		        .divisor(5d)
		        .build();
		HttpEntity<DivisionDto> httpEntity = new HttpEntity<>(dto);

		// when
		ResponseEntity<Double> response = testRestTemplate.exchange("/calculadora/divide", HttpMethod.POST, httpEntity,
		        Double.class);

		// then
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(2d, response.getBody());
	}

	/**
	 * Test de: {@link CalculadoraController#divide(DivisionDto)}
	 * <p>
	 * Para los casos en los que entre la validación de campos necesitamos usar el
	 * exchange
	 * <p>
	 * Usamos {@link ParameterizedTypeReference} para recoger de una manera mas
	 * exacta un objeto si usáramos el método simple de
	 * {@link TestRestTemplate#postForEntity(String, Object, Class, Object...)}
	 * tendríamos que hacer casteos ya que a este solo le podríamos decir que
	 * devuelve un {@link Map}
	 * <p>
	 * En cambio con el
	 * {@link TestRestTemplate#exchange(String, HttpMethod, HttpEntity, ParameterizedTypeReference, Object...)}
	 * podemos establecer la respuesta a {@link Map} <String,String>
	 */
	@Test
	void divideBadRequestTest() {

		// given
		DivisionDto dto = DivisionDto.builder()
		        .dividend(0d)
		        .divisor(0d)
		        .build();
		HttpEntity<DivisionDto> httpEntity = new HttpEntity<>(dto);

		// when
		ParameterizedTypeReference<Map<String, String>> typeReference = new ParameterizedTypeReference<Map<String, String>>() {
		};

		ResponseEntity<Map<String, String>> response = testRestTemplate.exchange("/calculadora/divide", HttpMethod.POST,
		        httpEntity, typeReference);

		// then
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		Map<String, String> errorMap = response.getBody();
		Assertions.assertEquals(2, errorMap.size());
		Assertions.assertEquals("must be greater than or equal to 1", errorMap.get("divisor"));
		Assertions.assertEquals("must be greater than or equal to 1", errorMap.get("dividend"));
	}

	/**
	 * Test de: {@link CalculadoraController#squareRoot(Integer)}
	 */
	@Test
	void squareRootBadRequestTest() {

		// when
		ParameterizedTypeReference<Map<String, String>> typeReference = new ParameterizedTypeReference<Map<String, String>>() {
		};

		ResponseEntity<Map<String, String>> response = testRestTemplate.exchange("/calculadora/square-root?number=0",
		        HttpMethod.GET, null, typeReference);

		// then
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		Map<String, String> errorMap = response.getBody();
		Assertions.assertEquals(1, errorMap.size());
		Assertions.assertEquals("Need to be greater than 1", errorMap.get("number"));
	}

	/**
	 * Test de: {@link CalculadoraController#absolute(Integer)}
	 */
	@Test
	void absoluteBadRequestTest() {

		// when
		ParameterizedTypeReference<Map<String, String>> typeReference = new ParameterizedTypeReference<Map<String, String>>() {
		};

		ResponseEntity<Map<String, String>> response = testRestTemplate.exchange("/calculadora/absolute/0",
		        HttpMethod.GET, null, typeReference);

		// then
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		Map<String, String> errorMap = response.getBody();
		Assertions.assertEquals(1, errorMap.size());
		Assertions.assertEquals("Need to be greater than 1", errorMap.get("number"));
	}

	/**
	 * Test de: {@link CalculadoraController#round(RoundDto)}
	 */
	@Test
	void roundBadRequestTest() {

		// given
		RoundDto dto = RoundDto.builder()
		        .number(0d)
		        .build();
		HttpEntity<RoundDto> httpEntity = new HttpEntity<>(dto);

		// when
		ParameterizedTypeReference<Map<String, String>> typeReference = new ParameterizedTypeReference<Map<String, String>>() {
		};

		ResponseEntity<Map<String, String>> response = testRestTemplate.exchange("/calculadora/round", HttpMethod.POST,
		        httpEntity, typeReference);

		// then
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		Map<String, String> errorMap = response.getBody();
		Assertions.assertEquals(1, errorMap.size());
		Assertions.assertEquals("Para redondear se tiene que introducir un valor decimal no exacto",
		        errorMap.get("roundDto"));
	}

}
