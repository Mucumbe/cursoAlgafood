package com.algaworks.algafood.api.domain.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(value = HttpStatus.CONFLICT)
public class RestauranteEmUsoException extends EntidadeEmUsoException {

	private static final long serialVersionUID = 1L;

	public RestauranteEmUsoException(String message) {
		super(message);
		
	}

	public RestauranteEmUsoException(Long id) {
	
		this(String.format("O Restaurante com Id %d nao pode ser eliminado porque esta em uso",id));
	}
	
	
	
	

}
