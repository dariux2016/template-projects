package com.github.dariux2016.elasticsearch;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class SearchFilter {
	
	private List<String> fields;
	private SearchType searchType;
	private String value;
	
}
