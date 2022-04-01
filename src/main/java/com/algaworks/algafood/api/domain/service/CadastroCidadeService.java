package com.algaworks.algafood.api.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.api.domain.model.Cidade;
import com.algaworks.algafood.api.domain.model.Estado;
import com.algaworks.algafood.api.domain.model.exception.CidadeEmUsoException;
import com.algaworks.algafood.api.domain.model.exception.CidadeNaoEncontradoExceprion;
import com.algaworks.algafood.api.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.api.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.api.domain.model.exception.NegocioException;
import com.algaworks.algafood.api.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository repository;

	@Autowired
	CadastroEstadoService estadoService;

	public Cidade salvar(Cidade cidade) {
		long estadoId = cidade.getEstado().getId();

		Estado estado = estadoService.buscarPorId(estadoId);

		cidade.setEstado(estado);
		cidade = repository.save(cidade);
		return cidade;

	}

	public void excluir(long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradoExceprion(id);
		} catch (DataIntegrityViolationException e) {
			System.err.println("cidade em uso");
			throw new CidadeEmUsoException(id);
		}
	}

	public Cidade actualizar(Cidade cidade, long id) {
		buscaPorIdValidada(id);
		long estadoId = cidade.getEstado().getId();
		Estado estado;
		try {
			estado = estadoService.buscarPorId(estadoId);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}

		cidade.setEstado(estado);
		Cidade cidadeActual = buscaPorIdValidada(id);
		BeanUtils.copyProperties(cidade, cidadeActual, "id");
		Cidade cidadeSalva = repository.save(cidadeActual);
		return cidadeSalva;
	}

	public Cidade buscaPorIdValidada(long id) {
		return repository.findById(id).orElseThrow(() -> new CidadeNaoEncontradoExceprion(id));
	}

}
