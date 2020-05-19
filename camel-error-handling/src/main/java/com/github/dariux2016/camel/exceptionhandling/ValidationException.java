package com.github.dariux2016.camel.exceptionhandling;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

/**
 * Validation exception used during validation of model
 */
@Getter
public class ValidationException extends BaseException{

	private static final long serialVersionUID = 3265749858748529734L;
	
	public ValidationException(final Throwable t, final List<ValidationMessage> validationMessageList) {
		super(t, validationMessageList);
	}

	public ValidationException(final List<ValidationMessage> validationMessageList) {
		super(validationMessageList);
	}
	
	public ValidationException(final ValidationMessage validationMessage) {
		super(Arrays.asList(validationMessage));
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ValidationMessage> getMessageList() {
		return (List<ValidationMessage>) this.messageList;
	}
}
