package com.dmontielfdez.gadgeton.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmontielfdez.gadgeton.R;
import com.dmontielfdez.gadgeton.ddbb.Crudable;
import com.dmontielfdez.gadgeton.model.Product;
import com.dmontielfdez.gadgeton.util.ImageLoader;


public class AdapterProducts extends ArrayAdapter<Product> {
	
	ArrayList<Product> list;
	Activity context;
	
	public AdapterProducts(Activity context, ArrayList<Product> l) {
		super(context , R.layout.product_item, l);
		this.list = l;
		this.context = context;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		LayoutInflater inflater = context.getLayoutInflater();
		
		Product p = list.get(position);
		
		v = inflater.inflate(R.layout.product_item, null);
			
		ImageView image = (ImageView) v.findViewById(R.id.image_product);
		
		ImageLoader imageLoader=new ImageLoader(context);
		imageLoader.DisplayImage(Crudable.URL+"products_img/"+p.getImageName(), image);
				
		TextView name = (TextView) v.findViewById(R.id.name_product);
		name.setText(p.getName());
		
		TextView rrp = (TextView) v.findViewById(R.id.rrp_product);
		rrp.setText(Double.toString(p.getRrp())+"€");
		
		TextView summary = (TextView) v.findViewById(R.id.sumary_product);
		summary.setText(p.getSummary());
		
		ImageView stock = (ImageView) v.findViewById(R.id.stock_product);
		
		if (p.getStock()==0) {
			stock.setImageDrawable(context.getResources().getDrawable(R.drawable.stock0));
		} else if((p.getStock()>0 && p.getStock()<=10)){
			stock.setImageDrawable(context.getResources().getDrawable(R.drawable.stock1));
		} else if((p.getStock()>10 && p.getStock()<=20)){
			stock.setImageDrawable(context.getResources().getDrawable(R.drawable.stock2));
		} else if((p.getStock()>20 && p.getStock()<=30)){
			stock.setImageDrawable(context.getResources().getDrawable(R.drawable.stock3));
		} else if((p.getStock()>30 && p.getStock()<=40)){
			stock.setImageDrawable(context.getResources().getDrawable(R.drawable.stock4));
		} else if((p.getStock()>40)){
			stock.setImageDrawable(context.getResources().getDrawable(R.drawable.stock_full));
		}
		
		return v;
	}
	
	
	
	
	

}
