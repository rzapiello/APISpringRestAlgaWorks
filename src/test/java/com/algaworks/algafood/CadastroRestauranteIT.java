package com.algaworks.algafood;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasSize;

import java.math.BigDecimal;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import groovyjarjarantlr4.v4.runtime.misc.ParseCancellationException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {
	
	
	private static final int RESTAURANTE_ID_INEXISTENTE = 100;
	private int quantidadeRestaurantesCadastrados;
	private String jsonRestauranteMcDonalds;
	
	
	@LocalServerPort
	private int port;
	
	@Autowired
	RestauranteRepository restauranteRepository;
	
	@Autowired
	CozinhaRepository cozinhaRepository;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/restaurantes";
		
		databaseCleaner.clearTables();
		prepararDados();
		
		quantidadeRestaurantesCadastrados = (int) restauranteRepository.count();
		
		jsonRestauranteMcDonalds = ResourceUtils.getContentFromResource("/json/correto/restaurante-McDonalds.json");
		
				}
	
	
	
	@Test
	public void deveRetornarStatus200_QuandoConsultaRestaurante() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
		
	}
	
	
	@Test
	public void deveRetornar201_QuandoCadastrarRestaurante() {
		
		given()
			.contentType(ContentType.JSON)
			.body(jsonRestauranteMcDonalds)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveConterQuantidadeCorretaDeRestaurantes_QuandoConsultaRestaurantes() {
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("",hasSize(quantidadeRestaurantesCadastrados));
	}
	
	@Test
	public void deveRetornar404_QuandoConsultaRestauranteInexistente() {
		given()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.pathParams("restauranteInexistente",RESTAURANTE_ID_INEXISTENTE)
		.when()
			.get("/{restauranteInexistente}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	
	private void prepararDados() {
		
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("cozinhaTest1");
		cozinhaRepository.save(cozinha);
		
		Restaurante restaurante1 = new Restaurante();
		restaurante1.setNome("NomeTeste1");
		restaurante1.setTaxaFrete(BigDecimal.valueOf(1));
		restaurante1.setCozinha(cozinha);
		
		restauranteRepository.save(restaurante1);
	}
	
	


	
}
