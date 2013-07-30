package com.dmontielfdez.gagdetonadmin.ui.fragments;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dmontielfdez.gadgetonadmin.R;
import com.dmontielfdez.gadgetonadmin.ddbb.CategoryCRUD;
import com.dmontielfdez.gadgetonadmin.ddbb.Crudable;
import com.dmontielfdez.gadgetonadmin.ddbb.ProductCRUD;
import com.dmontielfdez.gadgetonadmin.model.Category;
import com.dmontielfdez.gadgetonadmin.model.Product;
import com.dmontielfdez.gadgetonadmin.util.HttpFileUploader;
import com.dmontielfdez.gadgetonadmin.util.ImageLoader;

public class FragmentProducts extends Fragment {

	Product product;
	TextView name, rrp, textStock, stock, summary, feature;
	ImageView image;
	RelativeLayout layoutFeature;
	
	SharedPreferences pref;
	String serverDirection;
	
	private static final int ACTION_TAKE_PHOTO_B = 1;
	private static int SELECT_PICTURE = 2;
	Bitmap bit;
	int pos;

	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, 
			Bundle savedInstanceState) {

		setHasOptionsMenu(true);
		return inflater.inflate(R.layout.fragment_products, container, false);
	}

	public void setProduct(Product p, int pos){
		this.pos = pos;
		product = p;
		
		pref =
	            PreferenceManager.getDefaultSharedPreferences(
	                getActivity());
		
		serverDirection = pref.getString("server_direction", "");

		name = (TextView) getView().findViewById(R.id.name_product);
		name.setText(p.getName());

		rrp = (TextView) getView().findViewById(R.id.rrp_product);
		rrp.setText(Double.toString(p.getRrp())+"€");

		textStock = (TextView) getView().findViewById(R.id.stock_product);
		textStock.setText("Stock");

		stock = (TextView) getView().findViewById(R.id.stock);
		stock.setText(Integer.toString(p.getStock()));

		image = (ImageView) getView().findViewById(R.id.image_product);
		
		image.setVisibility(View.VISIBLE);

		ImageLoader imageLoader=new ImageLoader(getActivity());
		imageLoader.DisplayImageProduct(Crudable.URL+"products_img/"+p.getImageName(), image);

		summary = (TextView) getView().findViewById(R.id.summary_product);
		summary.setText(p.getSummary());

		feature = (TextView) getView().findViewById(R.id.feature_product);
		feature.setText(p.getFeature());

		layoutFeature = (RelativeLayout) getView().findViewById(R.id.layout_feature);
		layoutFeature.setBackgroundColor(Color.parseColor("#333333"));

		image.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				LayoutInflater li = LayoutInflater.from(getActivity());
				View promptsView = li.inflate(R.layout.prompt_photo, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						getActivity());

				alertDialogBuilder.setView(promptsView);

				final RadioButton takePicture = (RadioButton) promptsView.findViewById(R.id.take_picture);
				final RadioButton selectPicture = (RadioButton) promptsView.findViewById(R.id.select_picture);

				alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton(getResources().getString(R.string.accept),
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						if (takePicture.isChecked()) {
							dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
						} else if(selectPicture.isChecked()){
							selectPictureIntent(SELECT_PICTURE);
						}
					}
				})
				.setNegativeButton(getResources().getString(R.string.cancel),
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				});

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		});
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.products_details, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.delete:
			if (product!=null) {
				LayoutInflater li = LayoutInflater.from(getActivity());
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

				alertDialogBuilder
				.setMessage("¿Borrar producto?")
				.setCancelable(false)
				.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
					@SuppressLint("NewApi")
					public void onClick(DialogInterface dialog, int ida) {

						new Thread(new Runnable() {

							@Override
							public void run() {
								ProductCRUD productCrud = new ProductCRUD();
								String resultData = productCrud.delete(product);
								JSONObject json;
								try {
									json = new JSONObject(resultData);
									String result = json.getString("result");
									if (result.equals("true")) {
										getActivity().runOnUiThread(new Runnable() {
											@SuppressLint("ResourceAsColor")
											public void run() {
												FragmentListProducts.listProducts.remove(product);
												FragmentListProducts.adapter.notifyDataSetChanged();

												name.setText("");
												rrp.setText("");
												textStock.setText("");
												stock.setText("");
												image.setVisibility(View.INVISIBLE);

												summary.setText("");
												feature.setText("");
												layoutFeature.setBackgroundColor(android.R.color.transparent);
												
												
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
				Toast.makeText(getActivity(), "Seleccione un producto", Toast.LENGTH_SHORT).show();
			}

			break;

		case R.id.edit:
			if (product!=null) {
				LayoutInflater li = LayoutInflater.from(getActivity());
				View promptsNew = li.inflate(R.layout.prompt_new_product, null);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
				alertDialogBuilder.setView(promptsNew);

				final EditText namePrompt = (EditText) promptsNew.findViewById(R.id.name_new_product);
				namePrompt.setText(product.getName());

				final EditText rrpPrompt = (EditText) promptsNew.findViewById(R.id.rrp_new_product);
				rrpPrompt.setText(Double.toString(product.getRrp()));

				final EditText stockPrompt = (EditText) promptsNew.findViewById(R.id.stock_new_product);
				stockPrompt.setText(Integer.toString(product.getStock()));

				final EditText featurePrompt = (EditText) promptsNew.findViewById(R.id.feature_new_product);
				featurePrompt.setText(product.getFeature());

				final EditText summaryPrompt = (EditText) promptsNew.findViewById(R.id.summary_new_product);
				summaryPrompt.setText(product.getSummary());

				final Spinner categoryPrompt = (Spinner) promptsNew.findViewById(R.id.category_new_product);

				CategoryCRUD categoryCrud = new CategoryCRUD();
				ArrayList<Category> listCategories = (ArrayList<Category>) categoryCrud.findAll();

				final String[] categories = new String[listCategories.size()];

				int selected = 0;
				for (int i = 0; i < categories.length; i++) {
					if (product.getCategory_id() == listCategories.get(i).getId()) {
						selected = i;
					}
					categories[i] = listCategories.get(i).getName();
				}

				categoryPrompt.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,categories));
				categoryPrompt.setSelection(selected);
				Log.i("select", selected+"");

				alertDialogBuilder
				.setMessage("Actualizar producto")
				.setCancelable(false)
				.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
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
									final Product p = new Product(product.getId(), namePrompt.getText().toString(), Double.parseDouble(rrpPrompt.getText().toString()), summaryPrompt.getText().toString(), Integer.parseInt(stockPrompt.getText().toString()), featurePrompt.getText().toString(), "", 0);
									String resultData = productCrud.update(p,categories[categoryPrompt.getSelectedItemPosition()]);

									JSONObject json;
									try {
										json = new JSONObject(resultData);
										String result = json.getString("result");
										if (result.equals("true")) {
											getActivity().runOnUiThread(new Runnable() {
												public void run() {
													Toast.makeText(getActivity(), "Datos actualizados", Toast.LENGTH_SHORT).show();

													name.setText(p.getName());
													rrp.setText(Double.toString(p.getRrp())+"€");
													stock.setText(Integer.toString(p.getStock()));
													summary.setText(p.getSummary());
													feature.setText(p.getFeature());

													FragmentListProducts.adapter.getItem(pos).setName(p.getName());
													FragmentListProducts.adapter.getItem(pos).setRrp(p.getRrp());
													FragmentListProducts.adapter.getItem(pos).setStock(p.getStock());
													FragmentListProducts.adapter.getItem(pos).setSummary(p.getSummary());
													FragmentListProducts.adapter.getItem(pos).setFeature(p.getFeature());

													FragmentListProducts.adapter.notifyDataSetChanged();
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
				Toast.makeText(getActivity(), "Seleccione un producto", Toast.LENGTH_SHORT).show();
			}


			break;
			
		case R.id.promotion:
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Socket s = new Socket(serverDirection, 2222);
						DataOutputStream output = new DataOutputStream(s.getOutputStream());
						output.writeUTF("admin-"+product.getId());
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(getActivity(), "Producto promocionado", Toast.LENGTH_SHORT).show();	
							}
						});
					} catch (ConnectException e) {
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(getActivity(), "Fallo al conectar con el servidor", Toast.LENGTH_SHORT).show();	
							}
						});
						
					}
					catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			}).start();
			


		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	// TAKE PHOTO //

	private void dispatchTakePictureIntent(int actionCode) {
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(takePictureIntent, actionCode);
	}

	// SELECT PICTURE //

	private void selectPictureIntent(int actionCode) {
		Intent takePictureIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(takePictureIntent, actionCode);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		String imageFile = null;
		if (data!=null && resultCode == getActivity().RESULT_OK) {
			Log.i("result_code", resultCode+"");
			if (requestCode == ACTION_TAKE_PHOTO_B ) {
				Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
				bit = thumbnail;
				image.setImageBitmap(thumbnail);
				image.setBackgroundColor(getResources().getColor(android.R.color.transparent));

				if (checkSD()) {
					imageFile = saveImageToExternalStorage(bit, Integer.toString(product.getId()));
					Log.i("ruta", imageFile);
				}

			}else if (requestCode == SELECT_PICTURE){
				Uri selectedImage = data.getData();

				imageFile = getRealPathFromURI(selectedImage);

				Log.i("ruta", imageFile);

				Bitmap picture = null;
				try {
					picture = decodeUri(selectedImage);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				bit = picture;
				image.setImageBitmap(picture);
				image.setBackgroundColor(getResources().getColor(android.R.color.transparent));

			}

			uploadFile(imageFile);
			final File file = new File(imageFile);
			new Thread(new Runnable() {
				@Override
				public void run() {
					ProductCRUD.setImageName(product.getId(), file.getName().toString());
				}
			}).start();

		}
	}

	private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o);

		final int REQUIRED_SIZE = 140;

		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp / 2 < REQUIRED_SIZE
					|| height_tmp / 2 < REQUIRED_SIZE) {
				break;
			}
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		return BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o2);

	}

	private String getRealPathFromURI(Uri contentURI) {
		Cursor cursor = getActivity().getContentResolver()
				.query(contentURI, null, null, null, null); 
		cursor.moveToFirst(); 
		int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
		return cursor.getString(idx); 
	}

	public void uploadFile(final String filename){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					FileInputStream fis = new FileInputStream(filename);
					HttpFileUploader htfu = new HttpFileUploader(Crudable.URL+"products/uploadPhoto","noparamshere", filename);
					htfu.doStart(fis);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

			}
		}).start();

	}

	public boolean checkSD() {
		String state = Environment.getExternalStorageState();

		if (state.equals(Environment.MEDIA_MOUNTED)){
			return true;
		}
		else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
			return false;
		}
		else{
			return false;
		}
	}

	public String saveImageToExternalStorage(Bitmap image, String nameFile) {
		Calendar cal = Calendar.getInstance();
		String date = cal.get(Calendar.DATE)+""+cal.get(Calendar.MONTH)
				+""+cal.get(Calendar.YEAR)+""+cal.get(Calendar.HOUR)
				+""+cal.get(Calendar.MINUTE)+""+cal.get(Calendar.SECOND);
		String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/gadgetON/images";
		try{
			OutputStream fOut = null;
			File file = new File(fullPath, date+".png");

			if(file.exists()) {
				file.delete();
			}

			file.createNewFile();
			fOut = new FileOutputStream(file);
			image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			fOut.flush();
			fOut.close();

			Log.i("sqwd",file.getAbsolutePath());

			return file.getAbsolutePath();
		}
		catch (Exception e)
		{
			Log.e("saveToExternalStorage()", e.getMessage());
			return "";
		}
	}


}
