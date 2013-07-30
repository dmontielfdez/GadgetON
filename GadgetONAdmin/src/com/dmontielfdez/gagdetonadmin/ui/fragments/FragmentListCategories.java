package com.dmontielfdez.gagdetonadmin.ui.fragments;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.dmontielfdez.gadgetonadmin.ddbb.CategoryCRUD;
import com.dmontielfdez.gadgetonadmin.model.Category;
import com.dmontielfdez.gadgetonadmin.R;
import com.dmontielfdez.gadgetonadmin.ui.CategoriesDetailsActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentListCategories extends Fragment {

	private ListView lstListado;
	CategoryCRUD categoryCRUD;
	static ArrayList<Category> listCategory;
	static AdapterCategories adapter;
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, 
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		return inflater.inflate(R.layout.fragment_list_categories, container, false);
	}

	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);
		lstListado = (ListView)getView().findViewById(R.id.listview_categories);
		categoryCRUD = new CategoryCRUD();
		listCategory = (ArrayList<Category>) categoryCRUD.findAll();

		adapter = new AdapterCategories(getActivity(),listCategory);
		lstListado.setAdapter(adapter);

		lstListado.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list, View view, int pos, long id) {
				Category c = listCategory.get(pos);

				FragmentCategories fragment= (FragmentCategories) getFragmentManager().findFragmentById(R.id.FrgDetailsCategories);
				if (fragment != null && fragment.isInLayout()) {
					fragment.setCategory(c, pos);
				} else {
					Intent intent = new Intent(getActivity().getApplicationContext(), CategoriesDetailsActivity.class);
					intent.putExtra("id", c.getId());
					intent.putExtra("pos", pos);
					startActivity(intent);
				}

			}
		});
	}

	class AdapterCategories extends ArrayAdapter<Category> {

		ArrayList<Category> list;
		Activity context;

		public AdapterCategories(Activity context, ArrayList<Category> l) {
			super(context, android.R.layout.simple_list_item_1 , l);
			list = l;
			this.context = context;

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			LayoutInflater inflater = context.getLayoutInflater();
			Category c = list.get(position);

			v = inflater.inflate(android.R.layout.simple_list_item_1, null);

			TextView txt = (TextView) v.findViewById(android.R.id.text1);
			txt.setText(c.getName().toUpperCase());
			txt.setTextSize(16);


			return v;
		}

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.category_list, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.new_content:
			LayoutInflater li = LayoutInflater.from(getActivity());
			View promptsNew = li.inflate(R.layout.prompt_new_category, null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
			alertDialogBuilder.setView(promptsNew);
			final EditText name = (EditText) promptsNew.findViewById(R.id.name_new_category);
			final EditText feature = (EditText) promptsNew.findViewById(R.id.feature_new_category);

			alertDialogBuilder
			.setMessage("Añadir categoria")
			.setCancelable(false)
			.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
				@SuppressLint("NewApi")
				public void onClick(DialogInterface dialog, int ida) {
					if (name.getText().toString().isEmpty()) {
						Toast.makeText(getActivity(), "El nombre de la categoria no puede estar vacio", Toast.LENGTH_LONG).show();
					} else if(feature.getText().toString().isEmpty()){
						Toast.makeText(getActivity(), "Las caracteristicas de la categoria no puede estar vacia", Toast.LENGTH_LONG).show();
					}
					else{
						new Thread(new Runnable() {

							@Override
							public void run() {
								CategoryCRUD categoryCrud = new CategoryCRUD();
								String resultData = categoryCrud.insert(new Category(0, name.getText().toString(), feature.getText().toString()));

								JSONObject json;
								try {
									json = new JSONObject(resultData);
									String result = json.getString("result");
									final int id = json.getInt("id");
									if (result.equals("true")) {
										getActivity().runOnUiThread(new Runnable() {
											public void run() {
												listCategory.add(new Category(id, name.getText().toString(), feature.getText().toString()));
												adapter.notifyDataSetChanged();
												
												Toast.makeText(getActivity(), "Categoria creada correctamente", Toast.LENGTH_SHORT).show();
											}
										});

									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}).start();	
					}

				}
			})
			.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				}
			});

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	class LoadCategories extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		
		
	}


}
