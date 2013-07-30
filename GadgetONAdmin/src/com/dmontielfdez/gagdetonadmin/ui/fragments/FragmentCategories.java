package com.dmontielfdez.gagdetonadmin.ui.fragments;

import org.json.JSONException;
import org.json.JSONObject;

import com.dmontielfdez.gadgetonadmin.R;
import com.dmontielfdez.gadgetonadmin.ddbb.CategoryCRUD;
import com.dmontielfdez.gadgetonadmin.model.Category;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentCategories extends Fragment {

	Category category;
	int pos;
	TextView name, feature;

	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, 
			Bundle savedInstanceState) {

		setHasOptionsMenu(true);
		return inflater.inflate(R.layout.fragment_categories, container, false);
	}

	public void setCategory(Category c, int pos){
		this.pos = pos;
		category = c;

		TextView textName = (TextView)getView().findViewById(R.id.text_name_category);
		textName.setText("Nombre");

		name = (TextView)getView().findViewById(R.id.name_category);
		name.setText(c.getName());

		TextView textFeature = (TextView)getView().findViewById(R.id.text_feature_category);
		textFeature.setText("Caracteristicas");

		feature = (TextView)getView().findViewById(R.id.feature_category);
		feature.setText(c.getFeature());
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.category_details, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete:
			if (category!=null) {
				LayoutInflater li = LayoutInflater.from(getActivity());
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

				alertDialogBuilder
				.setMessage("¿Borrar categoria?")
				.setCancelable(false)
				.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
					@SuppressLint("NewApi")
					public void onClick(DialogInterface dialog, int ida) {

						new Thread(new Runnable() {

							@Override
							public void run() {
								CategoryCRUD categoryCrud = new CategoryCRUD();
								String resultData = categoryCrud.delete(category);
								JSONObject json;
								try {
									json = new JSONObject(resultData);
									String result = json.getString("result");
									if (result.equals("true")) {
										getActivity().runOnUiThread(new Runnable() {
											public void run() {
												FragmentListCategories.listCategory.remove(category);
												FragmentListCategories.adapter.notifyDataSetChanged();

												name.setText("");
												feature.setText("");
												
												
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
				})
				.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				});

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			} else{
				Toast.makeText(getActivity(), "Seleccione una categoria", Toast.LENGTH_SHORT).show();
			}

			break;

		case R.id.edit:
			if (category!=null) {
				LayoutInflater li = LayoutInflater.from(getActivity());
				View promptsNew = li.inflate(R.layout.prompt_new_category, null);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
				alertDialogBuilder.setView(promptsNew);
				final EditText namePrompt = (EditText) promptsNew.findViewById(R.id.name_new_category);
				namePrompt.setText(category.getName());
				final EditText featurePrompt = (EditText) promptsNew.findViewById(R.id.feature_new_category);
				featurePrompt.setText(category.getFeature());

				alertDialogBuilder
				.setMessage("Añadir categoria")
				.setCancelable(false)
				.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
					@SuppressLint("NewApi")
					public void onClick(DialogInterface dialog, int ida) {
						if (namePrompt.getText().toString().isEmpty()) {
							Toast.makeText(getActivity(), "El nombre de la categoria no puede estar vacio", Toast.LENGTH_LONG).show();
						} else if(featurePrompt.getText().toString().isEmpty()){
							Toast.makeText(getActivity(), "Las caracteristicas de la categoria no puede estar vacia", Toast.LENGTH_LONG).show();
						}
						else{
							new Thread(new Runnable() {

								@Override
								public void run() {
									CategoryCRUD categoryCrud = new CategoryCRUD();
									final Category c = new Category(category.getId(), namePrompt.getText().toString(), featurePrompt.getText().toString());
									String resultData = categoryCrud.update(c);

									JSONObject json;
									try {
										json = new JSONObject(resultData);
										String result = json.getString("result");
										if (result.equals("true")) {
											getActivity().runOnUiThread(new Runnable() {
												public void run() {
													Toast.makeText(getActivity(), "Datos actualizados", Toast.LENGTH_SHORT).show();
													name.setText(c.getName());
													feature.setText(c.getFeature());
													FragmentListCategories.listCategory.get(pos).setName(c.getName());
													FragmentListCategories.listCategory.get(pos).setFeature(c.getFeature());
													FragmentListCategories.adapter.notifyDataSetChanged();
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
			} else{
				Toast.makeText(getActivity(), "Seleccione una categoria", Toast.LENGTH_SHORT).show();
			}


			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
