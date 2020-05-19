package com.github.dariux2016.camel.exceptionhandling;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

/**
 * Application exception used during operations over a model
 */
@Getter
public class ApplicationException extends BaseException{

	private static final long serialVersionUID = 3072576641776445642L;

	public ApplicationException(final Throwable t, final List<ApplicationMessage> applicationMessageList) {
		super(t, applicationMessageList);
	}

	public ApplicationException(final List<ApplicationMessage> applicationMessageList) {
		super(applicationMessageList);
	}
	
	public ApplicationException(final ApplicationMessage applicationMessage) {
		super(Arrays.asList(applicationMessage));
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ApplicationMessage> getMessageList() {
		return (List<ApplicationMessage>) this.messageList;
	}
	
}
