package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

	boolean existsByNome(String nome);

	Optional<Cozinha> findBynome(String nome);

	List<Cozinha> findTodasBynomeContaining(String nome);
	


}
