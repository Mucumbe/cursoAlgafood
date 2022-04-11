package com.algaworks.algafood.api.domain.model.exception;

public class CozinhaNaoEncontradoExceprion extends RuntimeException {


	private static final long serialVersionUID = 1L;

	public CozinhaNaoEncontradoExceprion(String message) {
		
		super(message);
		
	}
	
	public CozinhaNaoEncontradoExceprion(Long id) {
		
		this(String.format("Nao Existe um cadastro de Cozinha com código: %d",id));
	}
	

	
	
}
