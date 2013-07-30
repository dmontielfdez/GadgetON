package com.dmontielfdez.gadgeton.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;
import com.dmontielfdez.gadgeton.R;
import com.dmontielfdez.gadgeton.ddbb.Crudable;
import com.dmontielfdez.gadgeton.ddbb.ProductCRUD;
import com.dmontielfdez.gadgeton.ddbblocal.Cart;
import com.dmontielfdez.gadgeton.ddbblocal.CrudableCart;
import com.dmontielfdez.gadgeton.ddbblocal.GadgetONOpenHelper;
import com.dmontielfdez.gadgeton.model.Product;
import com.dmontielfdez.gadgeton.util.ImageLoader;

public class ProductActivity extends SherlockActivity {

	int id;
	Button buy;
	Spinner quantity;
	private ShareActionProvider myShareActionProvider;


	Product p;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_product);

		Bundle bundle = getIntent().getExtras();
		id = bundle.getInt("id");

		Log.i("id", id+"");

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		ProductCRUD productCRUD = new ProductCRUD();
		p = productCRUD.findByPK(Integer.toString(id));

		TextView name = (TextView) findViewById(R.id.name_product);
		name.setText(p.getName());

		TextView rrp = (TextView) findViewById(R.id.rrp_product);
		rrp.setText(Double.toString(p.getRrp())+"€");

		ImageView image = (ImageView) findViewById(R.id.image_product);

		ImageLoader imageLoader=new ImageLoader(ProductActivity.this);
		imageLoader.DisplayImage(Crudable.URL+"products_img/"+p.getImageName(), image);

		quantity = (Spinner) findViewById(R.id.quantity);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.quantity, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		quantity.setAdapter(adapter);

		TextView feature = (TextView) findViewById(R.id.feature_product);
		feature.setText(p.getFeature());

		ImageView stock = (ImageView) findViewById(R.id.stock_image);

		if (p.getStock()==0) {
			stock.setImageDrawable(getResources().getDrawable(R.drawable.stock0));
		} else if((p.getStock()>0 && p.getStock()<=10)){
			stock.setImageDrawable(getResources().getDrawable(R.drawable.stock1));
		} else if((p.getStock()>10 && p.getStock()<=20)){
			stock.setImageDrawable(getResources().getDrawable(R.drawable.stock2));
		} else if((p.getStock()>20 && p.getStock()<=30)){
			stock.setImageDrawable(getResources().getDrawable(R.drawable.stock3));
		} else if((p.getStock()>30 && p.getStock()<=40)){
			stock.setImageDrawable(getResources().getDrawable(R.drawable.stock4));
		} else if((p.getStock()>40)){
			stock.setImageDrawable(getResources().getDrawable(R.drawable.stock_full));
		}

		buy = (Button) findViewById(R.id.buy_product);

		buy.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {

				GadgetONOpenHelper openHelper = new GadgetONOpenHelper(getApplicationContext(), "GadgetONDDBB", null, 1);
				CrudableCart connectDDBB = new CrudableCart(openHelper);

				connectDDBB.insert(new Cart(0,Integer.parseInt((String)quantity.getSelectedItem()), id, MainActivity.idCustomer));

				Toast.makeText(getApplicationContext(), getResources().getString(R.string.added_cart), Toast.LENGTH_LONG).show();

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.product, menu);
		
		MenuItem item = menu.findItem(R.id.share);
		myShareActionProvider = (ShareActionProvider)item.getActionProvider();
		myShareActionProvider.setShareHistoryFileName(
				ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
		myShareActionProvider.setShareIntent(createShareIntent());
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, ProductsActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("id", p.getCategory_id());
			startActivity(intent);
			break;
		case R.id.cartMenu:
			startActivity(new Intent(getApplicationContext(),CartActivity.class));
			break;


		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private Intent createShareIntent() {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT, p.getName()+" por sólo "+p.getRrp()+"€ - GadgetON, tus gadgets al alcance de tu mano");
		return shareIntent;
	}



}
