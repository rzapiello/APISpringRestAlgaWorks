package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.SenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

import lombok.Getter;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	CadastroUsuarioService cadastroUsuario;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	UsuarioModelAssembler  usuarioModelAssembler;
	
	@Autowired
	UsuarioInputDisassembler usuarioInputDisassembler;
	
	
	@GetMapping
	public List<UsuarioModel> listar() {
		
		List<Usuario> todosUsuarios = usuarioRepository.findAll();
		
		return usuarioModelAssembler.toCollectionModel(todosUsuarios);
	}
	
	  @GetMapping("/{usuarioId}")
	    public UsuarioModel buscar(@PathVariable Long usuarioId) {
	        Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
	        
	        return usuarioModelAssembler.toModel(usuario);
	    }
	
	  @PostMapping
     public UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput  usuarioInput) {
	        Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
	        usuario = cadastroUsuario.salvar(usuario);
	        return usuarioModelAssembler.toModel(usuario);
	    }
	  
	  
	  @PutMapping("/{usuarioId}")
	  public UsuarioModel adicionar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuarioInput) {
		  
		  Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);
 		  usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual); 
		  usuarioAtual = cadastroUsuario.salvar(usuarioAtual);
		  
		   return usuarioModelAssembler.toModel(usuarioAtual);
	  }
	  
	  @PutMapping("/{usuarioId}/senha")
	  public UsuarioModel atualizarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
		  
		  Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);
		  
		  cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getSenhaNova());


		  
		   return usuarioModelAssembler.toModel(usuarioAtual);
	  }
	  
	  
	
 
}
