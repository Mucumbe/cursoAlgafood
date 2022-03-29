package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.exception.NegocioException;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

	private static final String CIDADE_NAO_ENCONTRADA = "Cidade com Id %d nao encontrada";
	@Autowired
	private CidadeRepository repository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	CadastroEstadoService estadoService;

	public Cidade salvar(Cidade cidade) {
		long estadoId = cidade.getEstado().getId();

		Estado estado = estadoRepository.findById(estadoId).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format("Estado com Id %d nao encontrado", estadoId)));

		cidade.setEstado(estado);
		cidade = repository.save(cidade);
		return cidade;

	}

	public void excluir(long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(CIDADE_NAO_ENCONTRADA, id));
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Cidade com Id %d não pode ser eliminado porque esta ", id));
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
		return repository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(
						String.format(CIDADE_NAO_ENCONTRADA, id)));
	}

}
