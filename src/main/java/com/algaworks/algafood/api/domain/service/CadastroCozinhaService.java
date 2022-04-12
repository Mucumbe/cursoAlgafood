package com.algaworks.algafood.api.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.api.domain.model.Cozinha;
import com.algaworks.algafood.api.domain.model.exception.CozinhaEmUsoException;
import com.algaworks.algafood.api.domain.model.exception.CozinhaNaoEncontradoExceprion;
import com.algaworks.algafood.api.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	@Autowired
	private CozinhaRepository repository;

	public Cozinha adicionar(Cozinha cozinha) {
		return repository.save(cozinha);
	}

	public void excluir(Long id) {

		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradoExceprion(id);
		} catch (DataIntegrityViolationException e) {
			throw new CozinhaEmUsoException( id);
		}

	}
	
	public Cozinha buscarPor(Long id) {
		return  repository.findById(id).orElseThrow(()-> new CozinhaNaoEncontradoExceprion(id));
	}

}
