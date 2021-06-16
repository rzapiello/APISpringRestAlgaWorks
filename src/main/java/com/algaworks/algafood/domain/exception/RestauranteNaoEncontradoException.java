package com.algaworks.algafood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RestauranteNaoEncontradoException(Long restauranteId) {
		this(String.format("Restaurante de id: %d não existe", restauranteId));
	}

	public RestauranteNaoEncontradoException(String Mensagem) {
		super(Mensagem);
	}

}
