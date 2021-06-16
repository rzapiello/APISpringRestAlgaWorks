package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.Permissao;

public interface PermissaoRepository {
	
	Permissao buscar(Long id);
	List<Permissao> listar();
	void remover(Permissao permissao);
	Permissao salvar(Permissao permissao);

}
