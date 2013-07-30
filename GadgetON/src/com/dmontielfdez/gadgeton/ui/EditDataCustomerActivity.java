package com.dmontielfdez.gadgeton.ui;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.dmontielfdez.gadgeton.R;
import com.dmontielfdez.gadgeton.ddbb.CustomerCRUD;
import com.dmontielfdez.gadgeton.model.Customer;
import com.dmontielfdez.gadgeton.util.Utils;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

public class EditDataCustomerActivity extends SherlockActivity {

	EditText name, surname, birthday, address, postalCode, phone, email, password;
	AutoCompleteTextView province;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_data_user);

		CustomerCRUD customerCRUD = new CustomerCRUD();
		Customer c = customerCRUD.findByPK(Integer.toString(MainActivity.idCustomer));

		name = (EditText) findViewById(R.id.name_edit_data);
		surname = (EditText) findViewById(R.id.surname_edit_data);
		birthday = (EditText) findViewById(R.id.birthday_edit_data);
		address = (EditText) findViewById(R.id.address_edit_data);
		province = (AutoCompleteTextView) findViewById(R.id.province_edit_data);
		postalCode = (EditText) findViewById(R.id.postalcode_edit_data);
		phone = (EditText) findViewById(R.id.phone_edit_data);
		email = (EditText) findViewById(R.id.email_edit_data);
		password = (EditText) findViewById(R.id.password_edit_data);

		name.setText(c.getName());
		surname.setText(c.getSurname());
		birthday.setText(Utils.formatDate(c.getBirthday()));
		address.setText(c.getAddress());
		province.setText(c.getProvince());
		postalCode.setText(c.getPostalCode());
		phone.setText(c.getPhone());
		email.setText(c.getEmail());

		ArrayAdapter<String> adapterProvinces = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.provinces));

		province.setAdapter(adapterProvinces);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.edit_data_user, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.edit_data_user:
			new Thread(new Runnable() {
				@Override
				public void run() {
					if (checkFields()) {
						Customer c = new Customer(MainActivity.idCustomer, name.getText().toString(), surname.getText().toString(), birthday.getText().toString(), 
								address.getText().toString(), province.getText().toString(), postalCode.getText().toString(), phone.getText().toString(), 
								email.getText().toString(), password.getText().toString(),"");

						CustomerCRUD customer = new CustomerCRUD();
						String result = customer.update(c);

						if(result.equals("true")){
							finish();
							runOnUiThread(new Runnable() {
								public void run() {
									Toast.makeText(getApplicationContext(), getResources().getString(R.string.information_updated), Toast.LENGTH_SHORT).show();
									finish();
								}
							});
						} else if(result.equals("false")){
							runOnUiThread(new Runnable() {
								public void run() {
									Toast.makeText(getApplicationContext(), getResources().getString(R.string.updating_failure), Toast.LENGTH_LONG).show();
								}
							});
						} else if(result.equals("exists")){
							runOnUiThread(new Runnable() {
								public void run() {
									Toast.makeText(getApplicationContext(), getResources().getString(R.string.email_already_signed), Toast.LENGTH_LONG).show();
								}
							});
						} else{
							runOnUiThread(new Runnable() {
								public void run() {
									Toast.makeText(getApplicationContext(), getResources().getString(R.string.updating_failure), Toast.LENGTH_LONG).show();
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
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
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

}
