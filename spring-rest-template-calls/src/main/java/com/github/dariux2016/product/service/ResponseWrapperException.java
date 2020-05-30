package com.github.dariux2016.product.service;

import com.github.dariux2016.product.model.ResponseWrapper;

import lombok.Getter;

@Getter
public class ResponseWrapperException extends RuntimeException {

	private static final long serialVersionUID = -4202205109779254945L;
	
	private ResponseWrapper responseWrapper;
	
	public ResponseWrapperException(final ResponseWrapper responseWrapper) {
		this.responseWrapper = responseWrapper;
	}
}
