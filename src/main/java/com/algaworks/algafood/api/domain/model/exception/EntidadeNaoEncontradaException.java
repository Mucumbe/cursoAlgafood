package com.algaworks.algafood.api.domain.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public abstract class EntidadeNaoEncontradaException extends NegocioException {

	
	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncontradaException(String message) {

		super(message);

	}
}