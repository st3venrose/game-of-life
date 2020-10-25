package com.gol.golbackend.common;

import com.gol.golbackend.dto.ErrorDTO;
import com.gol.golbackend.dto.ValidationErrorResponse;
import com.gol.golbackend.dto.Violation;
import com.gol.golbackend.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErrorHandlingControllerAdvisor {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorDTO> notFoundException(final NotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ErrorDTO(HttpStatus.NOT_FOUND.toString(), e.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ValidationErrorResponse processValidationError(final MethodArgumentNotValidException e) {
		ValidationErrorResponse error = new ValidationErrorResponse();
		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			error.getViolations().add(
					new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
		}
		return error;
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ValidationErrorResponse  processConstraintViolationError(final ConstraintViolationException e) {
		ValidationErrorResponse error = new ValidationErrorResponse();
		for (ConstraintViolation violation : e.getConstraintViolations()) {
			error.getViolations().add(
					new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
		}
		return error;
	}
}
