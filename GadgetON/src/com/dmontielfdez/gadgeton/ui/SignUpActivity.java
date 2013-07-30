package com.dmontielfdez.gadgeton.ui;

import java.util.Calendar;
import java.util.regex.Pattern;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.dmontielfdez.gadgeton.R;
import com.dmontielfdez.gadgeton.ddbb.CustomerCRUD;
import com.dmontielfdez.gadgeton.model.Customer;

public class SignUpActivity extends SherlockActivity {

	EditText name, surname, birthday, address, postalCode, phone, email, password;
	Button signup;
	AutoCompleteTextView province;

	public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
			"[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
					"\\@" +
					"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
					"(" +
					"\\." +
					"[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
					")+"
			);

	//Atributos para datepicker
	private int mYear;    
	private int mMonth;    
	private int mDay;    
	static final int DATE_DIALOG_ID = 0;

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {                
		public void onDateSet(DatePicker view, int year,                                       
				int monthOfYear, int dayOfMonth) {                    
			mYear = year;                    
			mMonth = monthOfYear;                    
			mDay = dayOfMonth;                    
			updateDisplay();                
		}            
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_signup);

		name = (EditText) findViewById(R.id.name_signup);
		surname = (EditText) findViewById(R.id.surname_signup);
		birthday = (EditText) findViewById(R.id.birthday_signup);
		address = (EditText) findViewById(R.id.address_signup);
		province = (AutoCompleteTextView) findViewById(R.id.province_signup);
		postalCode = (EditText) findViewById(R.id.postalcode_signup);
		phone = (EditText) findViewById(R.id.phone_signup);
		email = (EditText) findViewById(R.id.email_signup);
		password = (EditText) findViewById(R.id.password_signup);

		ArrayAdapter<String> adapterProvinces = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.provinces));

		province.setAdapter(adapterProvinces);

		signup = (Button) findViewById(R.id.signup_button);

		signup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						if (checkFields()) {
							if (checkEmail(email.getText().toString())) {

								runOnUiThread(new Runnable() {
									public void run() {
										Toast.makeText(getApplicationContext(), getResources().getString(R.string.register), Toast.LENGTH_SHORT).show();
										signup.setBackgroundColor(Color.parseColor("#cccccc"));
									}
								});

								Customer c = new Customer(0, name.getText().toString(), surname.getText().toString(), birthday.getText().toString(), 
										address.getText().toString(), province.getText().toString(), postalCode.getText().toString(), phone.getText().toString(), 
										email.getText().toString(), password.getText().toString(),"");

								CustomerCRUD customer = new CustomerCRUD();
								String result = customer.insert(c);

								if(result.equals("true")){
									finish();
									runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(getApplicationContext(), getResources().getString(R.string.information_updated), Toast.LENGTH_LONG).show();
										}
									});
								} else if(result.equals("false")){
									runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(getApplicationContext(), getResources().getString(R.string.updating_failure), Toast.LENGTH_LONG).show();
											signup.setBackgroundColor(Color.parseColor("#3E7B3E"));
										}
									});
								} else if(result.equals("exists")){
									runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(getApplicationContext(), getResources().getString(R.string.email_already_signed), Toast.LENGTH_LONG).show();
											signup.setBackgroundColor(Color.parseColor("#3E7B3E"));
										}
									});
								} 
							} else{
								runOnUiThread(new Runnable() {
									public void run() {
										Toast.makeText(getApplicationContext(), getResources().getString(R.string.email_invalid), Toast.LENGTH_LONG).show();
									}
								});
							}

						} else{
							runOnUiThread(new Runnable() {
								public void run() {
									Toast.makeText(getApplicationContext(), getResources().getString(R.string.make_sure_fill_field), Toast.LENGTH_LONG).show();
								}
							});
						}

					}
				}).start();

			}
		});


		// Show datePicker
		birthday.setOnClickListener(new View.OnClickListener() 
		{            
			@SuppressWarnings("deprecation")
			public void onClick(View v) {                
				showDialog(DATE_DIALOG_ID);            
			}        
		});        
		// get the current date        
		final Calendar c = Calendar.getInstance();        
		mYear = c.get(Calendar.YEAR);        
		mMonth = c.get(Calendar.MONTH);        
		mDay = c.get(Calendar.DAY_OF_MONTH);        
		// display the current date (this method is below)        
		updateDisplay(); 

	}

	public boolean checkFields(){
		if (name.getText().toString().equals("")) {
			return false;
		} else if (surname.getText().toString().equals("")){
			return false;
		} else if (birthday.getText().toString().equals("")){
			return false;
		} else if (address.getText().toString().equals("")){
			return false;
		} else if (province.getText().toString().equals("")){
			return false;
		} else if (postalCode.getText().toString().equals("")){
			return false;
		} else if (phone.getText().toString().equals("")){
			return false;
		} else if (email.getText().toString().equals("")){
			return false;
		} else if (password.getText().toString().equals("")){
			return false;
		} else{
			return true;
		}
	}

	// updates the date in the TextView    
	private void updateDisplay() {        
		birthday.setText(            
				new StringBuilder()                    
				// Month is 0 based so add 1 
				.append(mDay).append("-")
				.append(mMonth + 1).append("-")         
				.append(mYear).append(" "));    
	}


	@Override
	protected Dialog onCreateDialog(int id) {    
		switch (id) {   
		case DATE_DIALOG_ID:        
			return new DatePickerDialog(this,                    
					mDateSetListener,                    
					mYear, mMonth, mDay);    
		}    
		return null;
	}

	private boolean checkEmail(String email) {
		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}


}
