package com.dmontielfdez.gadgetonadmin.model;

/**
 * POJO class to model a product
 * @author dmontiel
 *
 */
public class Product {

	int id;
	String name;
	double rrp;
	String summary;
	int stock;
	String feature;
	String imageName;
	int category_id;

	/**
	 * Create a product with parameters
	 * @param id Product's id
	 * @param name Product's name
	 * @param rrp Product's rrp
	 * @param summary Product's summary
	 * @param stock Product's stock
	 * @param feature Product's feature
	 * @param imageName Product's imageName
	 * @param category_id Product's category_id
	 */
	public Product(int id, String name, double rrp,String summary, int stock, String feature, String imageName,
			int category_id) {
		super();
		this.id = id;
		this.name = name;
		this.rrp = rrp;
		this.summary = summary;
		this.stock = stock;
		this.feature = feature;
		this.imageName = imageName;
		this.category_id = category_id;
	}
	
	/**
	 * Create a empty product
	 */
	public Product() {
		super();
	}

	/**
	 * Return Product's id
	 * @return Product's id
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Set an id to the product
	 * @param id
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * Return Product's id
	 * @return Product's id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set an id to the product
	 * @param Product's id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Return Product's name
	 * @return Product's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set a name to the product
	 * @param Product's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Return Product's rrp
	 * @return Product's rrp
	 */
	public double getRrp() {
		return rrp;
	}

	/**
	 * Set a rrp to the product
	 * @param Product's rrp
	 */
	public void setRrp(double rrp) {
		this.rrp = rrp;
	}

	/**
	 * Return Product's stock
	 * @return Product's stock
	 */
	public int getStock() {
		return stock;
	}

	/**
	 * Set a stock to the product
	 * @param Product's stock
	 */
	public void setStock(int stock) {
		this.stock = stock;
	}

	/**
	 * Return Product's feature
	 * @return Product's feature
	 */
	public String getFeature() {
		return feature;
	}

	/**
	 * Set a feature to the product
	 * @param Product's feature
	 */
	public void setFeature(String feature) {
		this.feature = feature;
	}

	/**
	 * Return Product's category id
	 * @return Product's category id
	 */
	public int getCategory_id() {
		return category_id;
	}

	/**
	 * Set category id to the product
	 * @param Product's category id
	 */
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	/**
	 * Return Product's image Name
	 * @return Product's image Name
	 */
	public String getImageName() {
		return imageName;
	}

	/**
	 * Set an imageName to the product
	 * @param Product's image Name
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

}
