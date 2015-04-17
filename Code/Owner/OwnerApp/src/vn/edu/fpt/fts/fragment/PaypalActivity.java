package vn.edu.fpt.fts.fragment;

import java.math.BigDecimal;

import org.json.JSONException;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PaypalActivity extends Activity {

	private static PayPalConfiguration config = new PayPalConfiguration()
			.environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
			.clientId(
					"AUYEywUBcwk_YKlg-Bqmfp2yx-ecX4A7qU6MN-oU12eq3k1xoH1JKnAfDjeFLjmDTIOSNgRBcAB8mwXm");
	private static double exchangeRate = 21;
	private String orderID;
	private int price;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		orderID = getIntent().getStringExtra("orderID");
		price = getIntent().getIntExtra("price", 0);
		
		setContentView(R.layout.activity_paypal);
		Intent intent = new Intent(this, PayPalService.class);
		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
		startService(intent);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		stopService(new Intent(this, PayPalService.class));
		super.onDestroy();
	}

	public void onBuyPressed(View pressed) {
		BigDecimal bdPrice = BigDecimal.valueOf(price / exchangeRate);
		PayPalPayment payment = new PayPalPayment(bdPrice,
				"USD", orderID, PayPalPayment.PAYMENT_INTENT_SALE);
		Intent intent = new Intent(this, PaymentActivity.class);
		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
		intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
		startActivityForResult(intent, 0);
	}
	public void onClosePressed(View pressed) {
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			PaymentConfirmation confirm = data
					.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
			if (confirm != null) {
				try {
					String json = confirm.toJSONObject().toString(4);
					Toast.makeText(getApplicationContext(),
							"PaymentConfirmation info received from PayPal",
							Toast.LENGTH_LONG).show();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else if (resultCode == Activity.RESULT_CANCELED) {
			Log.i("paymentExample", "The user canceled.");
		} else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
			Log.i("paymentExample",
					"An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.paypal, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
