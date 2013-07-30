package com.dmontielfdez.gadgetonadmin.ddbb;

import java.util.List;

public interface Crudable<T> {

	public final static String URL = "http://gadgeton.pagodabox.com/";

	public String insert(T t);
	
	public String update(T t);
	
	public String delete(T t);
	
	public List<T> findAll();
	
	public T findByPK(String pkvalue);
	
	
	
}
