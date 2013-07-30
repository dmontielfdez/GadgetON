package com.dmontielfdez.gadgeton.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.dmontielfdez.gadgeton.R;
import com.dmontielfdez.gadgeton.model.Product;

public class SearchActivity extends SherlockActivity {

	ListView listview; 
	AdapterProducts adapter;
	ArrayList<Product> listProducts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_products);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		listview = (ListView) findViewById(R.id.listViewProducts);

		listProducts = MainActivity.listProductsSearch;

		adapter = new AdapterProducts(SearchActivity.this, listProducts);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(SearchActivity.this, ProductActivity.class);
				intent.putExtra("id", listProducts.get(arg2).getId());
				startActivity(intent);
			}
		});


	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("idCustomer", MainActivity.idCustomer);
			startActivity(intent);
			return true;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}