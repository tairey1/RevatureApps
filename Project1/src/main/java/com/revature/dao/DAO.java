package com.revature.dao;

import java.io.Serializable;
import java.util.List;

public interface DAO <T,I extends Serializable> {
	
	List<T> findAll();
	T findByID(I id);
	T insert(T obj);
	T update(T obj);
	
	default boolean isUnique(T obj) {
		return true;
	}
	
}
