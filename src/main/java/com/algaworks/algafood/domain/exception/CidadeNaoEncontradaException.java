package com.algaworks.algafood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {
	
	private static final long serialVersionUID = 1L;
	
	public CidadeNaoEncontradaException(String mensagem) {
		
		super(mensagem);
		
	}
	
public CidadeNaoEncontradaException(Long Cidadeid) {
		
		this(String.format("Cidade de código %d não encontrada", Cidadeid));
		
	}

}
