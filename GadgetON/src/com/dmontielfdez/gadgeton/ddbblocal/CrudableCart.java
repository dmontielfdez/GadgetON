package com.dmontielfdez.gadgeton.ddbblocal;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dmontielfdez.gadgeton.ddbb.Crudable;

/**
 * Crudable class for cart. Include methods for insert, update, delete or find categories.
 * @author dmontiel
 *
 */
public class CrudableCart implements Crudable<Cart> {

	SQLiteOpenHelper h;
	
	
	public CrudableCart(SQLiteOpenHelper h) {
		this.h = h;
		
	}

	/**
	 * Insert a cart in the DDBB
	 * 
	 * @param Cart to insert
	 * @return Result of the insertion
	 */
	@Override
	public String insert(Cart t) {
		SQLiteDatabase db = h.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put("quantity",t.getQuantity());
		cv.put("product_id", t.getId_product());
		cv.put("customer_id", t.getId_customer());

		db.insert("CART",null,cv);

		return "";

	}

	/**
	 * Update a cart
	 * 
	 * @param Cart to update
	 * @return Result of the upgrade
	 */
	@Override
	public String update(Cart t) {
		SQLiteDatabase db = h.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put("quantity",t.getQuantity());
		cv.put("product_id", t.getId_product());
		cv.put("customer_id", t.getId_customer());

		db.update("CART", cv, "id=?", new String[] {Integer.toString(t.getId())});
		db.close();
		return "";
	}

	/**
	 * Delete a cart
	 * 
	 * @param Cart to delete
	 * @return Result of the elimination
	 */
	@Override
	public String delete(Cart t) {
		SQLiteDatabase db = h.getWritableDatabase();

		String id = Integer.toString(t.getId());
		String[] args = new String[]{id};
		db.delete("CART", "id=?", args);

		db.close();
		return "";
	}
	
	/**
	 * Delete all carts
	 * 
	 * @param idCustomer 
	 * @return Result of the elimination
	 */
	public int deleteAll(String idCustomer) {
		SQLiteDatabase db = h.getWritableDatabase();
		String[] args = new String[]{idCustomer};
		int i = db.delete("CART", "customer_id=?", args);
		return i;
	}

	/**
	 * Find all the carts in the DDBB
	 * 
	 * @return A List with all carts
	 */
	@Override
	public List<Cart> findAll() {
		SQLiteDatabase db = h.getWritableDatabase();
		ArrayList<Cart> r = new ArrayList<Cart>();
		Cursor c = db.query("CART", null, null, null, null, null, null);

		if(c.moveToFirst()) {
			do { 
				r.add(new Cart(c.getInt(0),c.getInt(1), c.getInt(2),c.getInt(3)));
			} while (c.moveToNext());
		}
		db.close();		
		return r;
	}
	
	/**
	 * Find all the carts by a customer
	 * @param idCustomer customer's id
	 * @return A List with all carts
	 */
	public List<Cart> findByCustomer(String idCustomer) {
		SQLiteDatabase db = h.getWritableDatabase();
		ArrayList<Cart> r = new ArrayList<Cart>();
		Cursor c = db.query("CART", null, "customer_id=?", new String[] {idCustomer}, null, null, null);

		if(c.moveToFirst()) {
			do { 
				r.add(new Cart(c.getInt(0),c.getInt(1), c.getInt(2),c.getInt(3)));
			} while (c.moveToNext());
		}
		db.close();		
		return r;
	}

	/**
	 * Find a cart by his primary key
	 * @param pkvalue Cart's primary key
	 * @return Cart found
	 */
	@Override
	public Cart findByPK(String pkvalue) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * Return the quantity of a cart
	 * @param primaryKey cart's id
	 * @return quantity of the cart
	 */
	public int getQuantity(String primaryKey){
		SQLiteDatabase db = h.getWritableDatabase();
		Cursor c = db.query("CART", null, "id=?", new String[] {primaryKey}, null, null, null);
		
		if (c.moveToFirst()) {
			return c.getInt(1);
		}
		db.close();
		return 0;

	}

}
