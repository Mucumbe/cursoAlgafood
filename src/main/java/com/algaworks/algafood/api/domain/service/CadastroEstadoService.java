package com.algaworks.algafood.api.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.api.domain.model.Estado;
import com.algaworks.algafood.api.domain.model.exception.EstadoEmUsoException;
import com.algaworks.algafood.api.domain.model.exception.EstadoNaoEncontradoExceprion;
import com.algaworks.algafood.api.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	@Autowired
	private EstadoRepository repository;

	public Estado salvar(Estado estado) {

		return repository.save(estado);
	}
	
	public void excluir(long id) {
		
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new EstadoEmUsoException(id);
		}catch (EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradoExceprion(id);
		}
		
	}
	
	public Estado buscarPorId(Long id) {
		
		return repository.findById(id).orElseThrow(()-> new EstadoNaoEncontradoExceprion(id));
	}
	
}
