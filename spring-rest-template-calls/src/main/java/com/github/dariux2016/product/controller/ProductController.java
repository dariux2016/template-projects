package com.github.dariux2016.product.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.dariux2016.product.model.Product;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping(
		value = "/products",
		produces = { MediaType.APPLICATION_JSON_VALUE  })
public class ProductController {
	
	private static Long maxId = 3L;
	private static List<Product> products;
	
	static{
		products = new ArrayList<>();
		products.add(Product.builder().id(1L).code("A").name("Book").build());
		products.add(Product.builder().id(2L).code("B").name("Computer").build());
		products.add(Product.builder().id(3L).code("C").name("Table").build());
	}
	
	@GetMapping
	@ApiOperation(value = "Get products", notes = "Get products", response = Product.class)
	public List<Product> getAll() {
		return products;
	}
	
	@GetMapping("{id}")
	@ApiOperation(value = "Get product by id", notes = "Get product by id", response = Product.class)
	public Product getById(@PathVariable Long id) {
		return products.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
	}
	
	@PostMapping
	@ApiOperation(value = "Save new product", notes = "Save new product", response = Product.class)
	@ResponseStatus(HttpStatus.CREATED)
	public Product save(@RequestBody Product product) {
		maxId = maxId + 1L;
		product.setId(maxId);
		products.add(product);
		return product;
	}
	
	@PutMapping("{id}")
	@ApiOperation(value = "Update product", notes = "Update product", response = Product.class)
	@ResponseStatus(HttpStatus.OK)
	public Product update(@PathVariable Long id, @RequestBody Product product) {
		for (Product p : products) {
			if(p.getId().equals(id)) {
				p.setCode(product.getCode());
				p.setName(product.getName());
				return p;
			}
		}
		return null;
	}

}
