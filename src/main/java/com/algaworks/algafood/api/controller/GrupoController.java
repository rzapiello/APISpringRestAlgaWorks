package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.assembler.GrupoModelAssembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
	
	@Autowired
	private CadastroGrupoService cadastroGrupo;

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private GrupoModelAssembler grupoModelAssembler;
	
	@Autowired
	private GrupoInputDisassembler grupoInputDisassembler;
	
	
	
	
	@GetMapping
	public List<GrupoModel> listar(){
		List<Grupo> todosGrupos =  grupoRepository.findAll();
		
		return grupoModelAssembler.toCollectionModel(todosGrupos);
	}
	
	@GetMapping("/{grupoId}")
	public GrupoModel   buscar(@PathVariable Long grupoId){
 
		
		Grupo grupo  = cadastroGrupo.buscarOuFalahar(grupoId);
		
		return grupoModelAssembler.toModel(grupo);
	}
	
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoinput) {
			Grupo grupo = grupoInputDisassembler.toDomainObject(grupoinput);
			grupo = cadastroGrupo.salvar(grupo);
			return grupoModelAssembler.toModel(grupo);
	}
	
	
	@PutMapping("/{grupoId}")
	public GrupoModel atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput) {
		
		Grupo grupoAtual = cadastroGrupo.buscarOuFalahar(grupoId);
		
		grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);
		
		grupoAtual = cadastroGrupo.salvar(grupoAtual);
		
		return grupoModelAssembler.toModel(grupoAtual);
	}
	
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable long grupoId) {
		
		cadastroGrupo.excluir(grupoId);
	}

	

}
