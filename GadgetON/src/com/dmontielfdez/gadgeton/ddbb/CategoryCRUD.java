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

import com.dmontielfdez.gadgeton.model.Category;
import com.dmontielfdez.gadgeton.util.RequestHttp;

/**
 * Crudable class for categories. Include methods for insert, update, delete or find categories.
 * @author dmontiel
 *
 */
public class CategoryCRUD implements Crudable<Category> {

	/**
	 * Insert a category in the DDBB
	 * 
	 * @param Category to insert
	 * @return Result of the insertion
	 */
	@Override
	public String insert(Category t) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Update a category
	 * 
	 * @param Category to update
	 * @return Result of the upgrade
	 */
	@Override
	public String update(Category t) {
		// TODO Auto-generated method stub
		return "";
	}

	/**
	 * Delete a category
	 * 
	 * @param Category to delete
	 * @return Result of the elimination
	 */
	@Override
	public String delete(Category t) {
		// TODO Auto-generated method stub
		return "";
	}

	
	/**
	 * Find all the categories in the DDBB
	 * 
	 * @return A List with all categories
	 */
	@SuppressWarnings("finally")
	@Override
	public List<Category> findAll() {
		FutureTask<List<Category>> future = 
				new FutureTask<List<Category>>(
						new Callable<List<Category>>() {
							@Override
							public List<Category> call() throws Exception {
								List<Category> listCategories = new ArrayList<Category>();

								String respStr = RequestHttp.requestGET(Crudable.URL+"categories");

								JSONArray respJSON;
								try {
									respJSON = new JSONArray(respStr);

									for(int i=0; i<respJSON.length(); i++){
										JSONObject obj = respJSON.getJSONObject(i);

										int id = obj.getInt("id");
										String name = obj.getString("name");
										String feature = obj.getString("feature");

										listCategories.add(new Category(id, name, feature));
									}

								} catch (JSONException e) {
									e.printStackTrace();
								}
								return listCategories;
							}
						});
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(future);
		List<Category> listCategories = new ArrayList<Category>();

		try {

			listCategories = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
			return  listCategories;
		}
	}
	
	/**
	 * Find a category by her primary key
	 * @param pkvalue Category's primary key
	 * @return Category found
	 */
	@Override
	public Category findByPK(String pkvalue) {
		// TODO Auto-generated method stub
		return null;
	}

}
