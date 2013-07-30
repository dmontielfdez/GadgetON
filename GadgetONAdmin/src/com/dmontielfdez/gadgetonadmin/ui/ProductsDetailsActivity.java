package com.dmontielfdez.gadgetonadmin.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.dmontielfdez.gadgetonadmin.R;
import com.dmontielfdez.gadgetonadmin.ddbb.ProductCRUD;
import com.dmontielfdez.gadgetonadmin.model.Product;
import com.dmontielfdez.gagdetonadmin.ui.fragments.FragmentProducts;

public class ProductsDetailsActivity extends FragmentActivity {
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_products);
		
		FragmentProducts details = 
				(FragmentProducts)getSupportFragmentManager()
					.findFragmentById(R.id.FrgDetailsProducts);
		
		int id = getIntent().getExtras().getInt("id");
		int pos = getIntent().getExtras().getInt("pos");
		Log.i("id", id+"");
		ProductCRUD productCRUD = new ProductCRUD();
		
		
		Product p = productCRUD.findByPK(Integer.toString(id));
		
		
		details.setProduct(p,pos);
	
	}
}
