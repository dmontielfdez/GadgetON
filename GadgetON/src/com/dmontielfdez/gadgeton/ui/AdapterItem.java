package com.dmontielfdez.gadgeton.ui;


import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmontielfdez.gadgeton.R;
import com.dmontielfdez.gadgeton.model.Item;

public class AdapterItem extends ArrayAdapter<Item> {
  private final Context context;
  ArrayList<Item> listItem;

  public AdapterItem(Context context, ArrayList<Item> listItem){
    super(context, R.layout.item, listItem);
    this.context = context;
    this.listItem = listItem;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.item, parent, false);
    TextView textView = (TextView) rowView.findViewById(R.id.textView1);
    ImageView imageView = (ImageView) rowView.findViewById(R.id.item_logo);
    
    Item i = listItem.get(position);
    
    textView.setText(i.getName());
    imageView.setImageDrawable(context.getResources().getDrawable(i.getImage()));

    return rowView;
  }
}