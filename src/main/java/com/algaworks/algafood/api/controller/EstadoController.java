package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private CadastroEstadoService cadastroEstado;

	@Autowired
	private EstadoRepository estadoRepository;

	@PutMapping("/{idEstado}")
	public Estado atualizar(@PathVariable Long idEstado, @RequestBody @Valid Estado estado) {

		Estado estadoAtual = cadastroEstado.buscarOuFalhar(idEstado);
		BeanUtils.copyProperties(estado, estadoAtual, "id");

		return cadastroEstado.salvar(estadoAtual);
	}

	@GetMapping("/{idEstado}")
	public Estado buscar(@PathVariable Long idEstado) {
		return cadastroEstado.buscarOuFalhar(idEstado);

	}

	@GetMapping
	public List<Estado> listar() {
		return estadoRepository.findAll();
	}

	@DeleteMapping("/{idEstado}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long idEstado) {
		cadastroEstado.excluir(idEstado);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado salvar(@RequestBody @Valid Estado estado) {
		return estadoRepository.save(estado);

	}

}
