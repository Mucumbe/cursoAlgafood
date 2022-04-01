package com.algaworks.algafood.api.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.api.domain.model.Cozinha;
import com.algaworks.algafood.api.domain.model.Restaurante;
import com.algaworks.algafood.api.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.api.domain.model.exception.NegocioException;
import com.algaworks.algafood.api.domain.model.exception.RestauranteEmUsoException;
import com.algaworks.algafood.api.domain.model.exception.RestauranteNaoEncontradoExceprion;
import com.algaworks.algafood.api.domain.repository.CozinhaRepository;
import com.algaworks.algafood.api.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	RestauranteRepository repository;
	@Autowired
	CozinhaRepository cozinhaRepository;

	@Autowired
	CadastroCozinhaService cozinhaService;

	public Restaurante salvar(Restaurante restaurante) {
		long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha;
		try {
			cozinha = cozinhaService.buscarPor(cozinhaId);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(),e);
		}

		restaurante.setCozinha(cozinha);
		restaurante = repository.save(restaurante);
		return restaurante;
	}

	public Restaurante actualizar(Restaurante restaurante, long restauranteId) {

		long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha;
		try {
			cozinha = cozinhaService.buscarPor(cozinhaId);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(),e);
		}

		restaurante.setCozinha(cozinha);
		Restaurante restauranteActual = buscarPorId(restauranteId);
		BeanUtils.copyProperties(restaurante, restauranteActual, "id", "formaPagamento", "endereco", "dataCadastro");
		restaurante = repository.save(restauranteActual);
		return restaurante;
	}

	public void remover(long id) {

		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new RestauranteNaoEncontradoExceprion(id);
		} catch (DataIntegrityViolationException e) {
			throw new RestauranteEmUsoException(id);
		}
		
		

	}

	public Restaurante buscarPorId(long id) {
		Restaurante restaurante = repository.findById(id).orElseThrow(() -> new RestauranteNaoEncontradoExceprion(id));
		return restaurante;
	}
}
