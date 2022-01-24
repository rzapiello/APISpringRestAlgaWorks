package com.algaworks.algafood.api.assembler;

import javax.validation.constraints.NotBlank;

import com.algaworks.algafood.api.model.input.UsuarioInput;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioComSenhaInput  extends UsuarioInput{
	
	@NotBlank
	private String senha;
	
	

}
