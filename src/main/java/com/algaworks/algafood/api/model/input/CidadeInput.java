package com.algaworks.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInput {

	@NotNull
	private String nome;
	
	@Valid
	@NotNull
	private EstadoInput estado;
	
}
