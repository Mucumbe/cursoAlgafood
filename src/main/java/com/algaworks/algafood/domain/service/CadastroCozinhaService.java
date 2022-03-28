package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	private static final String ENTIDADE_NAO_ENCONTRADA = "Entida com Com id %d nao encontrada";
	@Autowired
	private CozinhaRepository repository;

	public Cozinha adicionar(Cozinha cozinha) {
		return repository.save(cozinha);
	}

	public void excluir(Long id) {

		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(ENTIDADE_NAO_ENCONTRADA, id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Entidade com o ID %d nao pode ser Removido esta em Uso", id));
		}

	}
	
	public Cozinha buscarPor(Long id) {
		return  repository.findById(id).orElseThrow(()-> new EntidadeNaoEncontradaException(String.format(ENTIDADE_NAO_ENCONTRADA, id)));
	}

}
