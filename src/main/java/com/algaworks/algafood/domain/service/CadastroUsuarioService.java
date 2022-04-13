package com.algaworks.algafood.domain.service;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {
	
	private static final String USUARIO_EM_USU = "usuario de código: %d não pode ser excluido, esta em uso";
	
	@Autowired
	UsuarioRepository usuarioRepository;

	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistente =  usuarioRepository.findByEmail(usuario.getEmail());
		
		if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(String.format("Já existe usuário cadastrado com o email: %s", usuario.getEmail()));
		}
		
		
		return usuarioRepository.save(usuario);	
	}
	
          
 
	
	
	 @Transactional
	    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
	        Usuario usuario = buscarOuFalhar(usuarioId);
	        
	        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
	            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
	        }
	        
	        usuario.setSenha(novaSenha);
	    }
	 
	 
	    public Usuario buscarOuFalhar(Long usuarioId) {
	        return usuarioRepository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
	    }  
	

	
	
}
