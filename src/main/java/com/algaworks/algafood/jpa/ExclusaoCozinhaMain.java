package com.algaworks.algafood.jpa;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

public class ExclusaoCozinhaMain {

	public static void main(String[] args) {

		// ApplicationContext applicationContext = new
		// SpringApplicationBuilder(AlgafoodApiApplication.class).
		// web(WebApplicationType.NONE).
		// run(args);

		// CozinhaRepository cozinhas =
		// applicationContext.getBean(CozinhaRepository.class);

		CadastroCozinhaService cadastroCozinhaService = new CadastroCozinhaService();

		Cozinha cozinha = new Cozinha();
		cozinha.setId(1L);

		cadastroCozinhaService.excluir(1L);

	}
}
