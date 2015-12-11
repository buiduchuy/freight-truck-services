package vn.edu.fpt.fts.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.fragment.R;

public class RegisterActivity extends Activity {
	public class MyDatePickerDialog extends DatePickerDialog {

		private CharSequence title;

		public MyDatePickerDialog(Context context, OnDateSetListener callBack,
				int year, int monthOfYear, int dayOfMonth) {
			super(context, callBack, year, monthOfYear, dayOfMonth);
		}

		@Override
		public void onDateChanged(DatePicker view, int year, int month, int day) {
			super.onDateChanged(view, year, month, day);
			setTitle(title);
		}

		public void setPermanentTitle(CharSequence title) {
			this.title = title;
			setTitle(title);
		}
	}
	private class WebServiceTask extends AsyncTask<String, Integer, String> {

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;
		public static final int GET_TASK = 2;

		public static final int POST_TASK = 1;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 5000;

		private static final String TAG = "WebServiceTask";

		private Context mContext = null;
		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		private ProgressDialog pDlg = null;

		private String processMessage = "Processing...";

		private int taskType = GET_TASK;

		public WebServiceTask(int taskType, Context mContext,
				String processMessage) {

			this.taskType = taskType;
			this.mContext = mContext;
			this.processMessage = processMessage;
		}

		public void addNameValuePair(String name, String value) {

			params.add(new BasicNameValuePair(name, value));
		}

		@Override
		protected String doInBackground(String... urls) {

			String url = urls[0];
			String result = "";

			HttpResponse response = doResponse(url);

			if (response == null) {
				return result;
			} else {

				try {

					result = inputStreamToString(response.getEntity()
							.getContent());

				} catch (IllegalStateException e) {
					Log.e(TAG, e.getLocalizedMessage(), e);

				} catch (IOException e) {
					Log.e(TAG, e.getLocalizedMessage(), e);
				}

			}

			return result;
		}

		private HttpResponse doResponse(String url) {

			// Use our connection and data timeouts as parameters for our
			// DefaultHttpClient
			HttpClient httpclient = new DefaultHttpClient(getHttpParams());

			HttpResponse response = null;

			try {
				switch (taskType) {

				case POST_TASK:
					HttpPost httppost = new HttpPost(url);
					// Add parameters
					httppost.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));

					response = httpclient.execute(httppost);
					break;
				case GET_TASK:
					HttpGet httpget = new HttpGet(url);
					response = httpclient.execute(httpget);
					break;
				}
			} catch (Exception e) {

				Log.e(TAG, e.getLocalizedMessage(), e);

			}

			return response;
		}

		// Establish connection and socket (data retrieval) timeouts
		private HttpParams getHttpParams() {

			HttpParams htpp = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
			HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

			return htpp;
		}

		private String inputStreamToString(InputStream is) {

			String line = "";
			StringBuilder total = new StringBuilder();

			// Wrap a BufferedReader around the InputStream
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			try {
				// Read response until the end
				while ((line = rd.readLine()) != null) {
					total.append(line);
				}
			} catch (IOException e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
			}

			// Return full string
			return total.toString();
		}

		@Override
		protected void onPostExecute(String response) {
			// Xu li du lieu tra ve sau khi insert thanh cong
			// handleResponse(response);
			if (response.equals("-1")) {
				Toast.makeText(RegisterActivity.this,
						"Tài khoản mới đã không được tạo", Toast.LENGTH_LONG)
						.show();
				
			} else {
				Toast.makeText(RegisterActivity.this,
						"Tài khoản mới đã tạo thành công", Toast.LENGTH_LONG)
						.show();
				Intent intent = new Intent(RegisterActivity.this,
						LoginActivity.class);
				startActivity(intent);
			}
			pDlg.dismiss();

		}

		@Override
		protected void onPreExecute() {

			showProgressDialog();

		}

		private void showProgressDialog() {

			pDlg = new ProgressDialog(mContext);
			pDlg.setMessage(processMessage);
			pDlg.setProgressDrawable(mContext.getWallpaper());
			pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDlg.setCancelable(false);
			pDlg.show();

		}

	}
	private ArrayAdapter<String> adapter;
	private Button btnReg;
	private Calendar calendar;
	private DatePickerDialog.OnDateSetListener date;
	private EditText etMail, etPass, etFirst, etLast, etAddr, etDob, etPhone;
	private int gender = 0;

	private String[] list = { "Nam", "Nữ" };

	private Spinner spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		etMail = (EditText) findViewById(R.id.etMail);
		etPass = (EditText) findViewById(R.id.etPass);
		etFirst = (EditText) findViewById(R.id.etFirstname);
		etLast = (EditText) findViewById(R.id.etLastname);
		etAddr = (EditText) findViewById(R.id.etAddr);
		etDob = (EditText) findViewById(R.id.etDob);
		etPhone = (EditText) findViewById(R.id.etPhone);
		spinner = (Spinner) findViewById(R.id.spinGender);
		btnReg = (Button) findViewById(R.id.button_Register);

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 1:
					gender = 0;
					break;

				case 2:
					gender = 1;
					break;
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		calendar = Calendar.getInstance();
		date = new OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, monthOfYear);
				calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				Common.updateLabel(etDob, calendar);
			}
		};

		etDob.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					MyDatePickerDialog dialog = new MyDatePickerDialog(
							RegisterActivity.this, date, calendar
									.get(Calendar.YEAR), calendar
									.get(Calendar.MONTH), calendar
									.get(Calendar.DAY_OF_MONTH));
					dialog.setPermanentTitle("Ngày sinh");
					dialog.show();
				}

			}
		});

		btnReg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WebServiceTask wst = new WebServiceTask(
						WebServiceTask.POST_TASK, RegisterActivity.this,
						"Đang xử lý...");
				wst.addNameValuePair("email", etMail.getText().toString());
				wst.addNameValuePair("password", etPass.getText().toString());
				wst.addNameValuePair("firstName", etFirst.getText().toString());
				wst.addNameValuePair("lastName", etLast.getText().toString());
				wst.addNameValuePair("gender", gender + "");
				wst.addNameValuePair("phone", etPhone.getText().toString());
				wst.addNameValuePair("address", etAddr.getText().toString());
				wst.addNameValuePair("createBy", "owner");
				wst.addNameValuePair("createTime", Common.formatDate(Calendar.getInstance()));
				wst.addNameValuePair("dateOfBirth", Common.formatDate(calendar));
				String url = Common.IP_URL
						+ Common.Service_Account_CreateOwnerAccount;
				wst.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
						new String[] { url });
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
