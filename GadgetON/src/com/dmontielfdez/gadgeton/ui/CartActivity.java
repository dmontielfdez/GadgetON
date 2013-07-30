package com.dmontielfdez.gadgeton.ui;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.dmontielfdez.gadgeton.R;
import com.dmontielfdez.gadgeton.ddbblocal.Cart;
import com.dmontielfdez.gadgeton.ddbblocal.CrudableCart;
import com.dmontielfdez.gadgeton.ddbblocal.GadgetONOpenHelper;
import com.dmontielfdez.gadgeton.model.Line;
import com.dmontielfdez.gadgeton.util.SwipeListViewTouchListener;
import com.dmontielfdez.gadgeton.util.Utils;

public class CartActivity extends SherlockActivity {

	AdapterLine adapter;
	ListView listview;
	ArrayList<Line> listLines;

	GadgetONOpenHelper openHelper;
	CrudableCart connectDDBB;

	static TextView totalview;
	static double total;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		total = 0;

		listLines = new ArrayList<Line>();
		listview = (ListView) findViewById(R.id.listView_line);

		openHelper = new GadgetONOpenHelper(getApplicationContext(), "GadgetONDDBB", null, 1);
		connectDDBB = new CrudableCart(openHelper);

		final ArrayList<Cart> listCart = (ArrayList<Cart>) connectDDBB.findByCustomer(Integer.toString(MainActivity.idCustomer));

		if (listCart.size()==0) {
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.cart_empty), Toast.LENGTH_SHORT).show();
			finish();
		}

		for (Cart cart : listCart) {
			listLines.add(new Line(cart.getId(), 0, cart.getQuantity(), 0, cart.getId_product(), 0));
		}

		adapter = new AdapterLine(CartActivity.this, listLines);
		listview.setAdapter(adapter);

		totalview = (TextView) findViewById(R.id.total);

		Button proceed = (Button) findViewById(R.id.proceed_button);
		proceed.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (listLines.size()==0) {
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.cart_empty), Toast.LENGTH_SHORT).show();
				} else{
					finish();
					Intent intent = new Intent(CartActivity.this,ConfirmCartActivity.class);
					intent.putExtra("totalOrder", total);
					startActivity(intent);
				}

			}
		});

		SwipeListViewTouchListener touchListener =
				new SwipeListViewTouchListener(listview,
						new SwipeListViewTouchListener.OnSwipeCallback() {
					@Override
					public void onSwipeLeft(ListView listView, int [] reverseSortedPositions) {
						listLines.remove(reverseSortedPositions[0]);
						adapter.notifyDataSetChanged();
						total=0;
						connectDDBB.delete(listCart.get(reverseSortedPositions[0]));
					}

					@Override
					public void onSwipeRight(ListView listView, int [] reverseSortedPositions) {
						listLines.remove(reverseSortedPositions[0]);
						adapter.notifyDataSetChanged();
						total=0;
						connectDDBB.delete(listCart.get(reverseSortedPositions[0]));
					}
				},
				true,
				false); 
		listview.setOnTouchListener(touchListener);
		listview.setOnScrollListener(touchListener.makeScrollListener());

		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
				intent.putExtra("id", listCart.get(arg2).getId_product());
				startActivity(intent);
			}
		});

	}

	public static void setTotalCart(double rrp){
		total += rrp;
		totalview.setText(Utils.numberFormat(total, 2)+"€");

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
			return super.onOptionsItemSelected(item);
		}
	}

}
