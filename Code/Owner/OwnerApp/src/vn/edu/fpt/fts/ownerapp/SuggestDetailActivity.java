package vn.edu.fpt.fts.ownerapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.edu.fpt.fts.adapter.ModelAdapter;
import vn.edu.fpt.fts.classes.Route;
import vn.edu.fpt.fts.common.Common;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SuggestDetailActivity extends Activity {
	private int routeid;
	private TextView startAddr, destAddr, startTime, finishTime, category;
	private EditText etPrice, etNote;
	private Button btnSend;
	private String goodsID, ownerID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggest_detail);

		routeid = getIntent().getIntExtra("route", 0);
		goodsID = getIntent().getStringExtra("goodsID");
		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK,
				SuggestDetailActivity.this, "Đang xử lý...");
		String url = Common.IP_URL + Common.Service_Route_GetByID;
		wst.addNameValuePair("routeID", routeid + "");
		wst.execute(new String[] { url });

		SharedPreferences preferences = getSharedPreferences("MyPrefs",
				Context.MODE_PRIVATE);
		ownerID = preferences.getString("ownerID", "");

		startAddr = (TextView) this.findViewById(R.id.textview_startAddr);
		destAddr = (TextView) this.findViewById(R.id.textview_destAddr);
		startTime = (TextView) this.findViewById(R.id.textview_startTime);
		finishTime = (TextView) this.findViewById(R.id.textview_finishTime);
		category = (TextView) this.findViewById(R.id.textview_category);
		etPrice = (EditText) findViewById(R.id.edittext_price);
		etNote = (EditText) findViewById(R.id.edittext_note);
		btnSend = (Button) findViewById(R.id.button_send);

		btnSend.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar calendar = Calendar.getInstance();
				WebServiceTask2 wst2 = new WebServiceTask2(
						WebServiceTask2.POST_TASK, SuggestDetailActivity.this,
						"Đang xử lý...");
				wst2.addNameValuePair("price", etPrice.getText().toString());
				wst2.addNameValuePair("notes", etNote.getText().toString());
				wst2.addNameValuePair("createTime", formatDate(calendar));
				wst2.addNameValuePair("createBy", "owner");
				wst2.addNameValuePair("routeID", routeid + "");
				wst2.addNameValuePair("goodsID", goodsID + "");
				wst2.addNameValuePair("dealStatusID", "1");
				wst2.addNameValuePair("refDealID", "0");
				wst2.addNameValuePair("active", "1");
				String url = Common.IP_URL + Common.Service_Deal_Create;
				wst2.execute(new String[] { url });
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.suggest_detail, menu);
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

	private String formatDate(Calendar calendar) {
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
		return sdf.format(calendar.getTime());
	}

	private class WebServiceTask extends AsyncTask<String, Integer, String> {

		public static final int POST_TASK = 1;
		public static final int GET_TASK = 2;

		private static final String TAG = "WebServiceTask";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 5000;

		private int taskType = GET_TASK;
		private Context mContext = null;
		private String processMessage = "Processing...";

		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		private ProgressDialog pDlg = null;

		public WebServiceTask(int taskType, Context mContext,
				String processMessage) {

			this.taskType = taskType;
			this.mContext = mContext;
			this.processMessage = processMessage;
		}

		public void addNameValuePair(String name, String value) {

			params.add(new BasicNameValuePair(name, value));
		}

		private void showProgressDialog() {

			pDlg = new ProgressDialog(mContext);
			pDlg.setMessage(processMessage);
			pDlg.setProgressDrawable(mContext.getWallpaper());
			pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDlg.setCancelable(false);
			pDlg.show();

		}

		@Override
		protected void onPreExecute() {

			showProgressDialog();

		}

		protected String doInBackground(String... urls) {

			String url = urls[0];
			String result = "";

			HttpResponse response = doResponse(url);

			if (response.getEntity() == null) {
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

		@Override
		protected void onPostExecute(String response) {
			// Xu li du lieu tra ve sau khi insert thanh cong
			// handleResponse(response);
			if (response != null) {
				Route route = new Route();
				try {
					// android.os.Debug.waitForDebugger();
					JSONObject jsonObject = new JSONObject(response);
					String startTime = jsonObject.getString("startTime");
					String start[] = startTime.split(" ");
					String finishTime = jsonObject.getString("finishTime");
					String finish[] = finishTime.split(" ");
					// JSONObject jsonObject2 =
					// jsonObject.getJSONObject("goodsCategory");
					Object object = jsonObject.get("goodsCategory");
					String category = "";
					if (object instanceof JSONArray) {
						JSONArray array = jsonObject.getJSONArray("goodsCategory");					
						
						for (int i = 0; i < array.length(); i++) {
							JSONObject jsonObject2 = array.getJSONObject(i);
							category = category + jsonObject2.getString("name")
									+ ", ";
						}
					} else if (object instanceof JSONObject) {
						JSONObject jsonObject2 = jsonObject.getJSONObject("goodsCategory");
						category = category + jsonObject2.getString("name")
								+ ", ";
					}
					
					

					route.setStartingAddress(jsonObject
							.getString("startingAddress"));
					route.setDestinationAddress(jsonObject
							.getString("destinationAddress"));
					route.setStartTime(start[0]);
					route.setFinishTime(finish[0]);
					route.setCategory(category);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// String a = route.getStartingAddress();
				startAddr.setText("Địa điểm bắt đầu: "
						+ route.getStartingAddress());
				destAddr.setText("Địa điểm kết thúc: "
						+ route.getDestinationAddress());
				startTime.setText("Thời gian bắt đầu: " + route.getStartTime());
				finishTime.setText("Thời gian kết thúc: "
						+ route.getFinishTime());
				category.setText("Loại hàng không chở: " + route.getCategory());
			} else {
				Toast.makeText(SuggestDetailActivity.this,
						"Lộ trình không tồn tại", Toast.LENGTH_LONG).show();
			}

			pDlg.dismiss();
		}

		// Establish connection and socket (data retrieval) timeouts
		private HttpParams getHttpParams() {

			HttpParams htpp = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
			HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

			return htpp;
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

	}

	private class WebServiceTask2 extends AsyncTask<String, Integer, String> {

		public static final int POST_TASK = 1;
		public static final int GET_TASK = 2;

		private static final String TAG = "WebServiceTask";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 5000;

		private int taskType = GET_TASK;
		private Context mContext = null;
		private String processMessage = "Processing...";

		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		private ProgressDialog pDlg = null;

		public WebServiceTask2(int taskType, Context mContext,
				String processMessage) {

			this.taskType = taskType;
			this.mContext = mContext;
			this.processMessage = processMessage;
		}

		public void addNameValuePair(String name, String value) {

			params.add(new BasicNameValuePair(name, value));
		}

		private void showProgressDialog() {

			pDlg = new ProgressDialog(mContext);
			pDlg.setMessage(processMessage);
			pDlg.setProgressDrawable(mContext.getWallpaper());
			pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDlg.setCancelable(false);
			pDlg.show();

		}

		@Override
		protected void onPreExecute() {

			showProgressDialog();

		}

		protected String doInBackground(String... urls) {

			String url = urls[0];
			String result = "";

			HttpResponse response = doResponse(url);

			if (response.getEntity() == null) {
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

		@Override
		protected void onPostExecute(String response) {
			// Xu li du lieu tra ve sau khi insert thanh cong
			// handleResponse(response);
			if (response.equals("Success")) {
				Toast.makeText(SuggestDetailActivity.this,
						"Gửi đề nghị thành công", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(SuggestDetailActivity.this, SuggestActivity.class);
				intent.putExtra("goodsID", goodsID);
				startActivity(intent);
			} else {
				Toast.makeText(SuggestDetailActivity.this,
						"Gửi đề nghị thất bại", Toast.LENGTH_LONG).show();
			}
			pDlg.dismiss();
		}

		// Establish connection and socket (data retrieval) timeouts
		private HttpParams getHttpParams() {

			HttpParams htpp = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
			HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

			return htpp;
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

	}
}
