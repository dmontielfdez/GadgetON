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

import android.util.Log;

import com.dmontielfdez.gadgetonadmin.model.Customer;
import com.dmontielfdez.gadgetonadmin.model.Product;
import com.dmontielfdez.gadgetonadmin.util.RequestHttp;

public class CustomerCRUD implements Crudable<Customer>{

	@Override
	public String insert(final Customer c) {
		String result="";

		JSONObject data = new JSONObject(); 

		try {
			data.put("name", c.getName());
			data.put("surname", c.getSurname());
			data.put("birthday", c.getBirthday());
			data.put("address", c.getAddress());
			data.put("province", c.getProvince());
			data.put("postalcode", c.getPostalCode());
			data.put("phone", c.getPhone());
			data.put("email", c.getEmail());
			data.put("password", c.getPassword());

			String respStr = RequestHttp.requestPOST(Crudable.URL+"customers", data.toString());

			JSONObject resultData = new JSONObject(respStr);

			result = resultData.getString("result");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String update(Customer c) {
		String result="";

		JSONObject data = new JSONObject(); 

		try {
			data.put("id",c.getId());
			data.put("name", c.getName());
			data.put("surname", c.getSurname());
			data.put("birthday", c.getBirthday());
			data.put("address", c.getAddress());
			data.put("province", c.getProvince());
			data.put("postalcode", c.getPostalCode());
			data.put("phone", c.getPhone());
			data.put("email", c.getEmail());
			data.put("password", c.getPassword());
			
			Log.i("res", data.toString());

			String respStr = RequestHttp.requestPUT(Crudable.URL+"customers", data.toString());

			JSONObject resultData = new JSONObject(respStr);

			result = resultData.getString("result");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String delete(Customer c) {
		// TODO Auto-generated method stub
		return "";
	}

	@SuppressWarnings("finally")
	@Override
	public ArrayList<Customer> findAll() {
		FutureTask<ArrayList<Customer>> future = 
				new FutureTask<ArrayList<Customer>>(
						new Callable<ArrayList<Customer>>() {
							@Override
							public ArrayList<Customer> call() throws Exception {
								ArrayList<Customer> listCustomer = new ArrayList<Customer>();
								String respStr = RequestHttp.requestGET(Crudable.URL+"customers/");

								JSONArray respJSON;
								try {
									respJSON = new JSONArray(respStr);

									for(int i=0; i<respJSON.length(); i++){
										JSONObject obj = respJSON.getJSONObject(i);

										int id = obj.getInt("id");
										String name = obj.getString("name");
										String surname = obj.getString("surname");
										String birthday = obj.getString("birthday");
										String address = obj.getString("address");
										String province = obj.getString("province");
										String postalCode = obj.getString("postalcode");
										String phone = obj.getString("phone");
										String email = obj.getString("email");
										String password = obj.getString("password");
										String imageName = obj.getString("image_name");

										listCustomer.add(new Customer(id, name, surname, birthday, address, province, postalCode, phone, email, password,imageName));
									}
								}
								catch (Exception e) {
									// TODO: handle exception
								}

								return listCustomer;
							}
						});
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(future);
		ArrayList<Customer> customer = new ArrayList<Customer>();

		try {
			customer = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
			return  customer;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public Customer findByPK(final String pkvalue) {
		FutureTask<Customer> future = 
				new FutureTask<Customer>(
						new Callable<Customer>() {
							@Override
							public Customer call() throws Exception {
								Customer customer = null;
								String respStr = RequestHttp.requestGET(Crudable.URL+"customers/"+pkvalue);

								JSONObject respJSON = new JSONObject(respStr);								
								try {
									respJSON = new JSONObject(respStr);

									int id = respJSON.getInt("id");
									String name = respJSON.getString("name");
									String surname = respJSON.getString("surname");
									String birthday = respJSON.getString("birthday");
									String address = respJSON.getString("address");
									String province = respJSON.getString("province");
									String postalCode = respJSON.getString("postalcode");
									String phone = respJSON.getString("phone");
									String email = respJSON.getString("email");
									String password = respJSON.getString("password");
									String imageName = respJSON.getString("image_name");


									customer = new Customer(id, name, surname, birthday, address, province, postalCode, phone, email, password,imageName);


								} catch (JSONException e) {
									e.printStackTrace();
								}
								return customer;
							}
						});
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(future);
		Customer customer = new Customer();

		try {
			customer = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
			return  customer;
		}
	}
	
	public static void setImageName(final int id, String imageName) {
		JSONObject data = new JSONObject(); 

		try {
			data.put("image_name", imageName);

			RequestHttp.requestPOST(Crudable.URL+"customers/imageName/"+id, data.toString());

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
