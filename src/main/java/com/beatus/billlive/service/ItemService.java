package com.beatus.billlive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beatus.billlive.domain.model.ItemData;
import com.beatus.billlive.repository.ItemRepository;


@Service
@Component("itemService")
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;

	public void setItemRepository(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@Transactional
	public void addItem(ItemData item) {
		this.itemRepository.addItem(item);
	}

	
	@Transactional
	public void updateItem(ItemData item) {
		this.itemRepository.updateItem(item);
	}


	@Transactional
	public List<ItemData> getAllItems(String uid) {
		return this.itemRepository.getAllItems(uid);
	}


	@Transactional
	public ItemData getItemById(String id) {
		return this.itemRepository.getItemById(id);
	}


	@Transactional
	public void removeItem(String id) {
		this.itemRepository.removeItem(id);
	}

}