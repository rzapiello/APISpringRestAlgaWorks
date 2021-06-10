package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExcepionHandler extends ResponseEntityExceptionHandler {

	
	private static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu un erro interno inesperado no sistema. Tente novamente e se o problema persistir entre em contato com o administrador";

		
	protected ResponseEntity<Object> handleNoHandlerFoundException(
			NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		if (ex instanceof NoHandlerFoundException) {
			return handleMethodArgumentNoHandlerFoundException(ex,headers, status, request);
			
		}

		return handleExceptionInternal(ex, null, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		BindingResult bindingResult = ex.getBindingResult();
		List<Problem.Field> problemFields = bindingResult.getFieldErrors().stream()
						.map(fieldError -> Problem.Field.builder()
						.name(fieldError.getField())
						.userMessage(fieldError.getDefaultMessage())
						.build())
						.collect(Collectors.toList());
		
		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detail = "Um ou mais campos estão invalido. Faça o preenchimento correto e tente novamente";
		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).fields(problemFields).build();
	
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(
			TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
			
		if(ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException )ex,headers, status, request);
		}

		return super.handleExceptionInternal((MethodArgumentTypeMismatchException)ex, null, headers, status, request);
	}
	




	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		if(rootCause instanceof InvalidFormatException) {

			return handleInvalidFormat((InvalidFormatException)rootCause,headers, status, request); 
			
		}else if(rootCause instanceof PropertyBindingException) {
			
			return handlePropertyBindingException((PropertyBindingException)rootCause,headers, status, request); 
		} 
		
		
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = "O corpo da execução esta inválido. Verifique erro de sintaxe";
		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
	
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

	}
	
	
	private ResponseEntity<Object> handleMethodArgumentNoHandlerFoundException(NoHandlerFoundException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		
		String detail = String.format("O recurso'%s', que você tentou acessar é inexistente" , 
				  ex.getRequestURL());
Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();

return handleExceptionInternal(ex,problem, headers, status, request);
		
	}

	
	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = ex.getPath().stream().map(ref ->ref.getFieldName()).collect(Collectors.joining("."));
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		
		String detail = String.format("A propriedade '%s' não existe. Corrija ou remova essa propriedade e tente novamente" , 
				  path);
Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();

return handleExceptionInternal(ex,problem, headers, status, request);
	}
	
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(TypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
		String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', que é um tipo inválido. Corrija e informe um valor compativel com o tipo '%s'",
				   ((MethodArgumentTypeMismatchException) ex).getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
		
		return handleExceptionInternal(ex,problem, headers, status, request);


	}
	

	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String path =	ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
		
		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		String detail = String.format("A propriedade '%s' recebeu o valor '%s',"
									  +" que é de um tipo invalido. Corrija e informe um valor compatível com o tipo '%s'" , 
									  path,ex.getValue(),ex.getTargetType().getSimpleName());
		Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
		
		return handleExceptionInternal(ex,problem, headers, status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(
			EntidadeNaoEncontradaException ex, WebRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		String detail = ex.getMessage();
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
	
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocio(NegocioException ex,  WebRequest request){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		String detail = ex.getMessage();
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		
		Problem problem = createProblemBuilder(status,problemType, detail).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex,  WebRequest request) {
		

		HttpStatus status = HttpStatus.CONFLICT;
		String detail = ex.getMessage();
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		
		Problem problem = createProblemBuilder(status,problemType, detail).userMessage(detail).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleErroGenerico(Exception ex, WebRequest request){
		
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;
		ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
		
		Problem problem = createProblemBuilder(status,problemType, detail).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
		  ex.printStackTrace();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
		
	}
		
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
		body =   Problem.builder()
	           
	            .title(status.getReasonPhrase())
	            .status(status.value())
	            .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
	            .timestamp(LocalDateTime.now())
	            .build();
		}else if (body instanceof String) {
			body =   Problem.builder()
		            .title((String)body)
		            .status(status.value())
		            .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
		            .timestamp(LocalDateTime.now())
		            .build();
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail){
		
		LocalDateTime agora = LocalDateTime.now(); 
		
		return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail)
				.timestamp(agora);
	}
	

	
}
