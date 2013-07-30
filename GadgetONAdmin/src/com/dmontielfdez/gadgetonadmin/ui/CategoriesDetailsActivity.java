package com.dmontielfdez.gadgetonadmin.ui;

import com.dmontielfdez.gadgetonadmin.R;
import com.dmontielfdez.gadgetonadmin.ddbb.CategoryCRUD;
import com.dmontielfdez.gadgetonadmin.model.Category;
import com.dmontielfdez.gagdetonadmin.ui.fragments.FragmentCategories;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class CategoriesDetailsActivity extends FragmentActivity {
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);
		
		FragmentCategories details = 
				(FragmentCategories)getSupportFragmentManager()
					.findFragmentById(R.id.FrgDetailsCategories);
		
		int id = getIntent().getExtras().getInt("id");
		int pos = getIntent().getExtras().getInt("pos");
		
		CategoryCRUD categoryCrud = new CategoryCRUD();
		Category c = categoryCrud.findByPK(Integer.toString(id));
		
		details.setCategory(c, pos);
		
	}
}
