package com.dmontielfdez.gadgeton.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.dmontielfdez.gadgeton.R;
import com.dmontielfdez.gadgeton.ddbb.ProductCRUD;
import com.dmontielfdez.gadgeton.model.Product;

public class ProductsActivity extends SherlockActivity {

	ListView listview; 
	AdapterProducts adapter;
	ProductCRUD productCRUD;
	ArrayList<Product> listProducts;
	int category_id;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_products);
				
		Bundle bundle = getIntent().getExtras();
		category_id = bundle.getInt("id");
		String name = bundle.getString("name");
		setTitle(name);
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		productCRUD = new ProductCRUD();

		listview = (ListView) findViewById(R.id.listViewProducts);

		listProducts = (ArrayList<Product>) productCRUD.findByCategory(category_id);
		listProducts.size();
		
		if (listProducts.size()==0) {
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_products_found), Toast.LENGTH_LONG).show();
		} else{
			adapter = new AdapterProducts(ProductsActivity.this, listProducts);
			listview.setAdapter(adapter);

			listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					Intent intent = new Intent(ProductsActivity.this, ProductActivity.class);
					intent.putExtra("id", listProducts.get(arg2).getId());
					startActivity(intent);
				}
			});
		}

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, CategoryActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
