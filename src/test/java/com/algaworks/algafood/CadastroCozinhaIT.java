package com.algaworks.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import org.flywaydb.core.Flyway;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

//@EnableJpaRepositories
@EnableAutoConfiguration
@SpringBootTest(/*classes = { CadastroCozinhaIT.class },*/ webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ComponentScan({ "com.algaworks.algafood" })
@ExtendWith(SpringExtension.class)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {
	
	@LocalServerPort
	private int port;
	
	//static Integer totalCozinhas;
	
	private static final int COZINHA_ID_INEXISTENTE = 100;

	private Cozinha cozinhaAmericana;
	private int quantidadeCozinhasCadastradas;
	private String jsonCorretoCozinhaChinesa;
	
	@Autowired
	EntityManager entityManager;
	//@Autowired
	//private Flyway flyway;
	
	@Autowired
	CozinhaRepository cozinhaRepository;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		databaseCleaner.clearTables();
		
		//flyway.migrate();
		prepararDados();
		//totalCozinhas = contarRegistros();
		
		jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
				"/json/correto/cozinha-chinesa.json");
		
		System.out.println(jsonCorretoCozinhaChinesa.toString());
 
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
	
	@Test
	public void deveConterTotalCorretoDeCozinhas_QuandoConsultaCozinhas() {

		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(quantidadeCozinhasCadastradas))
			.body("nome",  hasItems("Tailandesa","Americana"))
			;
	
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		given()
			.body(jsonCorretoCozinhaChinesa)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()	
			.statusCode(HttpStatus.CREATED.value());
	}
	
	
	private void prepararDados() {
		
		Cozinha cozinhaTailandesa =  new Cozinha();
		cozinhaTailandesa.setNome("Tailandesa");
		cozinhaRepository.save(cozinhaTailandesa);
		
		cozinhaAmericana =  new Cozinha();
		cozinhaAmericana.setNome("Americana");
		cozinhaRepository.save(cozinhaAmericana);
		
		  quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
	}
	
	
	@Test
	public void deveRetornarRespostaEStatusCorretor_QuandoConsultarCozinheExistente() {
		given()
		.pathParam("cozinhaId", cozinhaAmericana.getId())
		.accept(ContentType.JSON)
	.when()
		.get("/{cozinhaId}")
	.then()
		.statusCode(HttpStatus.OK.value())
		.body("nome", equalTo("Americana"))
		;
		
		
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinheExistente() {
		given()
		.pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
		.accept(ContentType.JSON)
	.when()
		.get("/{cozinhaId}")
	.then()
		.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinheInexistente() {
		given()
		.pathParam("cozinhaId", 100)
		.accept(ContentType.JSON)
	.when()
		.get("/{cozinhaId}")
	.then()
		.statusCode(HttpStatus.NOT_FOUND.value());
	}	

	//private Integer contarRegistros() {	
	//	String jpql = "SELECT  count(1)   FROM Cozinha o";
	//	javax.persistence.Query q = entityManager.createQuery(jpql);

	// return Integer.parseInt(q.getSingleResult().toString());
		
		
	//}

}
