package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
@Component
public class CidadeRepositiryImpl implements CidadeRepository {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Cidade> listar() {
		
		return manager.createQuery("From Cidade",Cidade.class).getResultList();
	}

	@Override
	public Cidade buscar(long id) {
		// TODO Auto-generated method stub
		return manager.find(Cidade.class, id);
	}

	@Transactional
	@Override
	public Cidade adicionar(Cidade cidade) {
		
		return manager.merge(cidade);
	}

	@Transactional
	@Override
	public void remover(long id) {
		Cidade cidade=buscar(id);
		if (cidade==null) {
			throw new EmptyResultDataAccessException(1);
		}
		manager.remove(cidade);
		
	}

}
