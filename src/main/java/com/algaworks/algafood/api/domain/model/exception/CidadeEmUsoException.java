package com.algaworks.algafood.api.domain.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.CONFLICT)
public class CidadeEmUsoException extends EntidadeEmUsoException {

	private static final long serialVersionUID = 1L;

	public CidadeEmUsoException(String message) {
		super(message);
		
	}

	public CidadeEmUsoException(Long id) {
	
		this(String.format("A cidade com Id %d nao pode ser eliminado porque esta em uso",id));
	}
	
	
	
	

}
