package com.algaworks.algafood.api.domain.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.CONFLICT)
public abstract class EntidadeEmUsoException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public EntidadeEmUsoException(String message) {
		super(message);
		
	}
	
	

}
