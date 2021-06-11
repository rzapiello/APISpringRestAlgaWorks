package com.algaworks.algafood.api.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired 
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@GetMapping
	public List<Cidade> listar(){
		return cidadeRepository.findAll();
	}
	
	
	@GetMapping("/{idCidade}")
	public Cidade buscar(@PathVariable Long idCidade) {
		return  cadastroCidade.buscarOuFalhar(idCidade);
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody  @Valid Cidade cidade) {
		try {
		return cadastroCidade.salvar(cidade);
		}catch(EstadoNaoEncontradoException e) {
		throw new NegocioException(e.getMessage(),e);
		
	}
			//return cidade;
		}
	
	
	@DeleteMapping("/{idCidade}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover (@PathVariable Long idCidade) {
		
		cadastroCidade.excluir(idCidade);
				
	}
	
	
	@PutMapping("/{idCidade}")
	public Cidade atualizar(@PathVariable Long idCidade, @RequestBody  @Valid Cidade cidade) {
		
		
		
		try {
			
			Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(idCidade);
			BeanUtils.copyProperties(cidade, cidadeAtual, "id");
			return cadastroCidade.salvar(cidadeAtual);
		}catch(EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(),e);
			
		}

	}
	
	
	
	
}

