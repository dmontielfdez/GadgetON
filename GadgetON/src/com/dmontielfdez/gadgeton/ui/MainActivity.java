package com.dmontielfdez.gadgeton.ui;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.actionbarsherlock.widget.SearchView.OnQueryTextListener;
import com.dmontielfdez.gadgeton.R;
import com.dmontielfdez.gadgeton.ddbb.Crudable;
import com.dmontielfdez.gadgeton.ddbb.ProductCRUD;
import com.dmontielfdez.gadgeton.model.Item;
import com.dmontielfdez.gadgeton.model.Product;
import com.dmontielfdez.gadgeton.util.ActionBarDrawerToggleCompat;
import com.dmontielfdez.gadgeton.util.ImageLoader;



public class MainActivity extends SherlockActivity implements AdapterView.OnItemClickListener, OnQueryTextListener{

	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private DrawerLayout mDrawer;
	private ListView mDrawerOptions;
	private ActionBarDrawerToggleCompat mToggle;

	private SearchView mSearchView;


	static int idCustomer;
	static ArrayList<Product> listProductsSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("GadgetON");

		Bundle bundle = getIntent().getExtras();
		idCustomer = bundle.getInt("idCustomer");

		// DrawerLayout
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mDrawerOptions = (ListView) findViewById(R.id.left_drawer);
		mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

		ArrayList<Item> listItem = new ArrayList<Item>();
		listItem.add(new Item(R.drawable.category_icon, getResources().getString(R.string.categories)));
		listItem.add(new Item(R.drawable.cart_icon, getResources().getString(R.string.cart)));
		listItem.add(new Item(R.drawable.user_icon, getResources().getString(R.string.profile)));
		listItem.add(new Item(R.drawable.map, "Localizacion"));
		listItem.add(new Item(R.drawable.logout_icon, getResources().getString(R.string.logout)));

		mDrawerOptions.setAdapter(new AdapterItem(getApplicationContext(), listItem));
		mDrawerOptions.setOnItemClickListener(this);

		mToggle = new ActionBarDrawerToggleCompat(this, mDrawer, R.drawable.ic_drawer, R.string.menu, R.string.app_name){
			@SuppressLint("NewApi")
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
				invalidateOptionsMenu();
			}

			@SuppressLint("NewApi")
			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(getResources().getString(R.string.menu));
				invalidateOptionsMenu();
			}
		};

		mToggle.setDrawerIndicatorEnabled(true);
		mDrawer.setDrawerListener(mToggle);

		// Product Important

		ImageView imageImportant = (ImageView) findViewById(R.id.image_product_important);
		TextView nameProductImportant = (TextView) findViewById(R.id.name_product_important);
		TextView RRPProductImportant = (TextView) findViewById(R.id.rrp_product_important);

		final Product p = ProductCRUD.getProductImportant();

		ImageLoader imageLoader=new ImageLoader(getApplicationContext());
		imageLoader.DisplayImage(Crudable.URL+"products_img/"+p.getImageName(), imageImportant);

		nameProductImportant.setText(p.getName());
		RRPProductImportant.setText(p.getRrp()+"€");

		RelativeLayout layoutImpotant = (RelativeLayout) findViewById(R.id.layout_important);
		layoutImpotant.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
				intent.putExtra("id", p.getId());
				startActivity(intent);
			}
		});
		
		// Latest products
		
		ListView listview = (ListView) findViewById(R.id.listview_latest);
		final ArrayList<Product> listProducts =  (ArrayList<Product>) ProductCRUD.latest();
		
		AdapterProducts adapter = new AdapterProducts(MainActivity.this, listProducts);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(MainActivity.this, ProductActivity.class);
				intent.putExtra("id", listProducts.get(arg2).getId());
				startActivity(intent);
			}
		});

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		switch (i) {
		case 0:
			startActivity(new Intent(MainActivity.this,CategoryActivity.class));
			break;
		case 1:
			Intent intent = new Intent(MainActivity.this,CartActivity.class);
			startActivity(intent);
			break;

		case 2:
			
			Intent intent2 = new Intent(MainActivity.this,ProfileActivity.class);
			startActivity(intent2);
			break;
		case 3:
			Intent intent3 = new Intent(MainActivity.this,LocalizationActivity.class);
			startActivity(intent3);
			break;
		case 4:
			preferences = getSharedPreferences("PreferencesGadgeton",Context.MODE_PRIVATE);
			editor = preferences.edit();

			editor.putString("email", "");
			editor.putString("pwd", "");
			editor.commit();
			finish();
			stopService(new Intent(MainActivity.this, PromotionService.class));
			startActivity(new Intent(MainActivity.this, LoginActivity.class));
			break;

		default:
			break;
		}
		mDrawer.closeDrawers();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mToggle.onOptionsItemSelected(item) || item.getItemId() == android.R.id.home) {
			return true;
		}
		
		if (item.getItemId()==R.id.settings) {
			startActivity(new Intent(MainActivity.this, PreferencesActivity.class));
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mToggle.onConfigurationChanged(newConfig);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		final MenuItem searchItem = menu.findItem(R.id.action_search);

		mSearchView = (SearchView) searchItem.getActionView();
		mSearchView.setQueryHint("Search...");
		mSearchView.setOnQueryTextListener(this);

		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		listProductsSearch = (ArrayList<Product>) ProductCRUD.search(query);
		
		if (listProductsSearch.size()==0) {
			Toast.makeText(getApplicationContext(),getResources().getString(R.string.no_products_found), Toast.LENGTH_LONG).show();
		} else{
			startActivity(new Intent(getApplicationContext(),SearchActivity.class));
		}
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		return false;
	}




}
