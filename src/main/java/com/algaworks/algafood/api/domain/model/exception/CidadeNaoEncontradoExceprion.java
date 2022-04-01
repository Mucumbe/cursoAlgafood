package com.algaworks.algafood.api.domain.model.exception;

public class CidadeNaoEncontradoExceprion extends EntidadeNaoEncontradaException {


	private static final long serialVersionUID = 1L;

	public CidadeNaoEncontradoExceprion(String message) {
		super(message);
		
	}
	
	public CidadeNaoEncontradoExceprion( long id) {
		
		this(String.format("Nao Existe um cadastro de cidade com código: %d",id));
	}
	

	
	
}
