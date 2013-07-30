package com.dmontielfdez.gadgeton.model;

/**
 * POJO class to model a line
 * @author dmontiel
 *
 */
public class Line {

	/**
	 * Line's id
	 */
	int id;
	
	/**
	 * Line's rRP
	 */
	double RRP;
	
	/**
	 * Line's quantity
	 */
	int quantity;

	/**
	 * Line's subtotal
	 */
	double subtotal;

	/**
	 * Line's product_id
	 */
	int product_id;

	/**
	 * Line's order_id
	 */
	int order_id;

	/**
	 * Create a line with parameters
	 * @param id Line's id
	 * @param rRP Line's rRP
	 * @param quantity Line's quantity
	 * @param subtotal Line's subtotal
	 * @param product_id Line's product_id
	 * @param order_id Line's order_id
	 */
	public Line(int id, double rRP, int quantity, double subtotal,
			int product_id, int order_id) {
		super();
		this.id = id;
		RRP = rRP;
		this.quantity = quantity;
		this.subtotal = subtotal;
		this.product_id = product_id;
		this.order_id = order_id;
	}
	
	/**
	 * Create a empty line
	 */
	public Line() {
		super();
	}

	/**
	 * Return Line's id
	 * @return Line's id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set an id to the line
	 * @param Line's id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Return Line's RRP
	 * @return Line's RRP
	 */
	public double getRRP() {
		return RRP;
	}

	/**
	 * Set a RRP to the line
	 * @param Line's RRP
	 */
	public void setRRP(double rRP) {
		RRP = rRP;
	}

	/**
	 * Return Line's quantity
	 * @return Line's quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Set a quantity to the line
	 * @param Line's quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Return Line's subtotal
	 * @return Line's subtotal
	 */
	public double getSubtotal() {
		return subtotal;
	}

	/**
	 * Set a subtotal to the line
	 * @param Line's subtotal
	 */
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	/**
	 * Return Line's product id
	 * @return Line's product id
	 */
	public int getProduct_id() {
		return product_id;
	}

	/**
	 * Set a product id to the line
	 * @param Line's product id
	 */
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	/**
	 * Return Line's order id
	 * @return Line's order id
	 */
	public int getOrder_id() {
		return order_id;
	}

	/**
	 * Set an order id to the line
	 * @param Line's order id
	 */
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}



}
