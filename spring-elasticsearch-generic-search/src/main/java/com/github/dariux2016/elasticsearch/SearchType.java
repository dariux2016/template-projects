package com.github.dariux2016.elasticsearch;

public enum SearchType {
	
	LIKE("like"),
	EQUAL("equal"),
	PREFIX("prefix"),
	DATE_LT("date_less_than"),
	DATE_LE("date_less_than_or_equal"),
	DATE_GT("date_greater_than"),
	DATE_GE("date_greater_than_or_equal"),
	;
	
	private String value;
	
	private SearchType(String value) {
		this.value = value;
	}
	
	public static SearchType getEnum(String value) {
		for(SearchType s : SearchType.values()) {
			if(s.name().equals(value)) {
				return s;
			}
		}
		return null;
	}
}
