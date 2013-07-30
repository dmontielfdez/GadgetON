package com.dmontielfdez.gadgetonadmin.ui;

import com.dmontielfdez.gadgetonadmin.R;
import com.dmontielfdez.gadgetonadmin.ddbb.CustomerCRUD;
import com.dmontielfdez.gadgetonadmin.model.Category;
import com.dmontielfdez.gadgetonadmin.model.Customer;
import com.dmontielfdez.gagdetonadmin.ui.fragments.FragmentCategories;
import com.dmontielfdez.gagdetonadmin.ui.fragments.FragmentCustomers;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class CustomersDetailsActivity extends FragmentActivity {
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customers);
		
		FragmentCustomers details = 
				(FragmentCustomers)getSupportFragmentManager()
					.findFragmentById(R.id.FrgDetailsCustomers);
		
		int id = getIntent().getExtras().getInt("id");
		Log.i("id", id+"");
		CustomerCRUD customerCRUD = new CustomerCRUD();
		Customer c = customerCRUD.findByPK(Integer.toString(id));
		
		details.setCustomer(c);
		
	}
}
