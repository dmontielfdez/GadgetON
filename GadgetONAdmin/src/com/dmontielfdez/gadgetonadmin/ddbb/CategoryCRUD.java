package com.dmontielfdez.gadgetonadmin.ddbb;

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

import android.content.Context;
import android.widget.Toast;

import com.dmontielfdez.gadgetonadmin.util.*;
import com.dmontielfdez.gadgetonadmin.model.Category;
import com.dmontielfdez.gadgetonadmin.model.Product;

public class CategoryCRUD implements Crudable<Category> {
	
	@Override
	public String insert(Category t) {
		String result="";

		JSONObject data = new JSONObject(); 

		try {
			data.put("name", t.getName());
			data.put("feature", t.getFeature());

			String respStr = RequestHttp.requestPOST(Crudable.URL+"categories", data.toString());

			JSONObject resultData = new JSONObject(respStr);

			result = resultData.toString();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String update(Category t) {
		String result="";

		JSONObject data = new JSONObject(); 

		try {
			data.put("id",t.getId());
			data.put("name", t.getName());
			data.put("feature", t.getFeature());

			String respStr = RequestHttp.requestPUT(Crudable.URL+"categories/"+t.getId(), data.toString());

			JSONObject resultData = new JSONObject(respStr);

			result = resultData.toString();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String delete(Category t) {
		String result = RequestHttp.requestDELETE(Crudable.URL+"categories/"+t.getId());
		return result;
	}

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

	@SuppressWarnings("finally")
	@Override
	public Category findByPK(final String pkvalue) {
		FutureTask<Category> future = 
				new FutureTask<Category>(
						new Callable<Category>() {
							@Override
							public Category call() throws Exception {
								Category category = null;
								String respStr = RequestHttp.requestGET(Crudable.URL+"categories/"+pkvalue);

								JSONObject respJSON = new JSONObject(respStr);								
								try {
									respJSON = new JSONObject(respStr);

									int id = respJSON.getInt("id");
									String name = respJSON.getString("name");
									String feature = respJSON.getString("feature");

									category = new Category(id, name, feature);


								} catch (JSONException e) {
									e.printStackTrace();
								}
								return category;
							}
						});
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(future);
		Category category = new Category();

		try {
			category = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
			return  category;
		}
	}

}
