package com.algaworks.algafood.api.domain.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.CONFLICT)
public class CozinhaEmUsoException extends EntidadeEmUsoException {

	private static final long serialVersionUID = 1L;

	public CozinhaEmUsoException(String message) {
		super(message);
		
	}

	public CozinhaEmUsoException(Long id) {
	
		this(String.format("A cozinha com Id %d nao pode ser eliminado porque esta em uso",id));
	}
	
	
	
	

}
