package com.dmontielfdez.gadgeton.ui;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.dmontielfdez.gadgeton.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.os.Bundle;

public class LocalizationActivity extends SherlockFragmentActivity {

	private GoogleMap map;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_localization);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.407849,-5.952863),15));

		map.addMarker(new MarkerOptions()
		.position(new LatLng(37.407849,-5.952863))
		.title("GadgetON!")
		.snippet("Calle Automocion, 15. 41010 - Sevilla")
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher))
				);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.localization, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
			startActivity(intent);
			return true;
			
		case R.id.contact_email:
			Intent em = new Intent(android.content.Intent.ACTION_SEND);
			em.setType("plain/text");
			 
			em.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"contacto@gadgeton.com"});
			 
			startActivity(Intent.createChooser(em, "Enviar correo electrónico"));
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
