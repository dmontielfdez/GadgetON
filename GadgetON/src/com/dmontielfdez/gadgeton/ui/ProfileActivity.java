package com.dmontielfdez.gadgeton.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.dmontielfdez.gadgeton.R;
import com.dmontielfdez.gadgeton.ddbb.Crudable;
import com.dmontielfdez.gadgeton.ddbb.CustomerCRUD;
import com.dmontielfdez.gadgeton.ddbb.OrderCRUD;
import com.dmontielfdez.gadgeton.model.Customer;
import com.dmontielfdez.gadgeton.model.Order;
import com.dmontielfdez.gadgeton.util.HttpFileUploader;
import com.dmontielfdez.gadgeton.util.ImageLoader;

public class ProfileActivity extends SherlockActivity {

	TextView name, email, addreess, province;
	ImageButton addPhoto;

	ListView listview;
	AdapterOrders adapter;

	Bitmap bit;
	Customer c;
	private static final int ACTION_TAKE_PHOTO_B = 1;
	private static int SELECT_PICTURE = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		CustomerCRUD customerCRUD = new CustomerCRUD();
		c = customerCRUD.findByPK(Integer.toString(MainActivity.idCustomer));

		name = (TextView) findViewById(R.id.name_profile);
		name.setText(c.getName()+" "+c.getSurname());

		email = (TextView) findViewById(R.id.email_user);
		email.setText(c.getEmail());

		addreess = (TextView) findViewById(R.id.address_user);
		addreess.setText(c.getAddress());

		province = (TextView) findViewById(R.id.province_user);
		province.setText(c.getProvince());

		addPhoto = (ImageButton) findViewById(R.id.add_photo_user);

		final ArrayList<Order> listOrders = (ArrayList<Order>) OrderCRUD.findByCustomer(Integer.toString(c.getId()));

		listview = (ListView) findViewById(R.id.listView_profile);
		adapter = new AdapterOrders(ProfileActivity.this, listOrders);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getApplicationContext(), DetailsOrderActivity.class);
				intent.putExtra("id", listOrders.get(arg2).getId());
				startActivity(intent);
			}
		});

		ImageLoader imageLoader=new ImageLoader(getApplicationContext());
		imageLoader.DisplayImageProfile(Crudable.URL+"customers_img/"+c.getImageName(), addPhoto);

		addPhoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				LayoutInflater li = LayoutInflater.from(ProfileActivity.this);
				View promptsView = li.inflate(R.layout.prompt_photo, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						ProfileActivity.this);

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
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.profile, menu);
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
		case R.id.edit_data_user:
			startActivityForResult(new Intent(getApplicationContext(),EditDataCustomerActivity.class),0);
			break;

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

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String imageFile = null;
		if (data!=null && resultCode == RESULT_OK) {
			if (requestCode == ACTION_TAKE_PHOTO_B ) {
				Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
				bit = thumbnail;
				addPhoto.setImageBitmap(thumbnail);
				addPhoto.setBackgroundColor(getResources().getColor(android.R.color.transparent));

				if (checkSD()) {
					imageFile = saveImageToExternalStorage(bit, Integer.toString(c.getId()));
				}

			}else if (requestCode == SELECT_PICTURE){
				Uri selectedImage = data.getData();

				imageFile = getRealPathFromURI(selectedImage);
				Bitmap picture = null;
				try {
					picture = decodeUri(selectedImage);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				bit = picture;
				addPhoto.setImageBitmap(picture);
				addPhoto.setBackgroundColor(getResources().getColor(android.R.color.transparent));

			}

			uploadFile(imageFile);
			final File file = new File(imageFile);
			new Thread(new Runnable() {
				@Override
				public void run() {
					CustomerCRUD.setImageName(c.getId(), file.getName().toString());
				}
			}).start();

		}

		if(requestCode == 0){
			CustomerCRUD customerCRUD = new CustomerCRUD();
			Customer c = customerCRUD.findByPK(Integer.toString(MainActivity.idCustomer));

			name.setText(c.getName()+" "+c.getSurname());
			email.setText(c.getEmail());
			addreess.setText(c.getAddress());
			province.setText(c.getProvince());

		}
	}

	private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

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
		return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);

	}

	private String getRealPathFromURI(Uri contentURI) {
		Cursor cursor = getContentResolver()
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
					HttpFileUploader htfu = new HttpFileUploader(Crudable.URL+"customers/uploadPhoto","noparamshere", filename);
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

			return file.getAbsolutePath();
		}
		catch (Exception e)
		{
			return "";
		}
	}

}
