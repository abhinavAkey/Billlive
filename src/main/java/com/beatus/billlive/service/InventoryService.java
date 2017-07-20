package com.beatus.billlive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beatus.billlive.domain.model.Inventory;
import com.beatus.billlive.repository.InventoryRepository;


@Service
public class InventoryService {
	
	@Autowired
	private InventoryRepository inventoryRepository;

	public void setInventoryRepository(InventoryRepository inventoryRepository) {
		this.inventoryRepository = inventoryRepository;
	}

	@Transactional
	public void addInventory(Inventory inventory) {
		this.inventoryRepository.addInventory(inventory);
	}

	
	@Transactional
	public void updateInventory(Inventory inventory) {
		this.inventoryRepository.updateInventory(inventory);
	}


	@Transactional
	public List<Inventory> listInventorys() {
		return this.inventoryRepository.listInventorys();
	}


	@Transactional
	public Inventory getInventoryById(int id) {
		return this.inventoryRepository.getInventoryById(id);
	}


	@Transactional
	public void removeInventory(int id) {
		this.inventoryRepository.removeInventory(id);
	}

}