package com.algaworks.algafood.api.domain.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.CONFLICT)
public class EstadoEmUsoException extends EntidadeEmUsoException {

	private static final long serialVersionUID = 1L;

	public EstadoEmUsoException(String message) {
		super(message);
		
	}

	public EstadoEmUsoException(Long id) {
	
		this(String.format("o Estado com Id %d nao pode ser eliminado porque esta em uso",id));
	}
	
	
	
	

}
