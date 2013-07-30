package com.dmontielfdez.gadgeton.ui;

import java.io.File;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.dmontielfdez.gadgeton.R;
import com.dmontielfdez.gadgeton.ddbb.Crudable;
import com.dmontielfdez.gadgeton.util.RequestHttp;

public class LoginActivity extends SherlockActivity {

	EditText username, password;

	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	Button loginButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (!networkAvailable()) {
			finish();
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_internet), Toast.LENGTH_LONG).show();
		}

		preferences = getSharedPreferences("PreferencesGadgeton",Context.MODE_PRIVATE);
		editor = preferences.edit();

		String prefEmail = preferences.getString("email", "");

		if (prefEmail.equals("")) {
			setContentView(R.layout.activity_login);

			loginButton = (Button) findViewById(R.id.login_button);
			TextView signUp = (TextView) findViewById(R.id.text_sign_up);

			username = (EditText) findViewById(R.id.email_login);
			password = (EditText) findViewById(R.id.password_login);

			loginButton.setBackgroundColor(Color.parseColor("#3E7B3E"));

			loginButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					if(username.getText().toString().equals("")){
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.input_email), Toast.LENGTH_LONG).show();
					} else if(password.getText().toString().equals("")){
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.input_pwd), Toast.LENGTH_LONG).show();
					} else{
						if (networkAvailable()) {
							loginButton.setBackgroundColor(Color.parseColor("#cccccc"));
							login(username.getText().toString(), password.getText().toString());
						} else{
							Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_internet), Toast.LENGTH_SHORT).show();
						}
					}
				}
			});

			// Subraya el texto de sign up
			SpannableString myString  = new SpannableString(getResources().getString(R.string.text_sign_up));
			myString.setSpan(new UnderlineSpan(), 0, myString.length(), 0);
			signUp.setText(myString);

			signUp.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
				}
			});
		} else{
			login(preferences.getString("email", ""),preferences.getString("pwd", ""));
		}
	}

	public void login(final String usernameLogin, final String passwordLogin){
		new Thread(new Runnable() {
			@Override
			public void run() {

				runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.connecting), Toast.LENGTH_SHORT).show();
						new Color();

					}
				});

				JSONObject data = new JSONObject(); 

				try {
					data.put("username", usernameLogin);
					data.put("password", passwordLogin);

					String respStr = RequestHttp.requestPOST(Crudable.URL+"login", data.toString());

					JSONObject resultData = new JSONObject(respStr);

					boolean result = resultData.getBoolean("result");
					int idCustomer = resultData.getInt("idCustomer");

					if (result) {
						editor.putString("email",usernameLogin);
						editor.putString("pwd",passwordLogin);
						editor.commit();
						finish();
						Intent intent = new Intent(LoginActivity.this, MainActivity.class);
						intent.putExtra("idCustomer", idCustomer);
						createDirectories();
						startService(new Intent(LoginActivity.this, PromotionService.class));
						startActivity(intent);

					} else{
						runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_auth), Toast.LENGTH_LONG).show();
								setContentView(R.layout.activity_login);
								Button loginButton = (Button) findViewById(R.id.login_button);
								loginButton.setBackgroundColor(Color.parseColor("#3E7B3E"));
								TextView signUp = (TextView) findViewById(R.id.text_sign_up);

								// Subraya el texto de sign up
								SpannableString myString  = new SpannableString(getResources().getString(R.string.text_sign_up));
								myString.setSpan(new UnderlineSpan(), 0, myString.length(), 0);
								signUp.setText(myString);
								signUp.setOnClickListener(new OnClickListener() {
									@Override
									public void onClick(View v) {
										startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
									}
								});



								username = (EditText) findViewById(R.id.email_login);
								password = (EditText) findViewById(R.id.password_login);

								loginButton.setOnClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View v) {

										if(username.getText().toString().equals("")){
											Toast.makeText(getApplicationContext(), getResources().getString(R.string.input_email), Toast.LENGTH_LONG).show();
										} else if(password.getText().toString().equals("")){
											Toast.makeText(getApplicationContext(), getResources().getString(R.string.input_pwd), Toast.LENGTH_LONG).show();
										} else{
											if (networkAvailable()) {
												login(username.getText().toString(), password.getText().toString());
											} else{
												Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_internet), Toast.LENGTH_SHORT).show();
											}
										}
									}
								});


							}
						});
					}
				} 
				catch (Exception e) {
					e.printStackTrace();
				}

			}
		}).start();
	}

	public boolean networkAvailable() {
		Context context = getApplicationContext();
		ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectMgr != null) {
			NetworkInfo[] netInfo = connectMgr.getAllNetworkInfo();
			if (netInfo != null) {
				for (NetworkInfo net : netInfo) {
					if (net.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} 
		return false;
	}

	public void createDirectories() {
		if (checkSD()) {
			File sdPath = Environment.getExternalStorageDirectory();
			File main = new File(sdPath, "GadgetON");
			if (!main.exists()) {
				main.mkdir();
			}
			File pathImages = new File(main, "Images");
			if (!pathImages.exists()) {
				pathImages.mkdir();
			}
		}

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

}
