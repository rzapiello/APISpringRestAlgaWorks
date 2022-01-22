package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.algafood.api.model.EnderecoModel;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.Restaurante;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new  ModelMapper();
		
		//modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
		//.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);
		//.addMapping(Restaurante::getTaxaFrete,Restaurante::setPrecoFrete);
		
		var enderecooEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoModel.class);
		
		enderecooEnderecoModelTypeMap.<String>addMapping(
				EnderecoSrc -> EnderecoSrc.getCidade().getEstado().getNome(), 
				(EnderecoModelDest,value) -> EnderecoModelDest.getCidade().getEstado());
		
		return modelMapper;
	}

}
