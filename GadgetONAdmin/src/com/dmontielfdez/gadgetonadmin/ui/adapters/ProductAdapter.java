package com.dmontielfdez.gadgetonadmin.ui.adapters;

import java.util.ArrayList;
import java.util.List;

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

public class ProductAdapter extends ArrayAdapter<Product> implements Filterable {

	private List<Product> productList;
	private Activity context;
	private Filter productFilter;
	private List<Product> origProductList;
	
	public ProductAdapter(Activity ctx,List<Product> productList) {
		super(ctx, android.R.layout.simple_list_item_1, productList);
		this.productList = productList;
		this.context = ctx;
		this.origProductList = productList;
	}
	
	public int getCount() {
		return productList.size();
	}

	public Product getItem(int position) {
		return productList.get(position);
	}

	public long getItemId(int position) {
		return productList.get(position).hashCode();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		
		ProductHolder holder = new ProductHolder();
		
		// First let's verify the convertView is not null
		if (convertView == null) {
			// This a new view we inflate the new layout
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(android.R.layout.simple_list_item_1, null);
			// Now we can fill the layout with the right values
			TextView tv = (TextView) v.findViewById(android.R.id.text1);

			holder.productName = tv;
			
			v.setTag(holder);
		}
		else 
			holder = (ProductHolder) v.getTag();
		
		Product p = productList.get(position);
		holder.productName.setText(p.getName());
		
		
		return v;
	}

	public void resetData() {
		productList = origProductList;
	}
	
	
	/* *********************************
	 * We use the holder pattern        
	 * It makes the view faster and avoid finding the component
	 * **********************************/
	
	private static class ProductHolder {
		public TextView productName;
	}
	

	
	/*
	 * We create our filter	
	 */
	
	@Override
	public Filter getFilter() {
		if (productFilter == null)
			productFilter = new PlanetFilter();
		
		return productFilter;
	}



	private class PlanetFilter extends Filter {

		
		
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			// We implement here the filter logic
			if (constraint == null || constraint.length() == 0) {
				// No filter implemented we return all the list
				results.values = origProductList;
				results.count = origProductList.size();
			}
			else {
				// We perform filtering operation
				List<Product> nPlanetList = new ArrayList<Product>();
				
				for (Product p : productList) {
					if (p.getName().toUpperCase().startsWith(constraint.toString().toUpperCase()))
						nPlanetList.add(p);
				}
				
				results.values = nPlanetList;
				results.count = nPlanetList.size();

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
				productList = (List<Product>) results.values;
				notifyDataSetChanged();
			}
			
		}
		
	}
}
