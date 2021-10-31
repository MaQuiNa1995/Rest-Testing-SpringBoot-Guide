package com.github.maquina1995.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maquina1995.rest.controller.CalculadoraController;
import com.github.maquina1995.rest.service.CalculadoraService;

/**
 * Modo independiente, en este caso no se carga el contexto de spring por lo
 * tanto no tendremos un servidor sino que llamaremos directamente a las clases
 * usando con mockito
 * <p>
 * <img src=
 * "https://thepracticaldeveloper.com/images/posts/uploads/2017/07/tests_mockmvc_wm.png">
 * 
 * @see la imagen pertenece a
 *      https://thepracticaldeveloper.com/guide-spring-boot-controller-tests/#strategy-1-spring-mockmvc-example-in-standalone-mode
 * 
 * @author MaQuiNa1995
 */
@ExtendWith(MockitoExtension.class)
class Strategy1Test {

	private MockMvc mvc;
	private JacksonTester<Double> jacksonTester;

	@InjectMocks
	private CalculadoraController calculadoraController;
	@Mock
	private CalculadoraService calculadoraService;

	@BeforeEach
	public void setup() {
		// Si no usasemos MockitoExtension tendríamos que incluir la siguiente línea
		// MockitoAnnotations.initMocks(this);

		// Al no tener el contexto de spring no podemos usar @AutoConfigureJsonTesters y
		// tendremos que inicializar el objeto nosotros
		JacksonTester.initFields(this, new ObjectMapper());

		mvc = MockMvcBuilders.standaloneSetup(calculadoraController)
		        // .setControllerAdvice(new ControllerExceptionHandler())
		        .build();
	}

	@Test
	void sumTest() throws Exception {

		// given
		BDDMockito.given(calculadoraService.sum(10, 5))
		        .willReturn(15d);

		// when
		MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get("/calculadora/sum?num1=10&num2=5")
		        .accept(MediaType.APPLICATION_JSON))
		        .andReturn()
		        .getResponse();

		// then
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
		Assertions.assertEquals("15.0", response.getContentAsString());
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
