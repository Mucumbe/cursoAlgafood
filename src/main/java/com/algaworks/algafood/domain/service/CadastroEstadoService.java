package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	private static final String ENTIDA_NAO_ENCONTRADA = "Entidade com codifo %d não foi encontrada";
	@Autowired
	private EstadoRepository repository;

	public Estado salvar(Estado estado) {

		return repository.save(estado);
	}
	
	public void excluir(long id) {
		
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Estado com codifo %d não pode ser removido porque esta em uso",id));
		}catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format(ENTIDA_NAO_ENCONTRADA,id));
		}
		
	}
	
	public Estado buscarPorId(Long id) {
		
		return repository.findById(id).orElseThrow(()-> new EntidadeNaoEncontradaException(String.format(ENTIDA_NAO_ENCONTRADA,id)));
	}
	
}
