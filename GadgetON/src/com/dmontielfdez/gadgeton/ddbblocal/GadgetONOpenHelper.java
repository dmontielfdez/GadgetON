package com.dmontielfdez.gadgeton.ddbblocal;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class GadgetONOpenHelper extends SQLiteOpenHelper {

	String CREATE_TABLE_CART =
			"CREATE TABLE CART (" +
					"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
					"quantity INTEGER, " +
					"product_id INTEGER," +
					"customer_id INTEGER" +
					")";
	

	public GadgetONOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_CART);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS CART");

		onCreate(db);
	}

}
