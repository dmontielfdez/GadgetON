package com.dmontielfdez.gagdetonadmin.ui.fragments;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.dmontielfdez.gadgetonadmin.ddbb.LineCRUD;
import com.dmontielfdez.gadgetonadmin.model.Line;
import com.dmontielfdez.gadgetonadmin.ddbb.CustomerCRUD;
import com.dmontielfdez.gadgetonadmin.ddbb.OrderCRUD;
import com.dmontielfdez.gadgetonadmin.ddbb.ProductCRUD;
import com.dmontielfdez.gadgetonadmin.ui.adapters.AdapterLine;
import com.dmontielfdez.gadgetonadmin.ui.adapters.OrderAdapter;
import com.dmontielfdez.gadgetonadmin.util.ImageLoader;
import com.dmontielfdez.gadgetonadmin.R;
import com.dmontielfdez.gadgetonadmin.model.Category;
import com.dmontielfdez.gadgetonadmin.model.Customer;
import com.dmontielfdez.gadgetonadmin.model.Order;
import com.dmontielfdez.gadgetonadmin.model.Product;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentOrders extends Fragment {

	AdapterLine adapter;
	ListView listview;
	ArrayList<Line> listLines;
	String[] listStates;
	int stateOption;

	Order order;
	int pos;

	TextView idOrder, date, methodPay, nameCustomer, total;
	Spinner state;

	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, 
			Bundle savedInstanceState) {

		setHasOptionsMenu(true);
		return inflater.inflate(R.layout.fragment_orders, container, false);
	}

	public void setOrder(Order o, final int pos){
		this.pos = pos;
		order = o;

		listStates = getActivity().getResources().getStringArray(R.array.states);

		for (int i = 0; i < listStates.length; i++) {
			if (listStates[i].equals(o.getState())) {
				stateOption = i;
			} 
		}

		CustomerCRUD customerCRUD = new CustomerCRUD();
		Customer c = customerCRUD.findByPK(Integer.toString(o.getCustomerId()));

		idOrder = (TextView) getView().findViewById(R.id.id_order);
		idOrder.setText(o.getId()+"");

		date = (TextView) getView().findViewById(R.id.date_order);
		date.setText(o.getDate());

		methodPay = (TextView) getView().findViewById(R.id.method_pay_order);
		methodPay.setText(o.getMethodPay());

		nameCustomer = (TextView) getView().findViewById(R.id.customer);
		nameCustomer.setText(c.getName()+" "+c.getSurname());

		total = (TextView) getView().findViewById(R.id.total_order);
		total.setText(o.getTotal()+"€");

		state = (Spinner) getView().findViewById(R.id.states);
		state.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getActivity().getResources().getStringArray(R.array.states)));
		Log.i("estado", stateOption+"");
		
		listLines = (ArrayList<Line>) LineCRUD.findByOrder(Integer.toString(o.getId()));
		listview = (ListView) getView().findViewById(R.id.listView_lines);

		adapter = new AdapterLine(getActivity(), listLines);
		listview.setAdapter(adapter);

		state.setSelection(stateOption);
		state.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {

				new Thread(new Runnable() {
					@Override
					public void run() {
						if (stateOption != arg2) {
							String stateOrder = "";
							if (arg2==0) {
								stateOrder = "Preparando";
							} else if (arg2==1) {
								stateOrder = "Listo para enviar";
							} else if (arg2==2) {
								stateOrder = "Enviado";
							} else if (arg2==3) {
								stateOrder = "Recibido";
							}

							OrderCRUD orderCrud = new OrderCRUD();
							String resultData = orderCrud.update(new Order(order.getId(), "", stateOrder, "", 0, 0));

							JSONObject json;
							try {
								json = new JSONObject(resultData);
								String result = json.getString("result");
								if (result.equals("true")) {
									getActivity().runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(getActivity(), "Estado actualizado", Toast.LENGTH_LONG).show();
											String state = "";
											if (arg2==0) {
												state = "Preparando";
											} else if (arg2==1) {
												state = "Listo para enviar";
											} else if (arg2==2) {
												state = "Enviado";
											} else if (arg2==3) {
												state = "Recibido";
											}
											
											if (FragmentListOrders.listOrder!=null) {
												Order o = FragmentListOrders.listOrder.get(pos);
												if (o!=null) {
													o.setState(state);
													FragmentListOrders.adapter.notifyDataSetChanged();
												}
												
											}
											
										}
									});
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					}
				}).start();


			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});


	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.orders_details, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete:
			if (order!=null) {
				LayoutInflater li = LayoutInflater.from(getActivity());
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

				alertDialogBuilder
				.setMessage("¿Cancelar pedido?")
				.setCancelable(false)
				.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
					@SuppressLint("NewApi")
					public void onClick(DialogInterface dialog, int ida) {

						new Thread(new Runnable() {

							@Override
							public void run() {
								OrderCRUD orderCrud = new OrderCRUD();
								String resultData = orderCrud.delete(order);
								JSONObject json;
								try {
									json = new JSONObject(resultData);
									String result = json.getString("result");
									if (result.equals("true")) {
										getActivity().runOnUiThread(new Runnable() {
											@SuppressLint("ResourceAsColor")
											public void run() {
												FragmentListOrders.listOrder.remove(order);
												FragmentListOrders.adapter.notifyDataSetChanged();

												idOrder.setText("");
												date.setText("");
												methodPay.setText("");
												nameCustomer.setText("");
												total.setText("");
												
												String[] nothing = new String[]{""};
												state.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, nothing));
												
												listLines.clear();
												adapter.notifyDataSetChanged();
											}
										});

									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}).start();	
					}
				})
				.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				});

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			} else{
				Toast.makeText(getActivity(), "Seleccione un pedido", Toast.LENGTH_SHORT).show();
			}

			break;
		}
		return super.onOptionsItemSelected(item);


	}
}
