package com.algaworks.algafood.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	
	public EstadoNaoEncontradoException(Long estadoId) {
		this(String.format("Não existe o estado com o id: %d", estadoId));
		}
			
	public EstadoNaoEncontradoException(String mensagem) {
		super(mensagem);
		}		
}
