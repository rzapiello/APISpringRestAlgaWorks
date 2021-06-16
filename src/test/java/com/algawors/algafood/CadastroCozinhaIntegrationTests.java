package com.algawors.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

//@EnableJpaRepositories
@ComponentScan({"com.algaworks.algafood"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {CadastroCozinhaIntegrationTests.class})
public class CadastroCozinhaIntegrationTests {
	

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Test
	public void deveAtribuitId_QuandoCadastrarCozinhaComDadosIncorretos() {
		
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("teste");
		novaCozinha= cadastroCozinha.salvar(novaCozinha);
		
		assertThat(novaCozinha.getId()).isNotNull();
		
	
	}
	
	@Test
	public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
	   Cozinha novaCozinha = new Cozinha();
	   novaCozinha.setNome(null);
	   
	   ConstraintViolationException erroEsperado =
	      Assertions.assertThrows(ConstraintViolationException.class, () -> {
	         cadastroCozinha.salvar(novaCozinha);
	      });
	   
	   assertThat(erroEsperado).isNotNull();
	}

}
