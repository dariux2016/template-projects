package com.github.dariux2016.elasticsearch;

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
public class SearchSort {

	private SortDirection sortDirection = SortDirection.ASC; //default asc
	private String sort;
	
}
