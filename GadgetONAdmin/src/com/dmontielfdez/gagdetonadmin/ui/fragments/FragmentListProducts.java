package com.dmontielfdez.gagdetonadmin.ui.fragments;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.dmontielfdez.gadgetonadmin.ddbb.CategoryCRUD;
import com.dmontielfdez.gadgetonadmin.ddbb.ProductCRUD;
import com.dmontielfdez.gadgetonadmin.model.Category;
import com.dmontielfdez.gadgetonadmin.model.Product;
import com.dmontielfdez.gadgetonadmin.R;
import com.dmontielfdez.gadgetonadmin.ui.ProductsDetailsActivity;
import com.dmontielfdez.gadgetonadmin.ui.adapters.*;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentListProducts extends Fragment {

	private ListView lstListado;
	ProductCRUD productCRUD;
	static ArrayList<Product> listProducts;
	static ProductAdapter adapter;


	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, 
			Bundle savedInstanceState) {

		setHasOptionsMenu(true);
		return inflater.inflate(R.layout.fragment_list_products, container, false);
	}

	@Override
	public void onActivityCreated(Bundle state) {
		super.onActivityCreated(state);

		lstListado = (ListView)getView().findViewById(R.id.listview_products);
		productCRUD = new ProductCRUD();
		listProducts = (ArrayList<Product>) productCRUD.findAll();

		adapter = new ProductAdapter(getActivity(),listProducts);
		lstListado.setAdapter(adapter);

		lstListado.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> list, View view, int pos, long id) {

				Product p = adapter.getItem(pos);

				FragmentProducts fragment= (FragmentProducts) getFragmentManager().findFragmentById(R.id.FrgDetailsProducts);
				if (fragment != null && fragment.isInLayout()) {
					fragment.setProduct(p, pos);
				} else {
					Intent intent = new Intent(getActivity().getApplicationContext(), ProductsDetailsActivity.class);
					intent.putExtra("id", p.getId());
					intent.putExtra("pos",pos);
					startActivity(intent);
				}

			}
		});

		// TextFilter
		lstListado.setTextFilterEnabled(true);
		EditText search = (EditText)getView().findViewById(R.id.search_products);
		search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (count < before) {
					adapter.resetData();
				}

				adapter.getFilter().filter(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.products_list, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.new_content:
			LayoutInflater li = LayoutInflater.from(getActivity());
			View promptsNew = li.inflate(R.layout.prompt_new_product, null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
			alertDialogBuilder.setView(promptsNew);

			final EditText name = (EditText) promptsNew.findViewById(R.id.name_new_product);
			final EditText rrp = (EditText) promptsNew.findViewById(R.id.rrp_new_product);
			final EditText stock = (EditText) promptsNew.findViewById(R.id.stock_new_product);
			final EditText feature = (EditText) promptsNew.findViewById(R.id.feature_new_product);
			final EditText summary = (EditText) promptsNew.findViewById(R.id.summary_new_product);
			final Spinner category = (Spinner) promptsNew.findViewById(R.id.category_new_product);

			CategoryCRUD categoryCrud = new CategoryCRUD();
			ArrayList<Category> listCategories = (ArrayList<Category>) categoryCrud.findAll();

			final String[] categories = new String[listCategories.size()];

			for (int i = 0; i < categories.length; i++) {
				categories[i] = listCategories.get(i).getName();
			}

			category.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,categories));

			alertDialogBuilder
			.setMessage("Añadir producto")
			.setCancelable(false)
			.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
				@SuppressLint("NewApi")
				public void onClick(DialogInterface dialog, int ida) {
					if (name.getText().toString().isEmpty()) {
						Toast.makeText(getActivity(), "El nombre del producto no puede estar vacio", Toast.LENGTH_LONG).show();
					} else if(rrp.getText().toString().isEmpty()){
						Toast.makeText(getActivity(), "El precio no puede estar vacio", Toast.LENGTH_LONG).show();
					} else if(stock.getText().toString().isEmpty()){
						Toast.makeText(getActivity(), "El stock no puede estar vacio", Toast.LENGTH_LONG).show();
					} else if(feature.getText().toString().isEmpty()){
						Toast.makeText(getActivity(), "Las caracteristicas de la categoria no puede estar vacia", Toast.LENGTH_LONG).show();
					}
					else{
						new Thread(new Runnable() {

							@Override
							public void run() {
								ProductCRUD productCrud = new ProductCRUD();
								String resultData = productCrud.insert(new Product(0, name.getText().toString(), Double.parseDouble(rrp.getText().toString()), summary.getText().toString(), Integer.parseInt(stock.getText().toString()), feature.getText().toString(), "", 0), categories[category.getSelectedItemPosition()]);

								JSONObject json;
								try {
									json = new JSONObject(resultData);
									String result = json.getString("result");
									final int id = json.getInt("id");
									if (result.equals("true")) {
										getActivity().runOnUiThread(new Runnable() {
											public void run() {
												listProducts.add(new Product(id, name.getText().toString(), Double.parseDouble(rrp.getText().toString()), summary.getText().toString(), Integer.parseInt(stock.getText().toString()), feature.getText().toString(), "", 0));
												adapter.notifyDataSetChanged();
												
												Toast.makeText(getActivity(), "Producto creado correctamente", Toast.LENGTH_SHORT).show();
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

}
