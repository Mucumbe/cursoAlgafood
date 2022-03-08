package com.algaworks.algafood.domain.repository;



import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> ,RestauranteRepositoryQueries{

	List<Restaurante> findByTaxaFreteBetween(BigDecimal a, BigDecimal b);
	List<Restaurante> findByNomeContainingAndCozinhaId(String a, Long b);

}
