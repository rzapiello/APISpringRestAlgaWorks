package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;


@Repository
public class CadastroEstadoService {
	
	@Autowired
	private EstadoRepository  estadoRepository;
	
	public Estado salvar(Estado estado) {
		
		return estadoRepository.save(estado);
	}
	
	public void  excluir(Long idEstado) {
		
		try {
		estadoRepository.deleteById(idEstado);
		} catch(EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe o estado com o id: %d", idEstado)
					);
		}catch(DataIntegrityViolationException e ) {
			throw new EntidadeEmUsoException(
					String.format("Estado de código %d não pode ser removida, pois esta em uso ", idEstado)
					);
			
		}
		
	}
	
	
	
	

}
