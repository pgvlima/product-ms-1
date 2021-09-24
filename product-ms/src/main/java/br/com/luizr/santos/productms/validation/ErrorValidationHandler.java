package br.com.luizr.santos.productms.validation;

import javax.persistence.EntityNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorValidationHandler {

	//Retorna uma lista de erros de "bad request", de acordo com a exception ocorrida
	@ResponseStatus(code = HttpStatus.BAD_REQUEST) // seta o HTTP Code de response, nesse caso 400
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorFormDto methodArgumentNotValidException(MethodArgumentNotValidException exception) {
	    return new ErrorFormDto(HttpStatus.BAD_REQUEST.value(), exception.getBindingResult().getFieldError().getDefaultMessage()); 
	}
	
	//Retorna a mensagem de erro "not found", quando um recurso não existir com o id de parametro informado
	@ResponseStatus(code = HttpStatus.NOT_FOUND) // seta o HTTP Code de response, nesse caso 404
	@ExceptionHandler(EntityNotFoundException.class)
	public ErrorFormDto entityNotFoundException(EntityNotFoundException exception) {
		return new ErrorFormDto(HttpStatus.NOT_FOUND.value(), "Nenhuma dado encontrada com o parâmetro informado");
	}

	//Retorna a mensagem de erro "not found", quando um recurso não existir com o id de parametro informado
	@ResponseStatus(code = HttpStatus.NOT_FOUND) // seta o HTTP Code de response, nesse caso 404
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ErrorFormDto emptyResultDataAccessException(EmptyResultDataAccessException exception) {
		System.out.println("Passou 2");
		return new ErrorFormDto(HttpStatus.NOT_FOUND.value(), exception.getLocalizedMessage());
	}
}