package com.dmontielfdez.gagdetonadmin.ui.fragments;

import java.util.ArrayList;
import java.util.Arrays;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.OnNavigationListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dmontielfdez.gadgetonadmin.R;
import com.dmontielfdez.gadgetonadmin.ddbb.OrderCRUD;
import com.dmontielfdez.gadgetonadmin.model.Category;
import com.dmontielfdez.gadgetonadmin.model.Order;
import com.dmontielfdez.gadgetonadmin.ui.OrdersDetailsActivity;
import com.dmontielfdez.gadgetonadmin.ui.adapters.OrderAdapter;

public class FragmentListOrders extends Fragment {

	private ListView lstListado;
	OrderCRUD orderCRUD;
	static ArrayList<Order> listOrder, listOriginal;
	static OrderAdapter adapter;


	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, 
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.fragment_list_orders, container, false);
	}

	@SuppressLint("NewApi")
	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);

		ActionBar actionBar = getActivity().getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		lstListado = (ListView)getView().findViewById(R.id.listview_orders);
		orderCRUD = new OrderCRUD();
		listOriginal = (ArrayList<Order>) orderCRUD.findAll();
		listOrder = new ArrayList<Order>();
		adapter = new OrderAdapter(getActivity(),listOrder);
		lstListado.setAdapter(adapter);

		lstListado.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list, View view, int pos, long id) {
				Order o = adapter.getItem(pos);

				FragmentOrders fragment= (FragmentOrders) getFragmentManager().findFragmentById(R.id.FrgDetailsOrders);
				if (fragment != null && fragment.isInLayout()) {
					fragment.setOrder(o, pos);
				} else {
					Intent intent = new Intent(getActivity().getApplicationContext(), OrdersDetailsActivity.class);
					intent.putExtra("id", o.getId());
					intent.putExtra("pos", pos);
					startActivity(intent);
				}

			}
		});
		
		ArrayList<String> listStates = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.statesActionBar)));
		
		AdapterSpinnerActionBar adapterSpinner = new AdapterSpinnerActionBar(getActivity(),listStates);
		adapterSpinner.setDropDownViewResource(R.layout.custom_list_item);

		actionBar.setListNavigationCallbacks(adapterSpinner, new OnNavigationListener() {

			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				switch (itemPosition) {
				case 0:
					
					listOrder = (ArrayList<Order>) listOriginal.clone();
					adapter = new OrderAdapter(getActivity(),listOrder);
					lstListado.setAdapter(adapter);
					break;
				case 1:
					listOrder.clear();
					for (Order o : listOriginal) {
						if (o.getState().equals("Preparando")) {
							listOrder.add(o);
						}
					}
					adapter = new OrderAdapter(getActivity(),listOrder);
					lstListado.setAdapter(adapter);

					break;
				case 2:
					listOrder.clear();
					for (Order o : listOriginal) {
						if (o.getState().equals("Listo para enviar")) {
							listOrder.add(o);
						}
					}
					adapter = new OrderAdapter(getActivity(),listOrder);
					lstListado.setAdapter(adapter);
					break;
				case 3:
					listOrder.clear();
					for (Order o : listOriginal) {
						if (o.getState().equals("Enviado")) {
							listOrder.add(o);
						}
					}
					adapter = new OrderAdapter(getActivity(),listOrder);
					lstListado.setAdapter(adapter);
					break;
				case 4:
					listOrder.clear();
					for (Order o : listOriginal) {
						if (o.getState().equals("Recibido")) {
							listOrder.add(o);
						}
					}
					adapter = new OrderAdapter(getActivity(),listOrder);
					lstListado.setAdapter(adapter);
					break;

				default:
					break;
				}
				return false;


			}

		});
	}
	
	class AdapterSpinnerActionBar extends ArrayAdapter<String> {

		ArrayList<String> list;
		Activity context;

		public AdapterSpinnerActionBar(Activity context, ArrayList<String> l) {
			super(context, android.R.layout.simple_spinner_dropdown_item , l);
			list = l;
			this.context = context;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			LayoutInflater inflater = context.getLayoutInflater();
			String s = list.get(position);

			v = inflater.inflate(android.R.layout.simple_list_item_1, null);

			TextView txt = (TextView) v.findViewById(android.R.id.text1);
			txt.setText(s);
			txt.setTextColor(Color.parseColor("#EEEEEE"));


			return v;
		}

	}
}