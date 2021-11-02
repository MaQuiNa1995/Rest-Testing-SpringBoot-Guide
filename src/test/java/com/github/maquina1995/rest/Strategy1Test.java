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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.maquina1995.rest.configuration.BindingControllerAdvice;
import com.github.maquina1995.rest.configuration.ValidationConfiguration;
import com.github.maquina1995.rest.controller.CalculadoraController;
import com.github.maquina1995.rest.controller.handler.ControllerExceptionHandler;
import com.github.maquina1995.rest.dto.DivisionDto;
import com.github.maquina1995.rest.dto.RoundDto;
import com.github.maquina1995.rest.service.CalculadoraService;

/**
 * Modo independiente, en este caso no se carga el contexto de spring por lo
 * tanto no tendremos un servidor sino que llamaremos directamente a las clases
 * usando con mockito
 * <p>
 * <img src=
 * "https://thepracticaldeveloper.com/images/posts/uploads/2017/07/tests_mockmvc_wm.png">
 * 
 * @see https://thepracticaldeveloper.com/guide-spring-boot-controller-tests/#strategy-1-spring-mockmvc-example-in-standalone-mode
 * 
 * @author MaQuiNa1995
 */
@ExtendWith(MockitoExtension.class)
class Strategy1Test {

	private MockMvc mvc;
	private JacksonTester<DivisionDto> divisionDtoJacksonTester;
	private JacksonTester<RoundDto> roundDtoJacksonTester;

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
		        .setControllerAdvice(new BindingControllerAdvice(), new ControllerExceptionHandler())
		        .build();
	}

	@Test
	void sumTest() throws Exception {

		// given
		BDDMockito.given(calculadoraService.sum(10, 5))
		        .willReturn(15d);

		// when
		MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get("/calculadora/sum")
		        .queryParam("num1", "10")
		        .queryParam("num2", "5"))
		        .andReturn()
		        .getResponse();

		// then
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
		Assertions.assertEquals("15.0", response.getContentAsString());
	}

	@Test
	void minusTest() throws Exception {

		// given
		BDDMockito.given(calculadoraService.minus(10d, 5d))
		        .willReturn(5d);

		// when
		MockHttpServletResponse response = mvc
		        .perform(MockMvcRequestBuilders.get("/calculadora/minus/{num1}/{num2}", 10d, 5d))
		        .andDo(MockMvcResultHandlers.print())
		        .andReturn()
		        .getResponse();

		// then
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
		Assertions.assertEquals("5.0", response.getContentAsString());
	}

	@Test
	void multiplyTest() throws Exception {

		// given
		BDDMockito.given(calculadoraService.multiply(5, 5))
		        .willReturn(25d);

		// when
		MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get("/calculadora/multiply")
		        .queryParam("num1", "5")
		        .queryParam("num2", "5"))
		        .andDo(MockMvcResultHandlers.print())
		        .andReturn()
		        .getResponse();

		// then
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
		Assertions.assertEquals("25.0", response.getContentAsString());

	}

	@Test
	void divideTest() throws Exception {

		// given
		BDDMockito.given(calculadoraService.divide(10, 5))
		        .willReturn(2d);

		DivisionDto dto = DivisionDto.builder()
		        .dividend(10d)
		        .divisor(5d)
		        .build();

		// when
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/calculadora/divide")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(divisionDtoJacksonTester.write(dto)
		                .getJson());

		MockHttpServletResponse response = mvc.perform(builder)
		        .andDo(MockMvcResultHandlers.print())
		        .andReturn()
		        .getResponse();

		// then
		Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
		Assertions.assertEquals("2.0", response.getContentAsString());

	}

	@Test
	void divideBadRequestTest() throws Exception {

		// given
		DivisionDto dto = DivisionDto.builder()
		        .dividend(0d)
		        .divisor(0d)
		        .build();

		// when
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/calculadora/divide")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(divisionDtoJacksonTester.write(dto)
		                .getJson());

		mvc.perform(builder)
		        .andDo(MockMvcResultHandlers.print())
		        // status
		        .andExpect(MockMvcResultMatchers.status()
		                .isBadRequest())
		        .andExpect(MockMvcResultMatchers.content()
		                .contentType(MediaType.APPLICATION_JSON))
		        // body
		        .andExpect(MockMvcResultMatchers.jsonPath("$.divisor")
		                .value("must be greater than or equal to 1"))
		        .andExpect(MockMvcResultMatchers.jsonPath("$.dividend")
		                .value("must be greater than or equal to 1"));

	}

	/**
	 * la razón por la que este test no se puede hacer es porque es el contexto de
	 * spring a traves del bean
	 * {@link ValidationConfiguration#methodValidationPostProcessor()} es el
	 * encargado de la validación
	 * 
	 * @throws Exception
	 */
	@Test
	@Disabled("No se puede hacer este test debido a que no se carga el contexto de spring en la estrategia 1")
	void squareRootRequestTest() throws Exception {

		// when
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/calculadora/square-root")
		        .queryParam("number", "0");

		mvc.perform(builder)
		        .andDo(MockMvcResultHandlers.print())
		        // El status aunque estemos pasando un argumento inválido será un 200
		        .andExpect(MockMvcResultMatchers.status()
		                .isBadRequest())
		        .andReturn();
	}

	/**
	 * la razón por la que este test no se puede hacer es porque es el contexto de
	 * spring a traves del bean
	 * {@link ValidationConfiguration#methodValidationPostProcessor()} es el
	 * encargado de la validación
	 * 
	 * @throws Exception
	 */
	@Test
	@Disabled("No se puede hacer este test debido a que no se carga el contexto de spring en la estrategia 1")
	void absoluteBadRequestTest() throws Exception {

		// when
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/calculadora/absolute/{number}", 0);

		mvc.perform(builder)
		        .andDo(MockMvcResultHandlers.print())
		        .andExpect(MockMvcResultMatchers.status()
		                .isBadRequest())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.number")
		                .value("must be greater than or equal to 1"));
	}

	/**
	 * la razón por la que este test no se puede hacer es porque es el contexto de
	 * spring el que se encarga de la validación
	 * 
	 * @throws Exception
	 */
	@Test
	@Disabled("No se puede hacer este test debido a que no se carga el contexto de spring en la estrategia 1")
	void roundBadRequestTest() throws Exception {

		// given
		RoundDto dto = RoundDto.builder()
		        .number(1.0d)
		        .build();

		// when
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/calculadora/round")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(roundDtoJacksonTester.write(dto)
		                .getJson());

		mvc.perform(builder)
		        .andDo(MockMvcResultHandlers.print())

		        // then
		        .andExpect(MockMvcResultMatchers.status()
		                .isBadRequest())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.roundDto")
		                .value("Para redondear se tiene que introducir un valor decimal no exacto"));
	}

}
