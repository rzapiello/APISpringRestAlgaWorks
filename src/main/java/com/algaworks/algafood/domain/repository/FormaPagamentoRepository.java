package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository {

	FormaPagamento buscar(Long id);

	List<FormaPagamento> listar();

	void remover(FormaPagamento formaPagamento);

	FormaPagamento salvar(FormaPagamento formaPagamento);

}
