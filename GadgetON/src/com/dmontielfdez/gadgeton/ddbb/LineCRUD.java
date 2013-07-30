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

import com.dmontielfdez.gadgeton.model.Line;
import com.dmontielfdez.gadgeton.util.RequestHttp;

/**
 * Crudable class for lines of orders. Include methods for insert, update, delete or find lines.
 * @author dmontiel
 *
 */
public class LineCRUD implements Crudable<Line> {

	/**
	 * Insert a line in the DDBB
	 * 
	 * @param Line to insert
	 * @return Result of the insertion
	 */
	@Override
	public String insert(Line t) {
		String result="";

		JSONObject data = new JSONObject(); 

		try {
			data.put("RRP", t.getRRP());
			data.put("quantity", t.getQuantity());
			data.put("subtotal", t.getSubtotal());
			data.put("product_id", t.getProduct_id());
			data.put("order_id", t.getOrder_id());

			String respStr = RequestHttp.requestPOST(Crudable.URL+"lines", data.toString());

			JSONObject resultData = new JSONObject(respStr);

			result = resultData.toString();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * Insert all the order's lines
	 * 	 
	 * @param List with all the lines to insert
	 * @return Result of the insertion
	 */
	public String insertAll(ArrayList<Line> listLine) {

		JSONObject obj = new JSONObject();
		JSONArray req = new JSONArray();
		JSONObject data = null;
		String result="";
		try {
			for (Line line : listLine) {

				data = new JSONObject();
				req.put( data );

				data.put("RRP", line.getRRP());
				data.put("quantity", line.getQuantity());
				data.put("subtotal", line.getSubtotal());
				data.put("product_id", line.getProduct_id());
				data.put("order_id", line.getOrder_id());

				obj.put( "lines", req );

			} 

			Log.i("data", obj.toString());

			String respStr = RequestHttp.requestPOST(Crudable.URL+"linesAll", obj.toString());

			JSONObject resultData = new JSONObject(respStr);

			result = resultData.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Update a line
	 * 
	 * @param Line to update
	 * @return Result of the upgrade
	 */
	@Override
	public String update(Line t) {
		// TODO Auto-generated method stub
		return "";
	}

	/**
	 * Delete a line
	 * 
	 * @param Line to delete
	 * @return Result of the elimination
	 */
	@Override
	public String delete(Line t) {
		// TODO Auto-generated method stub
		return "";
	}

	/**
	 * Find all the lines in the DDBB
	 * 
	 * @return A List with all lines
	 */
	@Override
	public List<Line> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Find a line by her primary key
	 * @param pkvalue Lines's primary key
	 * @return Line found
	 */
	@Override
	public Line findByPK(String pkvalue) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Find all the order's line
	 * @param pkOrder Order's id
	 * @return List with all the lines
	 */
	@SuppressWarnings("finally")
	public static List<Line> findByOrder(final String pkOrder) {
		FutureTask<List<Line>> future = 
				new FutureTask<List<Line>>(
						new Callable<List<Line>>() {
							@Override
							public List<Line> call() throws Exception {
								List<Line> listLines = new ArrayList<Line>();
								String respStr = RequestHttp.requestGET(Crudable.URL+"orders/"+pkOrder+"/lines");

								JSONArray respJSON;
								try {
									respJSON = new JSONArray(respStr);

									for(int i=0; i<respJSON.length(); i++){
										JSONObject obj = respJSON.getJSONObject(i);

										int id = obj.getInt("id");
										double rRP = obj.getDouble("rrp");
										int quantity = obj.getInt("quantity");
										double subtotal = obj.getDouble("subtotal");
										int product_id = obj.getInt("product_id");
										int order_id = obj.getInt("order_id");

										listLines.add(new Line(id, rRP, quantity, subtotal, product_id, order_id));
									}

								} catch (JSONException e) {
									e.printStackTrace();
								}
								return listLines;
							}
						});
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(future);
		List<Line> listLines = new ArrayList<Line>();

		try {
			listLines = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
			return  listLines;
		}
	}

}
