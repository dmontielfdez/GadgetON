package com.dmontielfdez.gadgetonadmin.model;

/**
 * POJO class to model an order
 * @author dmontiel
 *
 */
public class Order {
	
	/**
	 * Order's id
	 */
	int id;
	
	/**
	 * Order's date
	 */
	String date;
	
	/**
	 * Order's state
	 */
	String state;
	
	/**
	 * Order's method Pay
	 */
	String methodPay;
	
	/**
	 * Order's total
	 */
	double total;
	
	/**
	 * Order's customer Id
	 */
	int customerId;

	/**
	 * Create an order with parameters
	 * @param id Order's id
	 * @param date Order's date
	 * @param state Order's state
	 * @param methodPay Order's method Pay
	 * @param total Order's total
	 * @param customerId Order's customer Id
	 */
	public Order(int id, String date, String state, String methodPay,
			double total, int customerId) {
		super();
		this.id = id;
		this.date = date;
		this.state = state;
		this.methodPay = methodPay;
		this.total = total;
		this.customerId = customerId;
	}

	/**
	 * Create a empty order
	 */
	public Order() {
		super();
	}

	/**
	 * Return Order's id
	 * @return Order's id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set an id to the order
	 * @param Order's id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Return Order's date
	 * @return Order's date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Set a date to the order
	 * @param Order's date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Return Order's state
	 * @return Order's state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Set a state to the order
	 * @param Order's state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Return Order's method Pay
	 * @return Order's method Pay
	 */
	public String getMethodPay() {
		return methodPay;
	}

	/**
	 * Set a method Pay to the order
	 * @param Order's method Pay
	 */
	public void setMethodPay(String methodPay) {
		this.methodPay = methodPay;
	}

	/**
	 * Return Order's total
	 * @return Order's total
	 */
	public double getTotal() {
		return total;
	}

	/**
	 * Set a total to the order
	 * @param Order's total
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * Return Order's customer Id
	 * @return Order's customer Id
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * Set a customer Id to the order
	 * @param Order's customer Id
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}




}
