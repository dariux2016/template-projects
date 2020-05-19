package com.github.dariux2016.camel.exceptionhandling;

import java.util.List;

import org.springframework.util.Assert;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException{

	private static final long serialVersionUID = 8776126175165775619L;
	
	protected List<? extends BaseMessage> messageList;
	private String objectName;
	private Object object;
	
	public BaseException() {
		super();
	}
	
	public BaseException(final Throwable t, final List<? extends BaseMessage> messageList) {
		super(t);
		Assert.notNull(messageList, "Messages must not be null.");
		Assert.notEmpty(messageList, "Messages must not be empty.");
		this.messageList = messageList;
	}

	public BaseException(final List<? extends BaseMessage> validationMessageList) {
		super();
		Assert.notNull(validationMessageList, "Messages must not be null.");
		Assert.notEmpty(validationMessageList, "Messages must not be empty.");
		this.messageList = validationMessageList;
	}
	
	public void setObject(String objectName, Object object) {
		this.objectName = objectName;
		this.object = object;
	}
	
	public abstract List<? extends BaseMessage> getMessageList();
}
