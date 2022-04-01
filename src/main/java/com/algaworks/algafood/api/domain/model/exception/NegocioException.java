package com.algaworks.algafood.api.domain.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NegocioException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NegocioException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
