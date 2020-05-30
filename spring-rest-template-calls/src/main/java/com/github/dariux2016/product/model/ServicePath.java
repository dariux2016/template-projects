package com.github.dariux2016.product.model;

import lombok.Getter;

/**
 * Map a service name to a property, which define the path 
 * to call for invoking the respective REST service
 */
@Getter
public enum ServicePath {

	PRODUCTS("products.path");
	
	private final String path;
	
	private ServicePath(String pathProperty) {
		this.path = pathProperty;
	}
	
}
