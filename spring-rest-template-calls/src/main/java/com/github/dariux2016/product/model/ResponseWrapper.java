package com.github.dariux2016.product.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Wrap the response messages and further data
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class ResponseWrapper implements Serializable {
	
	private static final long serialVersionUID = -9176766069696459856L;
	
	private String message;
	//you can put here, anything else you want to return to the callers

}
