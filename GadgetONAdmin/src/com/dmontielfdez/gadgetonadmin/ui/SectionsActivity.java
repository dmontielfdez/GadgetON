package com.dmontielfdez.gadgetonadmin.ui;

import com.dmontielfdez.gadgetonadmin.R;
import com.dmontielfdez.gagdetonadmin.ui.fragments.FragmentProducts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


public class SectionsActivity extends Activity {

	Button category, product, order, customer, scanner, logout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sections);

		category = (Button) findViewById(R.id.button1);
		product = (Button) findViewById(R.id.button2);
		order = (Button) findViewById(R.id.button3);
		customer = (Button) findViewById(R.id.button4);
		scanner = (Button) findViewById(R.id.button5);
		logout = (Button) findViewById(R.id.button6);
	}

	public void clicked(View view){
		switch (view.getId()) {
		case R.id.button1:
			startActivity(new Intent(getApplicationContext(), CategoriesListActivity.class));
			break;
		case R.id.button2:
			startActivity(new Intent(getApplicationContext(), ProductsListActivity.class));
			break;
		case R.id.button3:
			startActivity(new Intent(getApplicationContext(), OrdersListActivity.class));
			break;
		case R.id.button4:
			startActivity(new Intent(getApplicationContext(), CustomersListActivity.class));
			break;
		case R.id.button5:
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);
			break;
		case R.id.button6:
			finish();
			break;

		default:
			break;
		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");

				Intent i = new Intent(getApplicationContext(), ProductsDetailsActivity.class);
				i.putExtra("id", Integer.parseInt(contents));
				startActivity(i);
				// Handle successful scan
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sections, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId()==R.id.settings) {
			startActivity(new Intent(SectionsActivity.this, PreferencesActivity.class));
		}
		return super.onMenuItemSelected(featureId, item);
	}
	
	
	
	

}
