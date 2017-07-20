package com.beatus.billlive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beatus.billlive.domain.model.Item;
import com.beatus.billlive.repository.ItemRepository;


@Service
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;

	public void setItemRepository(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@Transactional
	public void addItem(Item item) {
		this.itemRepository.addItem(item);
	}

	
	@Transactional
	public void updateItem(Item item) {
		this.itemRepository.updateItem(item);
	}


	@Transactional
	public List<Item> listItems() {
		return this.itemRepository.listItems();
	}


	@Transactional
	public Item getItemById(int id) {
		return this.itemRepository.getItemById(id);
	}


	@Transactional
	public void removeItem(int id) {
		this.itemRepository.removeItem(id);
	}

}