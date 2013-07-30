package com.dmontielfdez.gadgeton.ddbb;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.dmontielfdez.gadgeton.model.Product;
import com.dmontielfdez.gadgeton.util.RequestHttp;

/**
 * Crudable class for products. Include methods for insert, update, delete or find lines.
 * @author dmontiel
 *
 */
public class ProductCRUD implements Crudable<Product>{

	/**
	 * Insert a product in the DDBB
	 * 
	 * @param Product to insert
	 * @return Result of the insertion
	 */
	@Override
	public String insert(Product t) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Update a product
	 * 
	 * @param Product to update
	 * @return Result of the upgrade
	 */
	@Override
	public String update(Product t) {
		// TODO Auto-generated method stub
		return "";
	}

	/**
	 * Delete a product
	 * 
	 * @param Product to delete
	 * @return Result of the elimination
	 */
	@Override
	public String delete(Product t) {
		// TODO Auto-generated method stub
		return "";
	}

	/**
	 * Find all the products in the DDBB
	 * 
	 * @return A List with all orders
	 */
	@Override
	public List<Product> findAll() {
		return null;
	}

	/**
	 * Find a product by his primary key
	 * @param pkvalue Products's primary key
	 * @return Product found
	 */
	@SuppressWarnings("finally")
	@Override
	public Product findByPK(final String pkvalue) {
		FutureTask<Product> future = 
				new FutureTask<Product>(
						new Callable<Product>() {
							@Override
							public Product call() throws Exception {
								Product product = null;
								String respStr = RequestHttp.requestGET(Crudable.URL+"products/"+pkvalue);

								JSONObject respJSON = new JSONObject(respStr);								
								try {
									respJSON = new JSONObject(respStr);

									int id = respJSON.getInt("id");
									String name = respJSON.getString("name");
									double rrp = respJSON.getDouble("rrp");
									String summary = respJSON.getString("summary");
									int stock = respJSON.getInt("stock");
									String feature = respJSON.getString("feature");
									String imageName = respJSON.getString("image_name");
									int category_id = respJSON.getInt("category_id");

									product = new Product(id, name, rrp,summary, stock, feature,imageName, category_id);


								} catch (JSONException e) {
									e.printStackTrace();
								}
								return product;
							}
						});
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(future);
		Product product = new Product();

		try {
			product = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
			return  product;
		}
	}

	/**
	 * Return a highlighted product 
	 * @return Highlighted product 
	 */
	@SuppressWarnings("finally")
	public static Product getProductImportant() {
		FutureTask<Product> future = 
				new FutureTask<Product>(
						new Callable<Product>() {
							@Override
							public Product call() throws Exception {
								Product product = null;
								String respStr = RequestHttp.requestGET(Crudable.URL+"products/important");

								JSONObject respJSON = new JSONObject(respStr);								
								try {
									respJSON = new JSONObject(respStr);

									int id = respJSON.getInt("id");
									String name = respJSON.getString("name");
									double rrp = respJSON.getDouble("rrp");
									String summary = respJSON.getString("summary");
									int stock = respJSON.getInt("stock");
									String feature = respJSON.getString("feature");
									String imageName = respJSON.getString("image_name");
									int category_id = respJSON.getInt("category_id");

									product = new Product(id, name, rrp,summary, stock, feature,imageName, category_id);


								} catch (JSONException e) {
									e.printStackTrace();
								}
								return product;
							}
						});
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(future);
		Product product = new Product();

		try {
			product = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
			return  product;
		}
	}

	/**
	 * Find all the products by categories
	 * @param id_category Category's ID
	 * @return List with all the products
	 */
	@SuppressWarnings("finally")
	public List<Product> findByCategory(final int id_category){
		FutureTask<List<Product>> future = 
				new FutureTask<List<Product>>(
						new Callable<List<Product>>() {
							@Override
							public List<Product> call() throws Exception {
								List<Product> listProducts = new ArrayList<Product>();
								String respStr = RequestHttp.requestGET(Crudable.URL+"categories/"+id_category+"/products");
								Log.i("url", Crudable.URL+"categories/"+id_category);

								JSONArray respJSON;
								try {
									respJSON = new JSONArray(respStr);

									for(int i=0; i<respJSON.length(); i++){
										JSONObject obj = respJSON.getJSONObject(i);

										int id = obj.getInt("id");
										String name = obj.getString("name");
										double rrp = obj.getDouble("rrp");
										String summary = obj.getString("summary");
										int stock = obj.getInt("stock");
										String feature = obj.getString("feature");
										String imageName = obj.getString("image_name");
										int category_id = obj.getInt("category_id");

										listProducts.add(new Product(id, name, rrp,summary, stock, feature,imageName, category_id));
									}

								} catch (JSONException e) {
									e.printStackTrace();
								}
								return listProducts;
							}
						});
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(future);
		List<Product> listProducts = new ArrayList<Product>();

		try {

			listProducts = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
			return  listProducts;
		}
	}
	
	/**
	 * Return latest products
	 * @return List with all the latest products
	 */
	@SuppressWarnings("finally")
	public static List<Product> latest(){
		FutureTask<List<Product>> future = 
				new FutureTask<List<Product>>(
						new Callable<List<Product>>() {
							@Override
							public List<Product> call() throws Exception {
								List<Product> listProducts = new ArrayList<Product>();
								String respStr = RequestHttp.requestGET(Crudable.URL+"/products/latest");

								JSONArray respJSON;
								try {
									respJSON = new JSONArray(respStr);

									for(int i=0; i<respJSON.length(); i++){
										JSONObject obj = respJSON.getJSONObject(i);

										int id = obj.getInt("id");
										String name = obj.getString("name");
										double rrp = obj.getDouble("rrp");
										String summary = obj.getString("summary");
										int stock = obj.getInt("stock");
										String feature = obj.getString("feature");
										String imageName = obj.getString("image_name");
										int category_id = obj.getInt("category_id");

										listProducts.add(new Product(id, name, rrp,summary, stock, feature,imageName, category_id));
									}

								} catch (JSONException e) {
									e.printStackTrace();
								}
								return listProducts;
							}
						});
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(future);
		List<Product> listProducts = new ArrayList<Product>();

		try {

			listProducts = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
			return  listProducts;
		}
	}

	/**
	 * Search all the products that coincide with the query
	 * @param query Query
	 * @return List with all the products found
	 */
	@SuppressWarnings("finally")
	public static List<Product> search(final String query){
		FutureTask<List<Product>> future = 
				new FutureTask<List<Product>>(
						new Callable<List<Product>>() {
							@Override
							public List<Product> call() throws Exception {
								List<Product> listProducts = new ArrayList<Product>();
								JSONObject data = new JSONObject();
								data.put("query", query);
								String respStr = RequestHttp.requestPOST(Crudable.URL+"products/search",data.toString());
								
								JSONArray respJSON;
								try {
									respJSON = new JSONArray(respStr);

									for(int i=0; i<respJSON.length(); i++){
										JSONObject obj = respJSON.getJSONObject(i);

										int id = obj.getInt("id");
										String name = obj.getString("name");
										double rrp = obj.getDouble("rrp");
										String summary = obj.getString("summary");
										int stock = obj.getInt("stock");
										String feature = obj.getString("feature");
										String imageName = obj.getString("image_name");
										int category_id = obj.getInt("category_id");

										listProducts.add(new Product(id, name, rrp,summary, stock, feature,imageName, category_id));
									}

								} catch (JSONException e) {
									e.printStackTrace();
								}
								return listProducts;
							}
						});
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(future);
		List<Product> listProducts = new ArrayList<Product>();

		try {

			listProducts = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			Log.i("size", listProducts.size()+"");
			executor.shutdown();
			return  listProducts;
		}
	}

}
