package com.dmontielfdez.gagdetonadmin.ui.fragments;

import java.util.ArrayList;

import com.dmontielfdez.gadgetonadmin.ddbb.CategoryCRUD;
import com.dmontielfdez.gadgetonadmin.ddbb.CustomerCRUD;
import com.dmontielfdez.gadgetonadmin.ddbb.ProductCRUD;
import com.dmontielfdez.gadgetonadmin.model.Category;
import com.dmontielfdez.gadgetonadmin.model.Customer;
import com.dmontielfdez.gadgetonadmin.model.Product;
import com.dmontielfdez.gadgetonadmin.R;
import com.dmontielfdez.gadgetonadmin.ui.CategoriesDetailsActivity;
import com.dmontielfdez.gadgetonadmin.ui.CustomersDetailsActivity;
import com.dmontielfdez.gadgetonadmin.ui.ProductsDetailsActivity;
import com.dmontielfdez.gadgetonadmin.ui.adapters.CustomerAdapter;
import com.dmontielfdez.gadgetonadmin.ui.adapters.ProductAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentListCustomers extends Fragment {

	private ListView lstListado;
	CustomerCRUD customerCRUD;
	ArrayList<Customer> listCustomers;
	CustomerAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, 
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_list_customers, container, false);
	}

	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);

		lstListado = (ListView)getView().findViewById(R.id.listview_customers);
		customerCRUD = new CustomerCRUD();
		listCustomers = (ArrayList<Customer>) customerCRUD.findAll();

		adapter = new CustomerAdapter(getActivity(),listCustomers);
		lstListado.setAdapter(adapter);

		lstListado.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list, View view, int pos, long id) {

				Customer c = adapter.getItem(pos);

				FragmentCustomers fragment= (FragmentCustomers) getFragmentManager().findFragmentById(R.id.FrgDetailsCustomers);
				if (fragment != null && fragment.isInLayout()) {
					fragment.setCustomer(c);
				} else {
					Intent intent = new Intent(getActivity().getApplicationContext(), CustomersDetailsActivity.class);
					intent.putExtra("id", c.getId());
					startActivity(intent);
				}

			}
		});

		lstListado.setTextFilterEnabled(true);
		EditText search = (EditText)getView().findViewById(R.id.search_customers);
		search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (count < before) {
					adapter.resetData();
				}

				adapter.getFilter().filter(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});
	}


}
