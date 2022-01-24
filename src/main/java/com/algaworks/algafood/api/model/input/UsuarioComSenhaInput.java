package com.algaworks.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import com.algaworks.algafood.domain.model.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioComSenhaInput extends Usuario {
	
	@NotBlank
	private String senha;
	

}
