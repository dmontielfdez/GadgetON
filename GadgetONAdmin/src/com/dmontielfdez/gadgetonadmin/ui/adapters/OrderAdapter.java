package com.dmontielfdez.gadgetonadmin.ui.adapters;

import java.util.ArrayList;
import java.util.List;

import com.dmontielfdez.gadgetonadmin.model.Order;
import com.dmontielfdez.gadgetonadmin.model.Product;
import com.dmontielfdez.gadgetonadmin.util.Utils;
import com.dmontielfdez.gadgetonadmin.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class OrderAdapter extends ArrayAdapter<Order> implements Filterable {

	private List<Order> orderList;
	private Activity context;
	private List<Order> origOrderList;

	public OrderAdapter(Activity ctx,List<Order> productList) {
		super(ctx, R.layout.order_item, productList);
		this.orderList = productList;
		this.context = ctx;
		this.origOrderList = productList;
	}

	public int getCount() {
		return orderList.size();
	}

	public Order getItem(int position) {
		return orderList.get(position);
	}

	public long getItemId(int position) {
		return orderList.get(position).hashCode();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		OrderHolder holder = new OrderHolder();

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.order_item, null);

			TextView order = (TextView) v.findViewById(R.id.text_order);
			TextView stateOrder = (TextView) v.findViewById(R.id.state_order);
			TextView id = (TextView) v.findViewById(R.id.id_order);
			TextView total = (TextView) v.findViewById(R.id.total_order);

			holder.orderName = order;
			holder.stateOrder = stateOrder;
			holder.id = id;
			holder.total = total;

			v.setTag(holder);
		}
		else 
			holder = (OrderHolder) v.getTag();

		Order o = orderList.get(position);
		
		holder.id.setText("ID: "+o.getId());
		holder.orderName.setText(Utils.formatDate(o.getDate()) + " - ");
		holder.stateOrder.setText(o.getState());
		holder.total.setText(o.getTotal()+"€");
		
		if (o.getState().equals("Preparando")) {
			holder.stateOrder.setBackgroundColor(context.getResources().getColor(R.color.yellow));
			//holder.state.setBackgroundColor(context.getResources().getColor(R.color.yellow_dark));
		} else if(o.getState().equals("Listo para enviar")){
			holder.stateOrder.setBackgroundColor(context.getResources().getColor(R.color.violet));
			//holder.state.setBackgroundColor(context.getResources().getColor(R.color.violet_dark));
		} else if(o.getState().equals("Enviado")){
			holder.stateOrder.setBackgroundColor(context.getResources().getColor(R.color.blue));
			//holder.state.setBackgroundColor(context.getResources().getColor(R.color.blue_dark));
		} else if(o.getState().equals("Recibido")){
			holder.stateOrder.setBackgroundColor(context.getResources().getColor(R.color.green));
			//holder.state.setBackgroundColor(context.getResources().getColor(R.color.green_dark));
		}
		

		return v;
	}

	public void resetData() {
		orderList = origOrderList;
	}


	private static class OrderHolder {
		public TextView orderName, stateOrder;
		public static TextView id;
		public TextView total;
	}
}
