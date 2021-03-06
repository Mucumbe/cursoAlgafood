package com.algaworks.algafood.api.infrastructure.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.yaml.snakeyaml.events.Event.ID;

import com.algaworks.algafood.api.domain.repository.CustomJpaRepository;


public class CustomJpaRepositoryImpl<T,ÍD> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID> {
	
	private EntityManager manager;

	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.manager=entityManager;
	}

	@Override
	public Optional<T> buscarPrimrito() {
		
		var jpql="from "+getDomainClass().getName();
		T entity= manager.createQuery(jpql,getDomainClass())
		.setMaxResults(1)
		.getSingleResult();
		return Optional.ofNullable(entity);
	}

}
