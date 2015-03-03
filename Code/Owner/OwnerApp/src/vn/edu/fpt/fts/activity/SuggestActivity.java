package vn.edu.fpt.fts.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

import vn.edu.fpt.fts.adapter.Model;
import vn.edu.fpt.fts.adapter.ModelAdapter;
import vn.edu.fpt.fts.classes.Route;
import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.ownerapp.R;
import android.app.Activity;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SuggestActivity extends Activity {
	private List<Route> list = new ArrayList<Route>();
	private ListView listView;
	private ModelAdapter adapter;
	private String goodsID;
	private TextView tvGone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggest);
		goodsID = getIntent().getStringExtra("goodsID");

		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK,
				SuggestActivity.this, "Đang xử lý...");
		String url = Common.IP_URL + Common.Service_Suggest_Route;
		wst.addNameValuePair("goodsID", goodsID);
		wst.execute(new String[] { url });

		listView = (ListView) findViewById(R.id.listview_suggest);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				int pos = listView.getPositionForView(view);
				Route route = list.get(pos);
				int routeId = route.getRouteID();
				Intent intent = new Intent(SuggestActivity.this,
						SuggestDetailActivity.class);
				intent.putExtra("route", routeId);
				intent.putExtra("goodsID", goodsID);
				startActivity(intent);
			}
		});

	}

	private ArrayList<Model> generateData() {
		// TODO Auto-generated method stub
		ArrayList<Model> models = new ArrayList<Model>();

		for (Route route : list) {
			String start = route.getStartingAddress().replace(", Vietnam", "");
			start = route.getStartingAddress().replace(", Việt Nam", "");
			String[] strings = start.split(",");
			String end = route.getDestinationAddress().replace(", Vietnam", "");
			end = route.getDestinationAddress().replace(", Việt Nam", "");
			String[] strings2 = end.split(",");
			models.add(new Model(strings[strings.length - 1] + " - "
					+ strings2[strings2.length - 1], "1"));
		}

		return models;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.suggest, menu);
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
		if (id == R.id.action_homepage) {
			Intent intent = new Intent(SuggestActivity.this, MainActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	private class WebServiceTask extends AsyncTask<String, Integer, String> {

		public static final int POST_TASK = 1;
		public static final int GET_TASK = 2;

		private static final String TAG = "WebServiceTask";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 100000;

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

		@Override
		protected void onPostExecute(String response) {
			// Xu li du lieu tra ve sau khi insert thanh cong
			// handleResponse(response);
			if (response.equals("null")) {
				pDlg.dismiss();
//				Toast.makeText(SuggestActivity.this,
//						"Không tìm thấy lộ trình phù hợp", Toast.LENGTH_LONG)
//						.show();
				tvGone = (TextView) findViewById(R.id.textview_gone);
				tvGone.setVisibility(View.VISIBLE);
			} else {
				try {
					// android.os.Debug.waitForDebugger();
					JSONObject jsonObject = new JSONObject(response);
					Object object = jsonObject.get("route");
					if (object instanceof JSONArray) {
						JSONArray array = jsonObject.getJSONArray("route");

						for (int i = 0; i < array.length(); i++) {
							Route route = new Route();
							JSONObject jsonObject2 = array.getJSONObject(i);
							route.setRouteID(Integer.parseInt(jsonObject2
									.getString("routeID")));
							route.setStartingAddress(jsonObject2
									.getString("startingAddress"));
							route.setDestinationAddress(jsonObject2
									.getString("destinationAddress"));
							route.setStartTime(jsonObject2
									.getString("startTime"));
							route.setFinishTime(jsonObject2
									.getString("finishTime"));
							try {
								route.setNotes(jsonObject2.getString("notes"));
							} catch (JSONException e) {
								route.setNotes("");
							}
							route.setWeight(Integer.parseInt(jsonObject2
									.getString("weight")));
							route.setCreateTime(jsonObject2
									.getString("createTime"));
							route.setActive(Integer.parseInt(jsonObject2
									.getString("active")));
							route.setDriverID(Integer.parseInt(jsonObject2
									.getString("driverID")));
							list.add(route);
						}
					} else if (object instanceof JSONObject) {
						Route route = new Route();
						JSONObject jsonObject2 = jsonObject
								.getJSONObject("route");
						route.setRouteID(Integer.parseInt(jsonObject2
								.getString("routeID")));
						route.setStartingAddress(jsonObject2
								.getString("startingAddress"));
						route.setDestinationAddress(jsonObject2
								.getString("destinationAddress"));
						route.setStartTime(jsonObject2.getString("startTime"));
						route.setFinishTime(jsonObject2.getString("finishTime"));
						try {
							route.setNotes(jsonObject2.getString("notes"));
						} catch (JSONException e) {
							route.setNotes("");
						}
						route.setWeight(Integer.parseInt(jsonObject2
								.getString("weight")));
						route.setCreateTime(jsonObject2.getString("createTime"));
						route.setActive(Integer.parseInt(jsonObject2
								.getString("active")));
						route.setDriverID(Integer.parseInt(jsonObject2
								.getString("driverID")));
						list.add(route);
					}

				} catch (JSONException e) {
					Log.e(TAG, e.getLocalizedMessage());
					Toast.makeText(SuggestActivity.this,
							"Không tìm thấy lộ trình phù hợp",
							Toast.LENGTH_LONG).show();
				}
				pDlg.dismiss();
				adapter = new ModelAdapter(SuggestActivity.this, generateData());

				listView.setAdapter(adapter);

			}

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
