package com.algaworks.algafood.domain.repository;



import java.math.BigDecimal;
import java.util.List;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long> ,RestauranteRepositoryQueries,
JpaSpecificationExecutor<Restaurante>{

	@Query("from Restaurante r join r.cozinha join  fetch r.formaPagamento")
	List<Restaurante> findAll();
	List<Restaurante> findByTaxaFreteBetween(BigDecimal a, BigDecimal b);
	List<Restaurante> findByNomeContainingAndCozinhaId(String a, Long b);

}
