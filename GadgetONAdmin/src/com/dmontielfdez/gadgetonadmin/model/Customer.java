package com.dmontielfdez.gadgetonadmin.model;

/**
 * POJO class to model a customer
 * @author dmontiel
 *
 */
public class Customer {

	/**
	 * Customer's id
	 */
	private int id;
	
	/**
	 * Customer's name
	 */
	private String name;
	
	/**
	 * Customer's surname
	 */
	private String surname;
	
	/**
	 * Customer's birthday
	 */
	private String birthday;
	
	/**
	 * Customer's address
	 */
	private String address;
	
	/**
	 * Customer's province
	 */
	private String province;
	
	/**
	 * Customer's postalCode
	 */
	private String postalCode;
	
	/**
	 * Customer's phone
	 */
	private String phone;
	
	/**
	 * Customer's email
	 */
	private String email;
	
	/**
	 * Customer's password
	 */
	private String password;
	
	/**
	 * Customer's image name
	 */
	private String imageName;

	/**
	 * Create a customer with parameters
	 * 
	 * @param id Customer's id
	 * @param name Customer's name
	 * @param surname Customer's surname
	 * @param birthday Customer's birthday
	 * @param address Customer's address
	 * @param province Customer's province
	 * @param postalCode Customer's postalCode
	 * @param phone Customer's phone
	 * @param email Customer's email
	 * @param password Customer's password
	 * @param imageName Customer's image name
	 */
	public Customer(int id, String name, String surname, String birthday,
			String address, String province, String postalCode, String phone,
			String email, String password, String imageName) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.birthday = birthday;
		this.address = address;
		this.province = province;
		this.postalCode = postalCode;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.imageName = imageName;
	}
	
	public Customer() {
		super();
	}

	/**
	 * Return Customers's image name
	 * @return Customers's image name
	 */
	public String getImageName() {
		return imageName;
	}

	/**
	 * Set an image name to the customer
	 * @param Customers's image name
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	/**
	 * Return Customers's id
	 * @return Customers's id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set an id to the customer
	 * @param Customers's id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Return Customers's name
	 * @return Customers's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set a name to the customer
	 * @param Customers's name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Return Customers's surname
	 * @return Customers's surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Set a surname to the customer
	 * @param Customers's surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	/**
	 * Return Customers's Birthday
	 * @return Customers's Birthday
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * Set a Birthday to the customer
	 * @param Customers's Birthday
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	/**
	 * Return Customers's Address
	 * @return Customers's Address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Set an Address to the customer
	 * @param Customers's Address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Return Customers's Province
	 * @return Customers's Province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * Set an Province to the customer
	 * @param Customers's Province
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * Return Customers's Postal Code
	 * @return Customers's Postal Code
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * Set a Postal Code to the customer
	 * @param Customers's Postal Code
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * Return Customers's Phone
	 * @return Customers's Phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Set a Phone to the customer
	 * @param Customers's Phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Return Customers's Email
	 * @return Customers's Email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set an Email name to the customer
	 * @param Customers's Email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Return Customers's password
	 * @return Customers's password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set a password name to the customer
	 * @param Customers's password
	 */
	public void setPassword(String password) {
		this.password = password;
	}





}
