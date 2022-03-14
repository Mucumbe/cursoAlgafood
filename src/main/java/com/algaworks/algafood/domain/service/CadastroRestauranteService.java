package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Não existe cadastro de cozinha com Codigo: %d", cozinhaId)));

		restaurante.setCozinha(cozinha);
		restaurante = repository.save(restaurante);
		return restaurante;
	}

	public Restaurante actualizar(Restaurante restaurante, long restauranteId) {

		long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId).orElseThrow(() -> 
		new EntidadeNaoEncontradaException(
				String.format("Não existe cadastro de cozinha com Codigo: %d", cozinhaId)));

		restaurante.setCozinha(cozinha);
		Optional<Restaurante> restauranteActual = repository.findById(restauranteId);
		BeanUtils.copyProperties(restaurante, restauranteActual.get(), "id","formaPagamento");
		restaurante = repository.save(restauranteActual.get());
		return restaurante;
	}
}
