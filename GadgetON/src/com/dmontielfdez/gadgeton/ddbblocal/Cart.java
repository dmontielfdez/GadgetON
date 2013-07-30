package com.dmontielfdez.gadgeton.ddbblocal;


/**
 * POJO class to model a cart
 * @author dmontiel
 *
 */
public class Cart {
	
	/**
	 * id Cart's id
	 */
	int id;
	
	/**
	 * Cart's quantity
	 */
	int quantity; 
	
	/**
	 * Cart's product's id
	 */
	int id_product;
	
	/**
	 * Cart's customer's id
	 */
	int id_customer;
	
	/**
	 * Create a empty cart
	 */
	public Cart() {
		super();
	}
	
	/**
	 * Create a cart with params
	 * @param id Cart's id
	 * @param quantity Cart's quantity
	 * @param id_product product's id
	 * @param id_customer customer's id
	 */
	public Cart(int id, int quantity, int id_product, int id_customer) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.id_product = id_product;
		this.id_customer = id_customer;
	}
	
	/**
	 * Return Cart's id
	 * @return Cart's id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Set an id to the cart
	 * @param id Cart's id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Return Cart's quantity
	 * @return Cart's quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	
	/**
	 * Set a quantity to the cart
	 * @param id Cart's quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * Return Cart's product's id
	 * @return Cart's product's id
	 */
	public int getId_product() {
		return id_product;
	}
	
	/**
	 * Set a product's id to the cart
	 * @param id Cart's product's id
	 */
	public void setId_product(int id_product) {
		this.id_product = id_product;
	}
	
	/**
	 * Return Cart's customer's id
	 * @return Cart's customer's id
	 */
	public int getId_customer() {
		return id_customer;
	}
	
	/**
	 * Set a customer's id to the cart
	 * @param id Cart's customer's id
	 */
	public void setId_customer(int id_customer) {
		this.id_customer = id_customer;
	}
	
	
	
	
	
	
	

}
