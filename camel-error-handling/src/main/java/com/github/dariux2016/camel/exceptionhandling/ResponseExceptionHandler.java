package com.github.dariux2016.camel.exceptionhandling;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.camel.CamelExecutionException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

/**
 * Main Exception Handler
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResponseExceptionHandler{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ResponseExceptionHandler.class);
	
	/**
	 * Translate a specific exception to the related ResponseWrapper object
	 * 
	 * @param exception
	 * @return
	 */
	private ResponseWrapper translateException(Throwable exception) {
		ResponseWrapper responseMessage = null;
		
		if(exception instanceof ValidationException) {
			ValidationException validationException = (ValidationException) exception;
			
			List<ValidationMessage> messageList = validationException.getMessageList();
			String objectName = validationException.getObjectName();
			Object object = validationException.getObject();
			
			responseMessage = new ResponseWrapper(null, messageList, objectName, object);
		} else if(exception instanceof ApplicationException) {
			ApplicationException applicationException = (ApplicationException) exception;
			List<ApplicationMessage> messageList = applicationException.getMessageList();
			String objectName = applicationException.getObjectName();
			Object object = applicationException.getObject();
			responseMessage = new ResponseWrapper(messageList, null, objectName, object);
		} else {
			ApplicationMessage applicationMessage=ApplicationMessage.builder()
						.message("Generic internal error").build();
			responseMessage = new ResponseWrapper(Arrays.asList(applicationMessage),null,null,null);
		}
		
		return responseMessage;
	}
	
	public ResponseEntity<ResponseWrapper> getResponseEntity(ResponseWrapper responseWrapper,HttpStatus httpStatus){
		return new ResponseEntity<>(responseWrapper, httpStatus);
	}
	
	/**
	 * Handle CamelExecutionException extracting the inner exception
	 * @param e
	 * @param request
	 * @return
	 * @throws Throwable 
	 */
	@ExceptionHandler(CamelExecutionException.class)
	public final ResponseEntity<ResponseWrapper> handleCamelExecutionException(CamelExecutionException e, WebRequest request) {
		LOGGER.error("handleCamelExecutionException {}",ExceptionUtils.getStackTrace(e));
		Throwable exception = e.getExchange().getException();
		
		if( !(exception instanceof ApplicationException || exception instanceof ValidationException) && exception.getCause() != null ){
			//get the real cause from the CamelExecutionException
			exception = exception.getCause();
		}
		
		ResponseWrapper wrapper = translateException(exception);
		
		if ((exception instanceof ApplicationException) 
				|| (exception instanceof ValidationException))
			return getResponseEntity(wrapper,HttpStatus.BAD_REQUEST);
		else
			return getResponseEntity(wrapper,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * Handle exception when a specific ValidationException object was throwed
	 * @param e
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ExceptionHandler(ValidationException.class)
	@ResponseBody
	public final ResponseEntity<ResponseWrapper> handleValidationException(ValidationException exception, WebRequest request) {
		LOGGER.error("handleValidationException {}",ExceptionUtils.getStackTrace(exception));
		ResponseWrapper responseModel = translateException(exception);
		return getResponseEntity(responseModel,HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Handle exception when a specific ApplicationException object was throwed
	 * @param e
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ExceptionHandler(ApplicationException.class)
	@ResponseBody
	public final ResponseEntity<ResponseWrapper> handleApplicationException(ApplicationException exception, WebRequest request) {
		LOGGER.error("handleApplicationException {}",ExceptionUtils.getStackTrace(exception));
		ResponseWrapper responseModel = translateException(exception);
		return getResponseEntity(responseModel,HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * Handle any generic exceptions
	 * @param e
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public final ResponseEntity<ResponseWrapper> handleGenericException(Exception exception, WebRequest request) {
		LOGGER.error("handleGenericException {}",ExceptionUtils.getStackTrace(exception));
		ResponseWrapper responseModel = translateException(exception);
		return getResponseEntity(responseModel,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
