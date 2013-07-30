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
import com.dmontielfdez.gadgetonadmin.model.Order;
import com.dmontielfdez.gadgetonadmin.model.Product;
import com.dmontielfdez.gadgetonadmin.util.RequestHttp;

public class OrderCRUD implements Crudable<Order> {

	@Override
	public String insert(Order t) {
		String result="";

		JSONObject data = new JSONObject(); 

		try {
			data.put("date_order", t.getDate());
			data.put("state", t.getState());
			data.put("method_payment", t.getMethodPay());
			data.put("total", t.getTotal());
			data.put("customer_id", t.getCustomerId());
			
			String respStr = RequestHttp.requestPOST(Crudable.URL+"orders", data.toString());

			JSONObject resultData = new JSONObject(respStr);
			
			result = resultData.toString();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String update(Order t) {
		String result="";

		JSONObject data = new JSONObject(); 

		try {
			data.put("state", t.getState());

			String respStr = RequestHttp.requestPUT(Crudable.URL+"orders/"+t.getId(), data.toString());

			JSONObject resultData = new JSONObject(respStr);

			result = resultData.toString();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String delete(Order t) {
		String result = RequestHttp.requestDELETE(Crudable.URL+"orders/"+t.getId());
		return result;
	}

	@SuppressWarnings("finally")
	@Override
	public List<Order> findAll() {
		FutureTask<List<Order>> future = 
				new FutureTask<List<Order>>(
						new Callable<List<Order>>() {
							@Override
							public List<Order> call() throws Exception {
								List<Order> listOrder = new ArrayList<Order>();
								String respStr = RequestHttp.requestGET(Crudable.URL+"orders/");

								JSONArray respJSON;
								try {
									respJSON = new JSONArray(respStr);

									for(int i=0; i<respJSON.length(); i++){
										JSONObject obj = respJSON.getJSONObject(i);

										int id = obj.getInt("id");
										String date = obj.getString("date");
										String state = obj.getString("state");
										String methodPay = obj.getString("method_payment");
										double total = obj.getDouble("total");
										int customerId = obj.getInt("customer_id");

										listOrder.add(new Order(id, date, state, methodPay, total, customerId));
									}

								} catch (JSONException e) {
									e.printStackTrace();
								}
								return listOrder;
							}
						});
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(future);
		List<Order> listOrder = new ArrayList<Order>();

		try {

			listOrder = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
			return  listOrder;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public Order findByPK(final String pkvalue) {
		FutureTask<Order> future = 
				new FutureTask<Order>(
						new Callable<Order>() {
							@Override
							public Order call() throws Exception {
								Order order = null;
								String respStr = RequestHttp.requestGET(Crudable.URL+"orders/"+pkvalue);

								JSONObject respJSON = new JSONObject(respStr);								
								try {
									respJSON = new JSONObject(respStr);

									int id = respJSON.getInt("id");
									String date = respJSON.getString("date");
									String state = respJSON.getString("state");
									String methodPay = respJSON.getString("method_payment");
									double total = respJSON.getDouble("total");
									int customerId = respJSON.getInt("customer_id");

									order = new Order(id, date, state, methodPay, total, customerId);


								} catch (JSONException e) {
									e.printStackTrace();
								}
								return order;
							}
						});
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(future);
		Order order = new Order();

		try {
			order = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
			return  order;
		}
	}
	
	@SuppressWarnings("finally")
	public static List<Order> findByCustomer(final String pkCustomer) {
		FutureTask<List<Order>> future = 
				new FutureTask<List<Order>>(
						new Callable<List<Order>>() {
							@Override
							public List<Order> call() throws Exception {
								List<Order> listOrders = new ArrayList<Order>();
								String respStr = RequestHttp.requestGET(Crudable.URL+"customers/"+pkCustomer+"/orders");

								JSONArray respJSON;
								try {
									respJSON = new JSONArray(respStr);

									for(int i=0; i<respJSON.length(); i++){
										JSONObject obj = respJSON.getJSONObject(i);

										int id = obj.getInt("id");
										String date = obj.getString("date");
										String state = obj.getString("state");
										String methodPay = obj.getString("method_payment");
										double total = obj.getDouble("total");
										int customerId = obj.getInt("customer_id");

										listOrders.add(new Order(id, date, state, methodPay, total, customerId));
									}

								} catch (JSONException e) {
									e.printStackTrace();
								}
								return listOrders;
							}
						});
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(future);
		List<Order> listOrders = new ArrayList<Order>();

		try {
			listOrders = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
			return  listOrders;
		}
	}

}
