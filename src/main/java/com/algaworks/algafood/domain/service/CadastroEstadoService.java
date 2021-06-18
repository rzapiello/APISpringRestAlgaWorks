package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Repository
public class CadastroEstadoService {

	private static final String ESTADO_EM_USO = "Estado de código %d não pode ser removida, pois esta em uso ";
	@Autowired
	private EstadoRepository estadoRepository;

	public Estado buscarOuFalhar(Long estadoId) {
		return estadoRepository.findById(estadoId).orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));

	}

	@Transactional
	public void excluir(Long idEstado) {

		try {
			estadoRepository.deleteById(idEstado);
		} catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradoException(idEstado);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(ESTADO_EM_USO, idEstado));

		}

	}

	@Transactional
	public Estado salvar(Estado estado) {

		return estadoRepository.save(estado);
	}

}
