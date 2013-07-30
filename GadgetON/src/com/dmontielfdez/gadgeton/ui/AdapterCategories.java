package com.dmontielfdez.gadgeton.ui;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dmontielfdez.gadgeton.model.Category;


public class AdapterCategories extends ArrayAdapter<Category> {
	
	ArrayList<Category> list;
	Activity context;
	
	public AdapterCategories(Activity context, ArrayList<Category> l) {
		super(context, android.R.layout.simple_list_item_1 , l);
		list = l;
		this.context = context;
		
	}

	@SuppressLint("DefaultLocale")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		LayoutInflater inflater = context.getLayoutInflater();
		Category c = list.get(position);
		
		v = inflater.inflate(android.R.layout.simple_list_item_1, null);

		TextView txt = (TextView) v.findViewById(android.R.id.text1);
		txt.setText(c.getName().toUpperCase());
		txt.setTextSize(16);
		txt.setTextColor(Color.parseColor("#96aa39"));

		return v;
	}
	
	
	
	
	

}
