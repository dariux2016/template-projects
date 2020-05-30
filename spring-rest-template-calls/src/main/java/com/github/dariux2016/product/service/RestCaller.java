package com.github.dariux2016.product.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.github.dariux2016.product.model.ServicePath;

/**
 * Generic service to call REST endpoints. It uses {@link ServicePath} class to resolve the endpoint to call.
 * @param <T>
 */
@Service
@Lazy
public class RestCaller<T> {
	
	@Autowired
	private PropertyResolver propertyResolver;
	
	@Autowired
	private RestTemplateResponseErrorHandler defaultErrorHandler;
	
	/**
	 * Call for an operation
	 * @param service : service enumeration referring to the URL to be called
	 * @param httpMethod : HTTP method to call (GET, POST, PUT, ...)
	 * @param id : optional, will be appended to the URL path
	 * @param params: optional request parameters
	 * @param body: request body
	 * @param responseType
	 * @param customErrorHandler : custom error handler
	 * @return
	 */
	public ResponseEntity<T> call(ServicePath service, HttpMethod httpMethod, 
								Long id, Map<String, Object> params, T body, 
								Class<T> responseType, RestTemplateResponseErrorHandler customErrorHandler) {
		
		String url = propertyResolver.getPropertyValue(service.getPath());
		if(id != null) {
			url =  url + "/" + id;
		}
		
		if(params == null) {
			params = new HashMap<>();
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
//		headers.set("jwtHeader", jwtHeader);
		
		RestTemplate restTemplate = new RestTemplate();
		setErrorHandler(restTemplate, customErrorHandler);
		return restTemplate.exchange(url, httpMethod, new HttpEntity<>(body, headers), responseType, params);
	}
		
	private void setErrorHandler(RestTemplate restTemplate, RestTemplateResponseErrorHandler errorHandler) {
		if(errorHandler == null) {
			//set the default error handler
			restTemplate.setErrorHandler(defaultErrorHandler);
		} else {
			restTemplate.setErrorHandler(errorHandler);
		}
	}
	
}