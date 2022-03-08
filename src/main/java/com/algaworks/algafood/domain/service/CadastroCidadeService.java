package com.algaworks.algafood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository repository;
	@Autowired
	private EstadoRepository estadoRepository;

	public Cidade salvar(Cidade cidade) {
		long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.buscar(estadoId);
		if (estado == null) {
			throw new EntidadeNaoEncontradaException(String.format("Cidade com Id %d nao encontrado", estadoId));
		}

		cidade = repository.adicionar(cidade);
		return cidade;

	}

	public void excluir(long id) {
		try {
			repository.remover(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Cidade com Id %d nao encontrada", id));
		}
	}
	
	public Cidade actualizar(Cidade cidade,long id) {
		long estadoId=cidade.getEstado().getId();
		Estado estado =estadoRepository.buscar(estadoId);
		if (estado==null) {
			throw new EntidadeNaoEncontradaException(String.format("Estado com codigo %d nao existe",estadoId));
		}
		
		cidade.setEstado(estado);
		Cidade cidadeActual=repository.buscar(id);
		BeanUtils.copyProperties(cidade, cidadeActual,"id");
		cidadeActual=repository.adicionar(cidadeActual);
		return cidadeActual;
	}

}
