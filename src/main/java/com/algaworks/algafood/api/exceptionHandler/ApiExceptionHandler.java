package com.algaworks.algafood.api.exceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.function.ServerRequest.Headers;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.api.domain.model.exception.EntidadeEmUsoException;
import com.algaworks.algafood.api.domain.model.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.api.domain.model.exception.NegocioException;
import com.algaworks.algafood.api.exceptionHandler.Problem.Field;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String MSG_ERRO_GENERICO_USUARIO_FINAL = "Ocorreu um erro interno no sistema. Tente novamente e se o problema persistir, entre em contacto com o administrador do sistema";

	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		

		if (ex instanceof MethodArgumentNotValidException) {
			
			ProblemType problemType = ProblemType.DADOS_INVALIDOS;
			String detail = "Um ou mais campos est??o Invalidos. Fa??a o preechimento correto e tente novamente";
			BindingResult bindingResult=ex.getBindingResult();
			
			List<Problem.Field> problemFields= bindingResult.getFieldErrors().stream()
					.map(fieldError->{ 
						
						String message= messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
				return	Problem.Field.builder().nome(fieldError.getField())
							.userMessage(message).build();}
					
							).collect(Collectors.toList());
			
			Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
					.timeStamp(LocalDateTime.now()).fields(problemFields).build();
			
			return handleExceptionInternal(ex, problem, headers, status, request);
		}
		
		
		return super.handleMethodArgumentNotValid(ex, headers, status, request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUnCought(Exception e, WebRequest request) {

		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
		String detail = MSG_ERRO_GENERICO_USUARIO_FINAL;

		e.printStackTrace();
		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
				.timeStamp(LocalDateTime.now()).build();

		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {

		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(
				"O recurso que pretende apagar nao pode ser eliminado porque esta em uso/vinculado com outra propiedade")
				.timeStamp(LocalDateTime.now()).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if (ex instanceof NoHandlerFoundException) {
			ProblemType problemType = ProblemType.RESCURSO_NAO_ENCONTRADO;
			String detail = String.format("O recurso %s, que voc?? tentou acessar, ?? inexistente.", ex.getRequestURL());
			Problem problem = createProblemBuilder(status, problemType, detail)
					.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL).timeStamp(LocalDateTime.now()).build();

			return handleExceptionInternal(ex, problem, headers, status, request);
		}

		return super.handleNoHandlerFoundException(ex, headers, status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {

		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RESCURSO_NAO_ENCONTRADO;
		String detail = ex.getMessage();

		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage("O recurso que tenta acessar ni sistema nao existe,Verrifique se os dados estao conforme")
				.timeStamp(LocalDateTime.now()).build();

//		Problem problem = Problem.builder().status(status.value())
//				.type("https://algafood.com.br/entidade-nao-encontrada").title("Entidade n??o encontada")
//				.detail(ex.getMessage()).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.NOT_FOUND, request);

	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		String detail = ex.getMessage();
		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
				.timeStamp(LocalDateTime.now()).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleNumberFormatException((MethodArgumentTypeMismatchException) ex, headers, status, request);
		}

		return super.handleTypeMismatch(ex, headers, status, request);
	}

	private ResponseEntity<Object> handleNumberFormatException(MethodArgumentTypeMismatchException ex,
			HttpHeaders header, HttpStatus status, WebRequest request) {

		status = HttpStatus.BAD_REQUEST;

		ProblemType problemType = ProblemType.PARAMENTRO_INVALIDO;
		String detail = String.format(
				"O par??metro de URL '%s' recebeu o valor '%s',"
						+ " que ?? de um tipo inv??lido.Corrija e informe um valor compativel com o tipo '%s'",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
				.timeStamp(LocalDateTime.now()).build();
		return handleExceptionInternal(ex, problem, header, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		
		System.err.println("teste teste tetse");
		
		if (rootCause instanceof InvalidFormatException) {

			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		} else if (rootCause instanceof PropertyBindingException) {

			return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
		}

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENIVEL;
		String detail = "O corpo da requisi????o est?? inv??lidade, verifique o erro de sintaxe";

		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
				.timeStamp(LocalDateTime.now()).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException rootCause,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.ERRO_SINTAXE;

		String path = joinPath(rootCause);

		String detail = String.format(
				"A propiedade '%s' n??o faz parte desta requisi????o. Corija ou remova essa propiedade e tente novamente",
				path);

		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
				.timeStamp(LocalDateTime.now()).build();

		return handleExceptionInternal(rootCause, problem, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException rootCause, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENIVEL;

		String path = rootCause.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
		String detail = String.format(
				"Apropiedade '%s' recebeu valor '%s', que ?? de um tipo inv??vlido, Corrija e informe um valor combat??vel com o tipo %s.",
				path, rootCause.getValue(), rootCause.getTargetType().getSimpleName());

		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL)
				.timeStamp(LocalDateTime.now()).build();
		return handleExceptionInternal(rootCause, problem, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			body = Problem.builder().title(status.getReasonPhrase()).status(status.value())
					.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL).timeStamp(LocalDateTime.now()).build();

		} else if (body instanceof String) {
			body = Problem.builder().title((String) body).status(status.value())
					.userMessage(MSG_ERRO_GENERICO_USUARIO_FINAL).timeStamp(LocalDateTime.now()).build();
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {

		return Problem.builder().status(status.value()).type(problemType.getUri()).title(problemType.getTitle())
				.detail(detail);
	}

	private String joinPath(PropertyBindingException rootCause) {
		String path = rootCause.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
		path = path.toUpperCase();
		return path;
	}

}
