package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.infrastruture.repository.spec.RestauranteSpecs;

@RestController
@RequestMapping("/teste")
public class TesteController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private com.algaworks.algafood.domain.repository.RestauranteRepository restauranteRepository;

	@GetMapping("/cozinhas/por-nome")
	public List<Cozinha> cozinhasPorNome(@RequestParam("nome") String nome){
		
		return cozinhaRepository.findTodasBynomeContaining(nome);
		
	}
	
	
	@GetMapping("/cozinhas/unica-por-nome")
	public Optional<Cozinha> cozinhasPorNomeOptional(@RequestParam("nome") String nome){
		
		return cozinhaRepository.findBynome(nome);
		
	}
	
	@GetMapping("/restaurantes/por-taxa-frete")
	public List<Restaurante> restaurantePorTaxaFrete(BigDecimal taxaInicial,
													 BigDecimal taxaFinal){
		
		return restauranteRepository.queryByTaxaFreteBetween(taxaInicial,taxaFinal);
	}
	
	@GetMapping("/restaurantes/por-nome-idCozinha")
	public List<Restaurante> restaurantePorTaxaFrete(String nome,
													 Long id){
		
		return restauranteRepository.consultarPorNome(nome,id);
	}
	
	@GetMapping("/restaurantes/primeiro-por-nome")
	public Optional<Restaurante> restaurantePrimeiroPorNome(String nome){
		
		return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
	}
	
	@GetMapping("/restaurantes/top2-por-nome")
	public List<Restaurante> restauranteTop2PorNome(String nome){
		
		return restauranteRepository.findTop2ByNomeContaining(nome);
	}
	
	
	@GetMapping("/cozinhas/exists")
	public boolean cozinhaExists(String nome){
		
		return cozinhaRepository.existsByNome(nome);
	}
	
	@GetMapping("/restaurantes/count-por-cozinha")
	public int countPorCozinha(Long cozinhaId){
		
		return restauranteRepository.countByCozinhaId(cozinhaId);
	}
	
	@GetMapping("/restaurantes/por-nome-e-frete")
	public List<Restaurante> restaurantePorNomeFrete(String nome,
			BigDecimal taxaFreteInicial,BigDecimal taxaFreteFinal ){
		
		
		return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
	}
	
	@GetMapping("/restaurantes/com=frete-gratis")
	public List<Restaurante> restaurantesComFreteGratis(String nome){
		return restauranteRepository.findComFreteGratis(nome);
		
	}
	
	@GetMapping("/restaurantes/primeiro")
	public Optional<Restaurante> restaurantePrimeiro(){
		return restauranteRepository.buscarPrimeiro();
		
	}
	


}
