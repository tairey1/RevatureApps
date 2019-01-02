package com.revature.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao<T,I extends Serializable> {

	List<T> findAll();
	T findByID(I id);
	T findByEmail(String name);
	T insert(T obj);
	T update(T obj);
	void delete(T obj);
	
	default boolean isUnique(T obj) {
		return true;
	}
	
}
