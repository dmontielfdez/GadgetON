package com.dmontielfdez.gadgetonadmin.ui.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dmontielfdez.gadgetonadmin.R;
import com.dmontielfdez.gadgetonadmin.ddbb.Crudable;
import com.dmontielfdez.gadgetonadmin.ddbb.ProductCRUD;
import com.dmontielfdez.gadgetonadmin.model.Line;
import com.dmontielfdez.gadgetonadmin.model.Product;
import com.dmontielfdez.gadgetonadmin.util.ImageLoader;
import com.dmontielfdez.gadgetonadmin.util.Utils;



public class AdapterLine extends ArrayAdapter<Line> {

	ArrayList<Line> list;
	Activity context;
	
	SeekBar seekbar;

	public AdapterLine(Activity context, ArrayList<Line> l) {
		super(context, R.layout.line_item , l);
		list = l;
		this.context = context;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		LayoutInflater inflater = context.getLayoutInflater();

		v = inflater.inflate(R.layout.line_item, null);

		final Line l = list.get(position);

		ProductCRUD productCRUD = new ProductCRUD();
		Log.i("id_prodcut", l.getProduct_id()+"");
		final Product p = productCRUD.findByPK(Integer.toString(l.getProduct_id()));

		ImageView image = (ImageView) v.findViewById(R.id.image_product);

		ImageLoader imageLoader=new ImageLoader(context);
		imageLoader.DisplayImage(Crudable.URL+"products_img/"+p.getImageName(), image);

		TextView name = (TextView) v.findViewById(R.id.name_product);
		name.setText(p.getName());

		TextView rrp = (TextView) v.findViewById(R.id.rrp_product);
		rrp.setText(Double.toString(p.getRrp())+"€");

		final TextView subtotal = (TextView) v.findViewById(R.id.subtotal);
		subtotal.setText(Utils.numberFormat(p.getRrp()*l.getQuantity(),2)+"€");

		TextView quantity = (TextView) v.findViewById(R.id.quantity);
		quantity.setText(Integer.toString(l.getQuantity()));




		return v;
	}






}
