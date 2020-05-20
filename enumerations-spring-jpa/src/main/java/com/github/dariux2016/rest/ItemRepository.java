package com.github.dariux2016.rest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.dariux2016.model.EnItemType;

public interface ItemRepository extends JpaRepository<ItemEntity, Long>{
	
	public List<ItemEntity> findByType(EnItemType type);

}
