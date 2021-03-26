package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
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
	public ResponseEntity<Cidade> buscar(@PathVariable Long idCidade) {
		Optional<Cidade> cidade = cidadeRepository.findById(idCidade);
		if (cidade.isPresent()) {
		return ResponseEntity.ok(cidade.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
		try{
			cidade = cadastroCidade.salvar(cidade);
		return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
			
		}
	}
	
	@DeleteMapping("/{idCidade}")
	public ResponseEntity<Cidade> remover (@PathVariable Long idCidade) {
		
		try {
		cadastroCidade.excluir(idCidade);
		return ResponseEntity.noContent().build();
		}catch (EntidadeNaoEncontradaException e) {
		return	ResponseEntity.notFound().build();
		}catch(EntidadeEmUsoException e) {
			return	ResponseEntity.status(HttpStatus.CONFLICT).build();
		}		
	}
	
	
	@PutMapping("/{idCidade}")
	public ResponseEntity<?> atualizar(@PathVariable Long idCidade, @RequestBody Cidade cidade) {
		
		try {
		Optional<Cidade>  cidadeAtual = cidadeRepository.findById(idCidade);

		
		if (cidadeAtual.isPresent()) {
			BeanUtils.copyProperties(cidade, cidadeAtual.get(), "id");
		
			Cidade cidadeSalva = cadastroCidade.salvar(cidadeAtual.get());
			
	
			return ResponseEntity.ok(cidadeSalva);
			}
		
		return ResponseEntity.notFound().build(); 
		
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		
		}	
	}
}

