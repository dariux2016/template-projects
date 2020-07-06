package com.github.dariux2016.elasticsearch;

public enum SortDirection {

	ASC("asc"),
	DESC("desc");
	
	private String sort;
	
	private SortDirection(String sort) {
		this.sort = sort;
	}
	
	public static SortDirection getEnum(String sort) {
		for(SortDirection s : SortDirection.values()) {
			if(s.name().equals(sort)) {
				return s;
			}
		}
		return null;
	}
}
