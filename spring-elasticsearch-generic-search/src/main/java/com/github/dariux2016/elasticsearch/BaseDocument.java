package com.github.dariux2016.elasticsearch;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDocument implements Serializable{

	private static final long serialVersionUID = 1542464016588390947L;
	
	@Id
	private String id;
}
