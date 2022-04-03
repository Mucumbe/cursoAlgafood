package com.algaworks.algafood.api.exceptionHandler;

import java.time.LocalDate;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.api.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.api.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.api.domain.model.exception.NegocioException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {

		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;
		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail).build();

//		Problem problem = Problem.builder().status(status.value())
//				.type("https://algafood.com.br/entidade-nao-encontrada").title("Entidade não encontada")
//				.detail(ex.getMessage()).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.NOT_FOUND, request);

	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		String detail = ex.getMessage();
		Problem problem = createProblemBuilder(status, problemType, detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		if (rootCause instanceof InvalidFormatException) {

			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}else if (rootCause instanceof PropertyBindingException) {
			
			return handlePropertyBindingException((PropertyBindingException) rootCause,headers,status,request);
		}

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENIVEL;
		String detail = "O corpo da requisição está inválidade, verifique o erro de sintaxe";

		Problem problem = createProblemBuilder(status, problemType, detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	

	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException rootCause,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
			
		ProblemType problemType=ProblemType.ERRO_SINTAXE;
		
		String path=rootCause.getPath().stream().map(ref-> ref.getFieldName()).collect(Collectors.joining("."));
		path=path.toUpperCase();
		
		String detail =String.format("A propiedade '%s' não faz parte desta requisição", path);
		
		Problem problem=createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(rootCause, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException rootCause, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENIVEL;
		
		String path=rootCause.getPath().stream().map(ref-> ref.getFieldName()).collect(Collectors.joining("."));
		String detail = String.format(
				"Apropiedade '%s' recebeu valor '%s', que é de um tipo invávlido, Corrija e informe um valor combatível com o tipo %s.",
				path, rootCause.getValue(), rootCause.getTargetType().getSimpleName());

		Problem problem = createProblemBuilder(status, problemType, detail).build();
		return handleExceptionInternal(rootCause, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			body = Problem.builder().title(status.getReasonPhrase()).status(status.value()).build();

		} else if (body instanceof String) {
			body = Problem.builder().title((String) body).status(status.value()).build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {

		return Problem.builder().status(status.value()).type(problemType.getUri()).title(problemType.getTitle())
				.detail(detail);

	}

}
