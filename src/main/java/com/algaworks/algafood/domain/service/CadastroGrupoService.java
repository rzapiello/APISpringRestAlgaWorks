package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {
	
	private static final String GRUPO_EM_USU ="Grupo de código %d não pode ser excluido, esta em uso";
	
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	public Grupo buscarOuFalahar(Long GrupoId) {
		return grupoRepository.findById(GrupoId).orElseThrow(() -> new GrupoNaoEncontradoException(GrupoId));
		
	}
	
	@Transactional
	public void excluir(Long GrupoId) {
		
		try {
			grupoRepository.deleteById(GrupoId);
			grupoRepository.flush();
		}catch(EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(GrupoId);
			
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(GRUPO_EM_USU, GrupoId));
		}
		
	}
	
	@Transactional
	public Grupo salvar(Grupo grupo) {
		
		return grupoRepository.save(grupo);
	}

}
