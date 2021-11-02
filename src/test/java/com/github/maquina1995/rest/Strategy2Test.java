package com.github.maquina1995.rest;

import org.junit.jupiter.api.Assertions;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.github.maquina1995.rest.controller.CalculadoraController;
import com.github.maquina1995.rest.dto.DivisionDto;
import com.github.maquina1995.rest.dto.RoundDto;
import com.github.maquina1995.rest.service.CalculadoraService;

/**
 * WebApplicationContext, en este caso se carga el contexto de spring pero
 * usamos un servidor embebido por lo tanto no habrá un servidor desplegado
 * <p>
 * <img src=
 * "https://thepracticaldeveloper.com/images/posts/uploads/2017/07/tests_mockmvc_with_context_wm.png">
 * 
 * @see https://thepracticaldeveloper.com/guide-spring-boot-controller-tests/#strategy-2-spring-mockmvc-example-with-webapplicationcontext
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
	private JacksonTester<DivisionDto> divisionDtoJacksonTester;
	@Autowired
	private JacksonTester<RoundDto> roundDtoJacksonTester;

	@MockBean
	private CalculadoraService calculadoraService;

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

	@Test
	void squareRootBadRequestTest() throws Exception {

		// when
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/calculadora/square-root")
		        .queryParam("number", "0");

		mvc.perform(builder)
		        .andDo(MockMvcResultHandlers.print())
		        .andExpect(MockMvcResultMatchers.status()
		                .isBadRequest())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.number")
		                .value("Need to be greater than 1"));
	}

	@Test
	void absoluteBadRequestTest() throws Exception {

		// when
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/calculadora/absolute/{number}", 0);

		mvc.perform(builder)
		        .andDo(MockMvcResultHandlers.print())
		        .andExpect(MockMvcResultMatchers.status()
		                .isBadRequest())
		        .andExpect(MockMvcResultMatchers.jsonPath("$.number")
		                .value("Need to be greater than 1"));
	}

	@Test
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
