package com.algaworks.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

//@EnableJpaRepositories
@EnableAutoConfiguration
@SpringBootTest(/*classes = { CadastroCozinhaIT.class },*/ webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ComponentScan({ "com.algaworks.algafood" })
@ExtendWith(SpringExtension.class)
@TestPropertySource("application-test.properties")
public class CadastroCozinhaIT {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private Flyway flyway;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		flyway.migrate();

		
	}
	

	
	@Test
	public void deveRetornarStatus200_QUandoConsultaCozinhas() {
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
//	@Test
//	public void deveConter4Cozinhas_QUandoConsultaCozinhas() {

//		given()
//			.accept(ContentType.JSON)
//		.when()
//			.get()
//		.then()
//			.body("", hasSize(1))
//			//.body("nome",  hasItems("Indiana","Argentina"))
//			;
//	
//	}
	
	@Test
	public void testRetornarStatus201_QuabdiCadastrarCozinha() {
		given()
			.body("{\"nome\": \"chinesa\"}")
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()	
			.statusCode(HttpStatus.CREATED.value());
	}

}
