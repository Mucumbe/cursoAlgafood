package com.algaworks.algafood.api.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algaworks.algafood.api.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> find(String nome, BigDecimal taxaInicia, BigDecimal taxaFinal);
	List<Restaurante> findComFreteGrates(String nome);

}