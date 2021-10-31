package com.github.maquina1995.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.github.maquina1995.rest.controller.CalculadoraController;
import com.github.maquina1995.rest.service.CalculadoraService;

/**
 * WebApplicationContext, en este caso se carga el contexto de spring pero
 * usamos un servidor embebido por lo tanto no habrá un servidor desplegado
 * <p>
 * <img src=
 * "https://thepracticaldeveloper.com/images/posts/uploads/2017/07/tests_mockmvc_with_context_wm.png">
 * 
 * @see la imagen pertenece a
 *      https://thepracticaldeveloper.com/guide-spring-boot-controller-tests/#strategy-2-spring-mockmvc-example-with-webapplicationcontext
 * 
 * @author MaQuiNa1995
 */
@AutoConfigureJsonTesters
@WebMvcTest(CalculadoraController.class)
class Strategy2Test {

	/**
	 * Al tener contexto podemos usa autowired de {@link MockMvc} y
	 * {@link JacksonTester}
	 */
	@Autowired
	private MockMvc mvc;

	@Autowired
	private JacksonTester<Double> jacksonTester;

	@MockBean
	private CalculadoraService calculadoraService;

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
