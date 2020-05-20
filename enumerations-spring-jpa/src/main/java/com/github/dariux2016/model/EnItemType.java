package com.github.dariux2016.model;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EnItemType {

	TYPE1("CODE_1"),
	TYPE2("CODE_2"),
	TYPE3("CODE_3");
	
	private String code;
	
	private EnItemType(String code) {
		this.code=code;
	}
	
	@JsonCreator
	public static EnItemType decode(final String code) {
		return Stream.of(EnItemType.values()).filter(targetEnum -> targetEnum.code.equals(code)).findFirst().orElse(null);
	}
	
	@JsonValue
	public String getCode() {
		return code;
	}
}
