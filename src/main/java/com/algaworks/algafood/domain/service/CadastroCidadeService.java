package com.algaworks.algafood.domain.service;

import java.util.Optional;

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

	private static final String CIDADE_NAO_ENCONTRADA = "Cidade com Id %d nao encontrada";
	@Autowired
	private CidadeRepository repository;
	@Autowired
	private EstadoRepository estadoRepository;

	public Cidade salvar(Cidade cidade) {
		long estadoId = cidade.getEstado().getId();
		
		Estado estado = estadoRepository.findById(estadoId).orElseThrow(
				()-> new EntidadeNaoEncontradaException(String.format("Estado com Id %d nao encontrado", estadoId)));
		
		cidade.setEstado(estado);
		cidade = repository.save(cidade);
		return cidade;

	}

	public void excluir(long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(CIDADE_NAO_ENCONTRADA, id));
		}
	}
	
	public Cidade actualizar(Cidade cidade,long id) {
		buscaPorIdValidada( id);
		long estadoId=cidade.getEstado().getId();
		Estado estado =estadoRepository.findById(estadoId).orElseThrow(
				()->new EntidadeNaoEncontradaException(String.format("Estado com codigo %d nao existe",estadoId))
				);
		
		
		cidade.setEstado(estado);
		Optional<Cidade> cidadeActual=repository.findById(id);
		BeanUtils.copyProperties(cidade, cidadeActual.get(),"id");
		Cidade cidadeSalva=repository.save(cidadeActual.get());
		return cidadeSalva;
	}
	
	public Cidade buscaPorIdValidada(long id) {
		
		return repository.findById(id).orElseThrow(()-> new EntidadeNaoEncontradaException(String.format(CIDADE_NAO_ENCONTRADA, id)));
	}

}
