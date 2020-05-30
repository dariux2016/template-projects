package com.github.dariux2016.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * Service to resolve property by name.
 */
@Service
public class PropertyResolver {
	
	@Autowired
	private Environment environment;
	
	/**
	 * Get the property value only by its key
	 * @param property
	 * @return
	 */
	public String getPropertyValue(String property){
		return environment.getProperty(property);
	}
	
	/**
	 * Get the property values only by its key
	 * @param property
	 * @return
	 */
	public String[] getPropertyValuesArray(String property){
		return environment.getProperty(property).split(",");
	}
	
}