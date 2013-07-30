package com.dmontielfdez.gadgeton.ui;

import java.util.ArrayList;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.dmontielfdez.gadgeton.R;
import com.dmontielfdez.gadgeton.ddbb.LineCRUD;
import com.dmontielfdez.gadgeton.ddbb.OrderCRUD;
import com.dmontielfdez.gadgeton.model.Line;
import com.dmontielfdez.gadgeton.model.Order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class DetailsOrderActivity extends SherlockActivity {

	AdapterLineDetails adapter;
	ListView listview;
	ArrayList<Line> listLines;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_details_order);
	    
	    Bundle bundle = getIntent().getExtras();
	    int orderId = bundle.getInt("id");
	    
	    ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    OrderCRUD orderCrud = new OrderCRUD();
	    Order o = orderCrud.findByPK(Integer.toString(orderId));
	    
	    TextView idOrder, dateOrder, methodPaymentOrder, totalOrder, state;
	    
	    idOrder = (TextView) findViewById(R.id.id_order);
	    dateOrder = (TextView) findViewById(R.id.date_order);
	    methodPaymentOrder = (TextView) findViewById(R.id.method_pay_order);
	    totalOrder = (TextView) findViewById(R.id.total_order);
	    state = (TextView) findViewById(R.id.states);
	    
	    idOrder.setText(o.getId()+"");
	    dateOrder.setText(o.getDate());
	    methodPaymentOrder.setText(o.getMethodPay());
	    totalOrder.setText(o.getTotal()+"€");
	    state.setText(o.getState());
	    
	    listLines = (ArrayList<Line>) LineCRUD.findByOrder(Integer.toString(orderId));
		listview = (ListView) findViewById(R.id.listView_lines);
		
		adapter = new AdapterLineDetails(DetailsOrderActivity.this, listLines);
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
				intent.putExtra("id", listLines.get(arg2).getProduct_id());
				startActivity(intent);
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, ProfileActivity.class);
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
