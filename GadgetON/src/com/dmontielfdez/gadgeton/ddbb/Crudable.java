package com.dmontielfdez.gadgeton.ddbb;

import java.util.List;

/**
 * Interface that provides all methods for use with DDBB
 * @author dmontiel
 *
 * @param <Model Class>
 */
public interface Crudable<T> {

	public final static String URL = "http://gadgeton.gopagoda.com/";

	public String insert(T t);
	
	public String update(T t);
	
	public String delete(T t);
	
	public List<T> findAll();
	
	public T findByPK(String pkvalue);
	
	
	
}
