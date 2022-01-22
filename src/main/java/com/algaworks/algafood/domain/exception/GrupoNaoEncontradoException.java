package com.algaworks.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public GrupoNaoEncontradoException(Long GrupoId) {
		
		this(String.format("Grupo de código %d bão encontrado", GrupoId));
		
	}
	
	
	public GrupoNaoEncontradoException(String mensagem) {
		super(mensagem);
		// TODO Auto-generated constructor stub
	}



}
