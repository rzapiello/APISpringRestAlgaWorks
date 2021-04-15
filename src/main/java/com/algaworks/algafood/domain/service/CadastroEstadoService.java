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
	
	private static final String ESTADO_EM_USO = "Estado de código %d não pode ser removida, pois esta em uso ";
	private static final String NÃO_EXISTE_ESTADO = "Não existe o estado com o id: %d";
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
					String.format(NÃO_EXISTE_ESTADO, idEstado)
					);
		}catch(DataIntegrityViolationException e ) {
			throw new EntidadeEmUsoException(
					String.format(ESTADO_EM_USO, idEstado)
					);
			
		}
		
	}
	
	public Estado buscarOuFalhar(Long estadoId) {
		return estadoRepository.findById(estadoId)
				.orElseThrow(()-> new EntidadeNaoEncontradaException(
						String.format(NÃO_EXISTE_ESTADO, estadoId)));

	}
	
	
	
	

}
