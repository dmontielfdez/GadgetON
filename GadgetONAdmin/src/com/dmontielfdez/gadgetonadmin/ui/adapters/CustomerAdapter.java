package com.dmontielfdez.gadgetonadmin.ui.adapters;

import java.util.ArrayList;
import java.util.List;

import com.dmontielfdez.gadgetonadmin.model.Customer;
import com.dmontielfdez.gadgetonadmin.model.Product;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class CustomerAdapter extends ArrayAdapter<Customer> implements Filterable {

	private List<Customer> customerList;
	private Activity context;
	private Filter customerFilter;
	private List<Customer> origCustomerList;

	public CustomerAdapter(Activity ctx,List<Customer> customerList) {
		super(ctx, android.R.layout.simple_list_item_1, customerList);
		this.customerList = customerList;
		this.context = ctx;
		this.origCustomerList = customerList;
	}

	public int getCount() {
		return customerList.size();
	}

	public Customer getItem(int position) {
		return customerList.get(position);
	}

	public long getItemId(int position) {
		return customerList.get(position).hashCode();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		ConsumerHolder holder = new ConsumerHolder();

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(android.R.layout.simple_list_item_1, null);
			TextView tv = (TextView) v.findViewById(android.R.id.text1);

			holder.consumerName = tv;

			v.setTag(holder);
		}
		else 
			holder = (ConsumerHolder) v.getTag();

		Customer c = customerList.get(position);
		holder.consumerName.setText(c.getName() + " "+c.getSurname());


		return v;
	}

	public void resetData() {
		customerList = origCustomerList;
	}


	private static class ConsumerHolder {
		public TextView consumerName;
	}

	@Override
	public Filter getFilter() {
		if (customerFilter == null)
			customerFilter = new CustomerFilter();

		return customerFilter;
	}



	private class CustomerFilter extends Filter {



		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			// We implement here the filter logic
			if (constraint == null || constraint.length() == 0) {
				// No filter implemented we return all the list
				results.values = origCustomerList;
				results.count = origCustomerList.size();
			}
			else {
				// We perforCm filtering operation
				List<Customer> nCustomerList = new ArrayList<Customer>();

				for (Customer c : customerList) {
					if (c.getName().toUpperCase().startsWith(constraint.toString().toUpperCase())
							|| c.getSurname().toUpperCase().startsWith(constraint.toString().toUpperCase()))
						nCustomerList.add(c);
				}

				results.values = nCustomerList;
				results.count = nCustomerList.size();

			}
			return results;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {

			// Now we have to inform the adapter about the new list filtered
			if (results.count == 0)
				notifyDataSetInvalidated();
			else {
				customerList = (List<Customer>) results.values;
				notifyDataSetChanged();
			}

		}

	}
}
