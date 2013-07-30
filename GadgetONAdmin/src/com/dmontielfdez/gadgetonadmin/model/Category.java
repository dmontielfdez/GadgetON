package com.dmontielfdez.gadgetonadmin.model;

/**
 * POJO class to model a category
 * @author dmontiel
 *
 */
public class Category {

	/**
	 * id Category's id
	 */
	int id;
	
	/**
	 * name Category name
	 */
	String name;
	
	/**
	 * feature Category's feature
	 */
	String feature;

	/**
	 * Create a category with parameters
	 * @param id Category's id
	 * @param name Category name
	 * @param feature Category's feature
	 */
	public Category(int id, String name, String feature) {
		super();
		this.id = id;
		this.name = name;
		this.feature = feature;
	}

	/**
	 * Create a empty category
	 */
	public Category() {
		super();
	}

	/**
	 * Return Category's id
	 * @return Category's id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Set an id to the category
	 * @param Category's id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Return Category's name
	 * @return Category's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set a name to the category
	 * @param Category's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Return Category's feature
	 * @return Category's feature
	 */
	public String getFeature() {
		return feature;
	}

	/**
	 * Set a feature to the category
	 * @param Category's feature
	 */
	public void setFeature(String feature) {
		this.feature = feature;
	}



}
