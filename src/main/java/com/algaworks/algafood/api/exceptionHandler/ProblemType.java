package com.algaworks.algafood.api.exceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	RESCURSO_NAO_ENCONTRADO("/entidade-nao-encontrada","Entidade não encontrada"),
	ENTIDADE_EM_USO("/entidade-em-uso","Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio","Violacao de regra do negocio"),
	MENSAGEM_INCOMPREENIVEL("/mensagem-incompresível","Mensagem incompresível"),
	ERRO_SINTAXE("/erro-de-sintaxe","Erro de sintaxe"),
	PARAMENTRO_INVALIDO("/parametro-invalido","Paramentro invalido");
	
	private String title;
	private String uri;
	
	private ProblemType(String path, String title) {
		
		this.uri="https://algafood.com.br"+path;
		this.title=title;
	}
}
