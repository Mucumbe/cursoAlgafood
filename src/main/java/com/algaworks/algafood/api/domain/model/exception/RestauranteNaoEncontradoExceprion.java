package com.algaworks.algafood.api.domain.model.exception;

public class RestauranteNaoEncontradoExceprion  extends EntidadeNaoEncontradaException{

	
	private static final long serialVersionUID = 1L;

	public RestauranteNaoEncontradoExceprion(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public RestauranteNaoEncontradoExceprion(long id) {
		
		this(String.format("Nao Existe um cadastro de Restaurante com código: %d",id));
	}
	
}
