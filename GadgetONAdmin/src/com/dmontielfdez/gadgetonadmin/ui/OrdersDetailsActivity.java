package com.dmontielfdez.gadgetonadmin.ui;

import com.dmontielfdez.gadgetonadmin.R;
import com.dmontielfdez.gadgetonadmin.ddbb.OrderCRUD;
import com.dmontielfdez.gadgetonadmin.ddbb.ProductCRUD;
import com.dmontielfdez.gadgetonadmin.model.Category;
import com.dmontielfdez.gadgetonadmin.model.Order;
import com.dmontielfdez.gadgetonadmin.model.Product;
import com.dmontielfdez.gagdetonadmin.ui.fragments.FragmentCategories;
import com.dmontielfdez.gagdetonadmin.ui.fragments.FragmentOrders;
import com.dmontielfdez.gagdetonadmin.ui.fragments.FragmentProducts;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class OrdersDetailsActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orders);

		FragmentOrders details = 
				(FragmentOrders)getSupportFragmentManager()
				.findFragmentById(R.id.FrgDetailsOrders);

		int id = getIntent().getExtras().getInt("id");
		int pos = getIntent().getExtras().getInt("pos");
		Log.i("id", id+"");
		OrderCRUD orderCRUD = new OrderCRUD();

		Order o = orderCRUD.findByPK(Integer.toString(id));

		details.setOrder(o, pos);

	}
}
