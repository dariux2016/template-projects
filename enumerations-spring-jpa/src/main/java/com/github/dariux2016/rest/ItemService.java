package com.github.dariux2016.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.dariux2016.model.EnItemType;
import com.github.dariux2016.model.Item;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	
	public List<Item> findItems() {
		List<ItemEntity> entityList = itemRepository.findAll();
		List<Item> results = new ArrayList<>();
		for (ItemEntity itemEntity : entityList) {
			Item model = new Item();
			BeanUtils.copyProperties(itemEntity, model);
			results.add(model);
		}
		return results;
	}
	
	public List<Item> findItemByType(EnItemType type) {
		List<ItemEntity> entityList = itemRepository.findByType(type);
		List<Item> results = new ArrayList<>();
		for (ItemEntity itemEntity : entityList) {
			Item model = new Item();
			BeanUtils.copyProperties(itemEntity, model);
			results.add(model);
		}
		return results;
	}
	
	public Item saveItem(Item item) {
		ItemEntity entity = new ItemEntity();
		BeanUtils.copyProperties(item, entity);
		
		ItemEntity saved = itemRepository.save(entity);
		Item result = new Item();
		BeanUtils.copyProperties(saved, result);
		return result;
	}
}
