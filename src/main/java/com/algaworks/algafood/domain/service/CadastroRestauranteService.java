package com.algaworks.algafood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	RestauranteRepository repository;
	@Autowired
	CozinhaRepository cozinhaRepository;

	public Restaurante salvar(Restaurante restaurante) {
		long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
		if (cozinha == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de cozinha com Codigo: %d", cozinhaId));
		}
		restaurante.setCozinha(cozinha);
		restaurante = repository.adicionar(restaurante);
		return restaurante;
	}

	public Restaurante actualizar(Restaurante restaurante,long restauranteId) {
		
		long cozinhaId=restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
		
		
		if (cozinha == null) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de cozinha com Codigo: %d", cozinhaId));
		}
		restaurante.setCozinha(cozinha);
		Restaurante restauranteActual = repository.buscar(restauranteId);
		BeanUtils.copyProperties(restaurante, restauranteActual, "id");
		restauranteActual = repository.adicionar(restauranteActual);
		return restauranteActual;
	}
	
	public void excluir(long id) {
		try {
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Restaurante com id %d nao Existe", id));
		}
	}
}
