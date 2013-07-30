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
import com.dmontielfdez.gadgeton.ddbb.CategoryCRUD;
import com.dmontielfdez.gadgeton.model.Category;

public class CategoryActivity extends SherlockActivity {

	ListView listview; 
	AdapterCategories adapter;
	CategoryCRUD categoryCRUD;
	ArrayList<Category> listCategory;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		setTitle("Categorias");
		categoryCRUD = new CategoryCRUD();

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		listview = (ListView) findViewById(R.id.listView);

		listCategory = (ArrayList<Category>) categoryCRUD.findAll();

		adapter = new AdapterCategories(CategoryActivity.this, listCategory);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(CategoryActivity.this, ProductsActivity.class);
				intent.putExtra("id", listCategory.get(arg2).getId());
				intent.putExtra("name", listCategory.get(arg2).getName());
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
