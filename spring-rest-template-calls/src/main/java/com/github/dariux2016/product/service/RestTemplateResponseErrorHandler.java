package com.github.dariux2016.product.service;

import java.io.IOException;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dariux2016.product.model.ResponseWrapper;

/**
 * Abstract Response Error Handler for {@link RestTemplate} class used in applications that need to call REST services.
 * Define only http-specific behaviors
 */
@Component
@Primary
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateResponseErrorHandler.class);
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public void manageExceptions(ResponseWrapper responseWrapper) {
		//by default, rethrow the response exception (given by the called app) up to the current application
		throw new ResponseWrapperException(responseWrapper);
	}
	
	@Override
	public boolean hasError(ClientHttpResponse httpResponse) 
			throws IOException {
		return (
				httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR 
				|| httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
	}

	@Override
	public void handleError(ClientHttpResponse httpResponse) {
		LOGGER.info("Got error response");
		String body = null;
		try{
			//try to read the body of the response
			body = StreamUtils.copyToString(httpResponse.getBody(), Charset.defaultCharset());
		} catch (IOException e) {
			//throw generic system exception
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		
		try {
			//try to decode the response as a ResponseWrapper object
			ResponseWrapper responseWrapper = objectMapper.readValue(body, ResponseWrapper.class);
			manageExceptions(responseWrapper);
		}catch(IOException e) {
			//throw generic system exception with body inside the object
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
}