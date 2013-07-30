package com.dmontielfdez.gagdetonadmin.ui.fragments;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dmontielfdez.gadgetonadmin.R;
import com.dmontielfdez.gadgetonadmin.ddbb.CategoryCRUD;
import com.dmontielfdez.gadgetonadmin.ddbb.Crudable;
import com.dmontielfdez.gadgetonadmin.ddbb.OrderCRUD;
import com.dmontielfdez.gadgetonadmin.model.Category;
import com.dmontielfdez.gadgetonadmin.model.Customer;
import com.dmontielfdez.gadgetonadmin.model.Order;
import com.dmontielfdez.gadgetonadmin.ui.OrdersDetailsActivity;
import com.dmontielfdez.gadgetonadmin.ui.adapters.OrderAdapter;
import com.dmontielfdez.gadgetonadmin.util.ImageLoader;

public class FragmentCustomers extends Fragment {

	TextView name, email, addreess, province;
	ImageButton addPhoto;

	ListView listview;
	OrderAdapter adapter;
	Customer customer;

	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, 
			Bundle savedInstanceState) {

		setHasOptionsMenu(true);

		return inflater.inflate(R.layout.fragment_customers, container, false);
	}

	public void setCustomer(Customer c){
		customer = c;

		name = (TextView) getView().findViewById(R.id.name_profile);
		name.setText(c.getName()+" "+c.getSurname());

		email = (TextView) getView().findViewById(R.id.email_user);
		email.setText(c.getEmail());

		addreess = (TextView) getView().findViewById(R.id.address_user);
		addreess.setText(c.getAddress());

		province = (TextView) getView().findViewById(R.id.province_user);
		province.setText(c.getProvince());

		addPhoto = (ImageButton) getView().findViewById(R.id.add_photo_user);

		final ArrayList<Order> listOrders = (ArrayList<Order>) OrderCRUD.findByCustomer(Integer.toString(c.getId()));
		Log.i("l", listOrders.size()+"");

		listview = (ListView) getView().findViewById(R.id.listView_profile);
		adapter = new OrderAdapter(getActivity(), listOrders);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getActivity(), OrdersDetailsActivity.class);
				intent.putExtra("id", listOrders.get(arg2).getId());
				startActivity(intent);
			}
		});

		ImageLoader imageLoader=new ImageLoader(getActivity());
		imageLoader.DisplayImage(Crudable.URL+"customers_img/"+c.getImageName(), addPhoto);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.customer_details, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.contact_call:

			if (customer!=null) {
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:"+customer.getPhone()));
				startActivity(intent);
			} else{
				Toast.makeText(getActivity(), "Seleccione un cliente", Toast.LENGTH_SHORT).show();
			}

			break;

		case R.id.contact_email:
			if (customer!=null) {
				Intent em = new Intent(android.content.Intent.ACTION_SEND);
				em.setType("plain/text");

				em.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{customer.getEmail()});

				startActivity(Intent.createChooser(em, "Enviar correo electrónico"));
			} else{
				Toast.makeText(getActivity(), "Seleccione un cliente", Toast.LENGTH_SHORT).show();
			}

			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
