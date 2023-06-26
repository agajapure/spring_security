package com.example.exceptions;

public class ExceptionDto {
	String message;
	Integer status;
	String time;
	String url;
	Object errors;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public Object getErrors() {
		return errors;
	}
	public void setErrors(Object errors) {
		this.errors = errors;
	}

	public ExceptionDto(String message, Integer status, String time) {
		super();
		this.message = message;
		this.status = status;
		this.time = time;
	}
	
	public ExceptionDto(String message, Integer status, String time, Object errors) {
		super();
		this.message = message;
		this.status = status;
		this.time = time;
		this.errors = errors;
	}
	
	public ExceptionDto() {
		super();
	}
	
}
