package com.dmontielfdez.gadgeton.ui;

import io.card.payment.CardIOActivity;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.dmontielfdez.gadgeton.R;
import com.dmontielfdez.gadgeton.ddbb.CustomerCRUD;
import com.dmontielfdez.gadgeton.ddbb.LineCRUD;
import com.dmontielfdez.gadgeton.ddbb.OrderCRUD;
import com.dmontielfdez.gadgeton.ddbb.ProductCRUD;
import com.dmontielfdez.gadgeton.ddbblocal.Cart;
import com.dmontielfdez.gadgeton.ddbblocal.CrudableCart;
import com.dmontielfdez.gadgeton.ddbblocal.GadgetONOpenHelper;
import com.dmontielfdez.gadgeton.model.Customer;
import com.dmontielfdez.gadgeton.model.Line;
import com.dmontielfdez.gadgeton.model.Order;
import com.dmontielfdez.gadgeton.model.Product;
import com.dmontielfdez.gadgeton.util.Utils;

public class ConfirmCartActivity extends SherlockActivity {

	private static final String MY_CARDIO_APP_TOKEN = "09c13afef7224cbdbaa391a2c9446c0a";
	private int MY_SCAN_REQUEST_CODE = 100;

	TextView name, surname, address, province, phone, postalCode, email;
	RadioButton creditCard, paypal;
	TextView order, totalOrder, textExpenses,expenses, textCostShipping;
	Button confirm;

	double costCreditCard = 3.99;
	double costPayPal = 1.99;
	double costShipping = 6.95;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_cart);

		Bundle bundle = getIntent().getExtras();
		final double total = bundle.getDouble("totalOrder");

		CustomerCRUD customerCrud = new CustomerCRUD();
		Customer c = customerCrud.findByPK(Integer.toString(MainActivity.idCustomer));

		name = (TextView) findViewById(R.id.name_confirm);
		name.setText(c.getName());

		surname = (TextView) findViewById(R.id.surname_confirm);
		surname.setText(c.getSurname());

		address = (TextView) findViewById(R.id.address_confirm);
		address.setText(c.getAddress());

		province = (TextView) findViewById(R.id.province_confirm);
		province.setText(c.getProvince());

		phone = (TextView) findViewById(R.id.phone_confirm);
		phone.setText(c.getPhone());

		postalCode = (TextView) findViewById(R.id.postalcode_confirm);
		postalCode.setText(c.getPostalCode());

		email = (TextView) findViewById(R.id.email_confirm);
		email.setText(c.getEmail());

		order = (TextView) findViewById(R.id.total_order_confirm);
		order.setText(Utils.numberFormat(total, 2)+"€");

		totalOrder = (TextView) findViewById(R.id.total_confirm);
		totalOrder.setText(Utils.numberFormat(total+costShipping,2)+"€");

		textExpenses = (TextView) findViewById(R.id.text_expenses);

		expenses = (TextView) findViewById(R.id.expenses);

		textCostShipping = (TextView) findViewById(R.id.cost_shipping_confirm);
		textCostShipping.setText(costShipping+"€");

		creditCard = (RadioButton) findViewById(R.id.credit_card_option);
		paypal = (RadioButton) findViewById(R.id.paypal_option);


		// Card.io
		creditCard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				scarCard();

				textExpenses.setText("Comision por tarjeta de credito");
				expenses.setText(costCreditCard+"€");
				totalOrder.setText(Utils.numberFormat(total+costShipping+costCreditCard,2)+"€");
			}
		});

		paypal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				textExpenses.setText("Comision por PayPal");
				expenses.setText(costPayPal+"€");
				totalOrder.setText(Utils.numberFormat(total+costShipping+costPayPal,2)+"€");
			}
		});

		confirm = (Button) findViewById(R.id.confirm_button);
		confirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (paypal.isChecked() || creditCard.isChecked()) {
					new Thread(new Runnable() {

						@Override
						public void run() {

							runOnUiThread(new Runnable() {
								public void run() {
									confirm.setBackgroundColor(Color.parseColor("#cccccc"));
									Toast.makeText(getApplicationContext(), getResources().getString(R.string.processing_request), Toast.LENGTH_SHORT).show();
								}
							});

							Calendar cal = Calendar.getInstance();
							String date = cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH)+1)
									+"-"+cal.get(Calendar.YEAR)+" "+cal.get(Calendar.HOUR)
									+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);

							String method_pay = "";
							if (creditCard.isChecked()) {
								method_pay = "Tarjeta de credito";
							} else if(paypal.isChecked()){
								method_pay = "PayPal";
							}

							Order o = new Order(0, date, "Preparando", method_pay, (total+6.95), MainActivity.idCustomer);

							OrderCRUD orderCRUD = new OrderCRUD();
							String respStr = orderCRUD.insert(o);

							JSONObject resultData;
							try {
								resultData = new JSONObject(respStr);

								boolean result = resultData.getBoolean("result");
								int idOrder = resultData.getInt("order_id");

								if (result) {
									GadgetONOpenHelper openHelper = new GadgetONOpenHelper(getApplicationContext(), "GadgetONDDBB", null, 1);
									CrudableCart connectDDBB = new CrudableCart(openHelper);

									ProductCRUD productCRUD = new ProductCRUD();
									ArrayList<Cart> listCart = (ArrayList<Cart>) connectDDBB.findAll();
									ArrayList<Line> listLines = new ArrayList<Line>();
									for (Cart cart : listCart) {
										Product p = productCRUD.findByPK(Integer.toString(cart.getId_product()));
										listLines.add(new Line(0, p.getRrp(), cart.getQuantity(), p.getRrp()*cart.getQuantity(), cart.getId_product(), idOrder));
									}

									LineCRUD lineCRUD = new LineCRUD();
									respStr = lineCRUD.insertAll(listLines);

									resultData = new JSONObject(respStr);


									result = resultData.getBoolean("result");

									if (result) {
										connectDDBB.deleteAll(Integer.toString(MainActivity.idCustomer));
										finish();
										Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
										intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										startActivity(intent);
									}
								}

							} catch (Exception e) {
							}

						}
					}).start();
				} else{
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.select_method_payment), Toast.LENGTH_SHORT).show();
				}

			}
		});

	}

	public void scarCard() {
		Intent scanIntent = new Intent(this, CardIOActivity.class);

		scanIntent.putExtra(CardIOActivity.EXTRA_APP_TOKEN, MY_CARDIO_APP_TOKEN);

		scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true);
		scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true);
		scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_ZIP, false);

		scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, false);

		startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.cc_saved), Toast.LENGTH_SHORT).show();
		}
		else {
			Toast.makeText(getApplicationContext(), getResources().getString(R.string.cc_unsaved), Toast.LENGTH_SHORT).show();
		}

	}

}
