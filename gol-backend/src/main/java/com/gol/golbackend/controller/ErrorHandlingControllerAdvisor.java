package com.gol.golbackend.controller;

import com.gol.golbackend.dto.ErrorDTO;
import com.gol.golbackend.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandlingControllerAdvisor {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorDTO> notFoundException(final NotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ErrorDTO(HttpStatus.NOT_FOUND.toString(), e.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDTO> processValidationError(final MethodArgumentNotValidException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ErrorDTO(HttpStatus.BAD_REQUEST.toString(), e.getMessage()));
	}
}
