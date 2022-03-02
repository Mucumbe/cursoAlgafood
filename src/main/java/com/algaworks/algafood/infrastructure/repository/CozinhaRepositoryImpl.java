package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
@Component
public class CozinhaRepositoryImpl implements CozinhaRepository{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Cozinha> listar(){
		
		return manager.createQuery("from Cozinha", Cozinha.class).getResultList();
	}
	
	@Override
	public Cozinha buscar(long id) {
		return manager.find(Cozinha.class,id);
	}
	
	@Transactional
	@Override
	public void remover(Cozinha cozinha) {
		cozinha=buscar(cozinha.getId());
		manager.remove(cozinha);
	}
	
	@Transactional
	@Override
	public Cozinha adicionar(Cozinha cozinha) {
		
		return manager.merge(cozinha);
	}
}