package com.beatus.billlive.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.beatus.billlive.domain.model.Item;


@Repository
@Transactional
public class ItemRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(ItemRepository.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	public void addItem(Item item) {
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(item);
		logger.info("User saved successfully, User Details="+item);
	}

	public void updateItem(Item item) {
		Session session = this.sessionFactory.getCurrentSession();
		session.saveOrUpdate(item);
		logger.info("User updated successfully, User Details="+item);
	}

	@SuppressWarnings("unchecked")
	public List<Item> listItems() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Item> itemList = session.createQuery("from Item").list();
		for(Item item : itemList){
			logger.info("User List::"+item);
		}
		return itemList;
	}

    @Cacheable(value="itemCache", key="#item.uid")
	public Item getItemById(int id) {
		Session session = this.sessionFactory.getCurrentSession();		
		Item item = (Item) session.load(Item.class, new Integer(id));
		logger.info("User loaded successfully, User details="+item);
		return item;
	}
	
	
	public void removeItem(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Item item = (Item) session.load(Item.class, new Integer(id));
		if(null != item){
			session.delete(item);
		}
		logger.info("Item deleted successfully, person details="+item);
	}

}