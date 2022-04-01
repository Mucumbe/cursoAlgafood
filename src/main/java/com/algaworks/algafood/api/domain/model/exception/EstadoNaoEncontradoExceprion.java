package com.algaworks.algafood.api.domain.model.exception;

public class EstadoNaoEncontradoExceprion extends EntidadeNaoEncontradaException {


	private static final long serialVersionUID = 1L;

	public EstadoNaoEncontradoExceprion(String message) {
		super(message);
		
	}
	
	public EstadoNaoEncontradoExceprion( long id) {
		
		this(String.format("Nao Existe um cadastro de estado com código: %d",id));
	}
	

	
	
}
