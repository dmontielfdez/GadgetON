package com.dmontielfdez.gadgetonadmin.model;

/**
 * POJO class to model an item
 * @author dmontiel
 *
 */
public class Item {
	
	/**
	 * item's image
	 */
	int image;
	
	/**
	 * item's name
	 */
	String name;
	
	/**
	 * Create an item with parameters
	 * @param image item's image
	 * @param name item's name
	 */
	public Item(int image, String name) {
		super();
		this.image = image;
		this.name = name;
	}
	
	/**
	 * Return item's image
	 * @return item's image
	 */
	public int getImage() {
		return image;
	}
	
	/**
	 * Set an image to the item
	 * @param item's image
	 */
	public void setImage(int image) {
		this.image = image;
	}
	
	/**
	 * Return item's name
	 * @return item's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set a name to the item
	 * @param item's name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	

}
