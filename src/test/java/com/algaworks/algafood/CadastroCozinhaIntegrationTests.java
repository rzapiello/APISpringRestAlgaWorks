package com.algaworks.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaService;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

//@EnableJpaRepositories
@ComponentScan({"com.algaworks.algafood"})
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { CadastroCozinhaIntegrationTests.class })
public class CadastroCozinhaIntegrationTests {

	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Test
	public void deveAtribuitId_QuandoCadastrarCozinhaComDadosIncorretos() {

		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("teste");
		novaCozinha = cadastroCozinha.salvar(novaCozinha);

		assertThat(novaCozinha.getId()).isNotNull();

	}

	@Test
	public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		ConstraintViolationException erroEsperado = Assertions.assertThrows(ConstraintViolationException.class, () -> {
			cadastroCozinha.salvar(novaCozinha);
		});

		assertThat(erroEsperado).isNotNull();
	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {

		EntidadeEmUsoException erroEsperado = Assertions.assertThrows(EntidadeEmUsoException.class, () -> {

			Cozinha cozinha = new Cozinha();
			cozinha.setNome("deveFalhar_QuandoExcluirCozinhaEmUso");
			System.out.println("1");
			cozinha = cadastroCozinha.salvar(cozinha);

			Restaurante restaurante = new Restaurante();
			restaurante.setNome("deveFalhar_QuandoExcluirCozinhaEmUso");
			restaurante.setCozinha(cozinha);
			restaurante.setTaxaFrete(BigDecimal.valueOf(1L));

			System.out.println("2");
			cadastroRestaurante.salvar(restaurante);

			System.out.println("3");
			cadastroCozinha.excluir(cozinha.getId());

		});

		System.out.println(erroEsperado.getMessage());
		assertThat(erroEsperado).isNotNull();
	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
		Cozinha cozinha = new Cozinha();

		cozinha.setId(-1L);

		CozinhaNaoEncontradaService erroEsperado = Assertions.assertThrows(CozinhaNaoEncontradaService.class, () -> {
			cadastroCozinha.excluir(-1L);

		});

		assertThat(erroEsperado).isNotNull();

	}

}
