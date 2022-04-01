package com.algaworks.algafood.api.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.api.domain.model.Restaurante;
import com.algaworks.algafood.api.domain.repository.RestauranteRepository;
import com.algaworks.algafood.api.domain.repository.RestauranteRepositoryQueries;
import com.algaworks.algafood.api.infrastructure.repository.spec.RestauranteComFreteGratisSpec;
import com.algaworks.algafood.api.infrastructure.repository.spec.RestauranteComNomeSemelhanteSpec;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	EntityManager manager;
	
	@Autowired @Lazy
	RestauranteRepository restauranteRepository;

	@Override
	public List<Restaurante> find(String nome, BigDecimal taxaInicia, BigDecimal taxaFinal) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		Root<Restaurante> root = criteria.from(Restaurante.class);

		var predicates= new ArrayList<Predicate>();
		
		if (StringUtils.hasText(nome)) {
			predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
		}
		
		if (taxaInicia!=null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaInicia));
		}
		
		if (taxaFinal!=null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFinal));
		}
		
		criteria.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Restaurante> query = manager.createQuery(criteria);

		return query.getResultList();

	}

	@Override
	public List<Restaurante> findComFreteGrates(String nome) {
		var freteGratis= new RestauranteComFreteGratisSpec();
		var nomeSemelhante= new RestauranteComNomeSemelhanteSpec(nome);
		return restauranteRepository.findAll(freteGratis.and(nomeSemelhante));
	}

}
