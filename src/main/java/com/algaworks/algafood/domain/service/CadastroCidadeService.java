package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {
	
	@Autowired
	private CidadeRepository  cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	
	
	public Cidade salvar(Cidade cidade) {
		Long estadoID = cidade.getEstado().getId();
		Optional<Estado> estado = estadoRepository.findById(estadoID);
		
		if (estado.isEmpty()) {	
			throw new EntidadeNaoEncontradaException(String.format("Estado id %d não encontrado", estadoID));
		}
		
		Estado estadoAtual  = estado.get();
		cidade.setEstado(estadoAtual);
		return  cidadeRepository.save(cidade);
		
	}
	
	
	
	public void excluir (Long Cidadeid) {

		try {
		cidadeRepository.deleteById(Cidadeid);
		}catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Cidade de código %d não encontrada", Cidadeid));
		}catch(DataIntegrityViolationException e ) {
			throw new EntidadeEmUsoException(String.format("Cidade de código: %d não pode ser excluida, esta em uso", Cidadeid));
		}
		
	}

}
