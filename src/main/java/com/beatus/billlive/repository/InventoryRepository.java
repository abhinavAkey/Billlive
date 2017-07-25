package com.beatus.billlive.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.beatus.billlive.domain.model.Inventory;

@Component("inventoryRepository")
public class InventoryRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(InventoryRepository.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	public void addInventory(Inventory inventory) {
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(inventory);
		logger.info("User saved successfully, User Details="+inventory);
	}

	public void updateInventory(Inventory inventory) {
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(inventory);
		logger.info("User updated successfully, User Details="+inventory);
	}

	@SuppressWarnings("unchecked")
	public List<Inventory> listInventorys() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Inventory> inventoryList = session.createQuery("from Inventory").list();
		for(Inventory inventory : inventoryList){
			logger.info("User List::"+inventory);
		}
		return inventoryList;
	}

	public Inventory getInventoryById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		Inventory inventory = (Inventory) session.load(Inventory.class, new Integer(id));
		logger.info("User loaded successfully, User details="+inventory);
		return inventory;
	}
	
	
	public void removeInventory(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Inventory inventory = (Inventory) session.load(Inventory.class, new Integer(id));
		if(null != inventory){
			session.delete(inventory);
		}
		logger.info("Inventory deleted successfully, person details="+inventory);
	}

}