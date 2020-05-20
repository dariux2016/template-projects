package com.github.dariux2016.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.dariux2016.model.EnItemType;
import com.github.dariux2016.model.Item;

import io.swagger.annotations.Api;

@Api
@RestController
@RequestMapping(
		value = "/item",
		produces = { MediaType.APPLICATION_JSON_VALUE  })
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@GetMapping
	public List<Item> findItems() {
		return itemService.findItems();
	}
	
	@GetMapping("/{type}")
	public List<Item> findItemByType(@PathVariable("type") EnItemType type) {
		return itemService.findItemByType(type);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Item saveItem(@RequestBody Item item) {
		return itemService.saveItem(item);
	}

}
