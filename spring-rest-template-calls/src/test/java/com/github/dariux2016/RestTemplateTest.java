package com.github.dariux2016;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.dariux2016.product.RestTemplateAppStarter;
import com.github.dariux2016.product.model.Product;
import com.github.dariux2016.product.model.ServicePath;
import com.github.dariux2016.product.service.RestCaller;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestTemplateAppStarter.class, webEnvironment=WebEnvironment.RANDOM_PORT)
public class RestTemplateTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateTest.class);
	
	@Autowired
	private RestCaller<Product> restCaller;
	
	@Autowired
	private RestCaller<Product[]> restCallerArray;
	
	@Test
	public void testGetAll() throws Exception {
		LOGGER.info("##################testGetAll");
		ResponseEntity<Product[]> response = restCallerArray.callForObject(ServicePath.PRODUCTS, HttpMethod.GET, 
															null, null, null, Product[].class, null);
		if(response.getStatusCode() == HttpStatus.OK) {
			Product[] list = response.getBody();
			for (Product product : list) {
				LOGGER.info("Found product {}", product);
			}
		}
		assertTrue(response.getStatusCode() == HttpStatus.OK);
	}
	
	@Test
	public void testGetById() throws Exception {
		LOGGER.info("##################testGetById");
		ResponseEntity<Product> response = restCaller.callForObject(ServicePath.PRODUCTS, HttpMethod.GET, 
															1L, null, null, Product.class, null);
		if(response.getStatusCode() == HttpStatus.OK) {
			Product product = response.getBody();
			LOGGER.info("Found product {}", product);
		}
		
		assertTrue(response.getStatusCode() == HttpStatus.OK);
	}
	
	@Test
	public void testPostById() throws Exception {
		LOGGER.info("##################testPostById");
		Product newProduct = Product.builder().code("Z").name("Television").build();
		ResponseEntity<Product> response = restCaller.callForObject(ServicePath.PRODUCTS, HttpMethod.POST, 
															null, null, newProduct, Product.class, null);
		if(response.getStatusCode() == HttpStatus.CREATED) {
			Product product = response.getBody();
			LOGGER.info("Found product {}", product);
		}
		
		assertTrue(response.getStatusCode() == HttpStatus.CREATED);
	}
	
	@Test
	public void testPutById() throws Exception {
		LOGGER.info("##################testPutById");
		Product newProduct = Product.builder().code("T").name("Videogame").build();
		ResponseEntity<Product> response = restCaller.callForObject(ServicePath.PRODUCTS, HttpMethod.PUT, 
															2L, null, newProduct, Product.class, null);
		if(response.getStatusCode() == HttpStatus.OK) {
			Product product = response.getBody();
			LOGGER.info("Found product {}", product);
		}
		
		assertTrue(response.getStatusCode() == HttpStatus.OK);
	}
}
