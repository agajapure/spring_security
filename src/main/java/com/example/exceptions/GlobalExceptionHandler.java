package com.example.exceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.utility.ErrorConstants;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RestException.class)
	@ResponseBody
	public ResponseEntity<ExceptionDto> resolveException(RestException exception) {
		return new ResponseEntity<>(new ExceptionDto(exception.getMessage(), exception.getStatus().value(), LocalDateTime.now().toString(), exception.getMessage()), exception.getStatus());
	}
	
	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<String> errors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			errors.add(((FieldError) error).getField() + ": " + error.getDefaultMessage());
		});
		return new ResponseEntity<>(new ExceptionDto(ErrorConstants.INVALID_PARAM, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(), errors), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public ResponseEntity<ExceptionDto> resolveException(ConstraintViolationException exception) {
		List<String> errors = new ArrayList<>();
		for (final ConstraintViolation<?> violation : exception.getConstraintViolations()) {
        	String fieldName = null;
            for (Node node : violation.getPropertyPath()) {
                fieldName = node.getName();
            }
            errors.add(fieldName + ": " + violation.getMessage());
        }
		return new ResponseEntity<>(new ExceptionDto(ErrorConstants.CONSTRAINT_VIOLATION, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(), errors), HttpStatus.BAD_REQUEST);
	}
}
