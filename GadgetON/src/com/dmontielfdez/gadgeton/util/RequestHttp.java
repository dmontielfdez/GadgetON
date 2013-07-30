package com.dmontielfdez.gadgeton.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * This class allows request HTTP
 * @author dmontielfdez
 *
 */
public class RequestHttp {

	/**
	 * Open a connection HTTP and makes a request GET
	 * @param url URL server
	 * @return Response of request 
	 */
	public static String requestGET(String url){

		String respStr="";

		HttpClient httpClient = new DefaultHttpClient();

		HttpGet get = new HttpGet(url);

		get.setHeader("content-type", "application/json");

		try {
			HttpResponse resp = httpClient.execute(get);
			respStr = EntityUtils.toString(resp.getEntity());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return respStr;

	}

	/**
	 * Open a connection HTTP and makes a request POST
	 * @param url URL server
	 * @param data Data to send in the connection
	 * @return Response of request 
	 */
	public static String requestPOST(String url, String data){

		String respStr = "";

		try {

			HttpClient httpClient = new DefaultHttpClient();

			HttpPost post = new HttpPost(url);

			post.setHeader("content-type", "application/json");

			StringEntity entity = new StringEntity(data, HTTP.UTF_8);
			entity.setContentEncoding(new BasicHeader(HTTP.UTF_8, "application/json"));
			
			post.setEntity(entity);	

			HttpResponse resp = httpClient.execute(post);
			respStr = EntityUtils.toString(resp.getEntity());

			Log.i("resp", respStr);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return respStr;

	}

	/**
	 * Open a connection HTTP and makes a request PUT
	 * @param url URL server
	 * @param data Data to send in the connection
	 * @return Response of request 
	 */
	public static String requestPUT(String url, String data) {
		String respStr="";

		HttpClient httpClient = new DefaultHttpClient();

		HttpPut put = new HttpPut(url);
		put.setHeader("content-type", "application/json");

		try
		{
			
			StringEntity entity = new StringEntity(data, HTTP.UTF_8);
			entity.setContentEncoding(new BasicHeader(HTTP.UTF_8, "application/json"));
			put.setEntity(entity);

			HttpResponse resp = httpClient.execute(put);
			respStr = EntityUtils.toString(resp.getEntity());
			

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

		return respStr;
	}

	/**
	 * Open a connection HTTP and makes a request DELETE
	 * @param url URL server
	 * @return Response of request 
	 */
	public static String requestDELETE(String url){
		String respStr = null;
		HttpClient httpClient = new DefaultHttpClient();

		HttpDelete del =
				new HttpDelete(url);

		del.setHeader("content-type", "application/json");

		try {
			HttpResponse resp = httpClient.execute(del);
			respStr = EntityUtils.toString(resp.getEntity());

		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		return respStr;
	}


}
