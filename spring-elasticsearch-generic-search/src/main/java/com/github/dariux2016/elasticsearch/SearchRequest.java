package com.github.dariux2016.elasticsearch;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchRequest {

	private Integer page; //null for all pages
	private Integer pageSize; //default 10
	private List<SearchSort> searchSorts;
	private List<SearchFilter> searchFilters;
	
}