package com.algaworks.algafood.domain.exception;

public class CozinhaNaoEncontradaService extends EntidadeNaoEncontradaException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CozinhaNaoEncontradaService(Long cozinhaId) {
		super(String.format("Não existe um cadastro de cozinha com o código: %d", cozinhaId));
	}
	
	public CozinhaNaoEncontradaService(String mensagem) {
		super(mensagem);
	}
	

}
