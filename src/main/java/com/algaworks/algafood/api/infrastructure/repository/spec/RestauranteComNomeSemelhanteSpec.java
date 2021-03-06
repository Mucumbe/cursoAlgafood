package com.algaworks.algafood.api.infrastructure.repository.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.api.domain.model.Restaurante;

import lombok.AllArgsConstructor;
@AllArgsConstructor
public class RestauranteComNomeSemelhanteSpec  implements Specification<Restaurante>{

	private String nome;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		// TODO Auto-generated method stub
		return builder.like(root.get("nome"),"%"+nome+"%");
	}

}
