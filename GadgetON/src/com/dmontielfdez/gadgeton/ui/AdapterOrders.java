package com.dmontielfdez.gadgeton.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dmontielfdez.gadgeton.R;
import com.dmontielfdez.gadgeton.model.Order;
import com.dmontielfdez.gadgeton.util.Utils;


public class AdapterOrders extends ArrayAdapter<Order> {

	ArrayList<Order> list;
	Activity context;

	public AdapterOrders(Activity context, ArrayList<Order> l) {
		super(context , R.layout.order_item, l);
		this.list = l;
		this.context = context;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		LayoutInflater inflater = context.getLayoutInflater();

		Order o = list.get(position);

		v = inflater.inflate(R.layout.order_item, null);

		TextView date, state, total;

		date = (TextView) v.findViewById(R.id.date_order);
		state = (TextView) v.findViewById(R.id.state_order);
		total = (TextView) v.findViewById(R.id.total_order);

		date.setText(Utils.formatDate(o.getDate()));
		state.setText(o.getState());
		total.setText(o.getTotal()+" €");

		return v;


	}

}
