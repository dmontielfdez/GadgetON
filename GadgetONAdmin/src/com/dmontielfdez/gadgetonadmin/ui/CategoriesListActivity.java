package com.dmontielfdez.gadgetonadmin.ui;

import com.dmontielfdez.gadgetonadmin.R;
import com.dmontielfdez.gagdetonadmin.ui.fragments.FragmentListCategories;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.support.v4.app.FragmentActivity;

public class CategoriesListActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categories);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}
}
