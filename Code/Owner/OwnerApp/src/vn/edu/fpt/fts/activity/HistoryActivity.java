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

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import vn.edu.fpt.fts.classes.Deal;
import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.fragment.R;

public class HistoryActivity extends Activity {
	private class WebServiceTask extends AsyncTask<String, Integer, String> {

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;
		public static final int GET_TASK = 2;

		public static final int POST_TASK = 1;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 50000;

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
			if (response.equals("null")) {
				tvGone = (TextView) findViewById(R.id.textview_gone);
				tvGone.setVisibility(View.VISIBLE);
			} else {
				try {
					JSONObject jsonObject = new JSONObject(response);
					Object obj = jsonObject.get("deal");
					String[] price = null;
					if (obj instanceof JSONArray) {
						JSONArray array = jsonObject.getJSONArray("deal");
						list = new ArrayList<Deal>();

						for (int i = 0; i < array.length(); i++) {
							JSONObject jsonObject2 = array.getJSONObject(i);
							Deal deal = new Deal();
							int check = Integer.parseInt(jsonObject2
									.getString("dealStatusID"));
							int check2 = Integer.parseInt(jsonObject2
									.getString("active"));
							if (check != 1 && check2 != 0 && check2 != 2) {
								deal.setActive(Integer.parseInt(jsonObject2
										.getString("active")));
								deal.setCreateBy(jsonObject2
										.getString("createBy"));
								deal.setCreateTime(jsonObject2
										.getString("createTime"));
								deal.setDealID(Integer.parseInt(jsonObject2
										.getString("dealID")));
								deal.setDealStatusID(Integer
										.parseInt(jsonObject2
												.getString("dealStatusID")));
								deal.setGoodsID(Integer.parseInt(jsonObject2
										.getString("goodsID")));
								deal.setNotes(jsonObject2.getString("notes"));
								deal.setPrice(Double.parseDouble(jsonObject2
										.getString("price")));								
								deal.setRouteID(Integer.parseInt(jsonObject2
										.getString("routeID")));
								list.add(deal);
							}

						}
					} else if (obj instanceof JSONObject) {
						// price = new String[1];
						JSONObject jsonObject2 = jsonObject
								.getJSONObject("deal");
						list = new ArrayList<Deal>();
						Deal deal = new Deal();
						int check = Integer.parseInt(jsonObject2
								.getString("dealStatusID"));
						int check2 = Integer.parseInt(jsonObject2
								.getString("active"));
						if (check != 1 && check2 != 0 && check2 != 2) {
							deal.setActive(Integer.parseInt(jsonObject2
									.getString("active")));
							deal.setCreateBy(jsonObject2.getString("createBy"));
							deal.setCreateTime(jsonObject2
									.getString("createTime"));
							deal.setDealID(Integer.parseInt(jsonObject2
									.getString("dealID")));
							deal.setDealStatusID(Integer.parseInt(jsonObject2
									.getString("dealStatusID")));
							deal.setGoodsID(Integer.parseInt(jsonObject2
									.getString("goodsID")));
							deal.setNotes(jsonObject2.getString("notes"));
							deal.setPrice(Double.parseDouble(jsonObject2
									.getString("price")));							
							deal.setRouteID(Integer.parseInt(jsonObject2
									.getString("routeID")));
							list.add(deal);
						}
					}

					// for (Deal d : list) {
					// if (d.getDealStatusID() == 3 || d.getDealStatusID() == 4
					// || d.getActive() == 0) {
					// list.remove(d);
					// }
					// }
					int size = list.size();
					if (size != 0) {
						price = new String[list.size()];

						for (int i = 0; i < list.size(); i++) {
							price[i] = "Giao dịch giá: "
									+ (int) list.get(i).getPrice()
									+ " nghìn đồng";
						}
						// String a = price[0];

						if (price[0] == null) {
							Toast.makeText(HistoryActivity.this,
									"Không có lịch sử giao dịch mới",
									Toast.LENGTH_LONG).show();
						} else {
							adapter = new ArrayAdapter<String>(
									HistoryActivity.this,
									android.R.layout.simple_list_item_1, price);
							listView.setAdapter(adapter);
						}
					} else {
						tvGone = (TextView) findViewById(R.id.textview_gone);
						tvGone.setVisibility(View.VISIBLE);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Toast.makeText(HistoryActivity.this,
							"Không có lịch sử giao dịch mới", Toast.LENGTH_LONG)
							.show();
				}
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
	private List<Deal> list;
	private ListView listView;
	private String ownerID;

	private TextView tvGone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);

		listView = (ListView) findViewById(R.id.listview_deal_list);
		SharedPreferences preferences = getSharedPreferences("MyPrefs",
				Context.MODE_PRIVATE);
		ownerID = preferences.getString("ownerName", "");
		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK, this,
				"Đang xử lý...");
		wst.addNameValuePair("ownerID", ownerID);
		String url = Common.IP_URL + Common.Service_Deal_getDealByOwnerID;
		wst.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
				new String[] { url });

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				int pos = listView.getPositionForView(view);
				int dealID = list.get(pos).getDealID();			
				int dealStatus = list.get(pos).getDealStatusID();
				String createBy = list.get(pos).getCreateBy();
				int routeID = list.get(pos).getRouteID();
				int goodsID = list.get(pos).getGoodsID();
				double price = list.get(pos).getPrice();
				String note = list.get(pos).getNotes();
				Intent intent = new Intent(HistoryActivity.this,
						DealDetailActivity.class);
				intent.putExtra("dealID", dealID);				
				intent.putExtra("dealStatus", dealStatus);
				intent.putExtra("createBy", createBy);
				intent.putExtra("routeID", routeID);
				intent.putExtra("goodsID", goodsID);
				intent.putExtra("price", price);
				intent.putExtra("note", note);
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.history, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onKeyDown(keyCode, event);
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
		if (id == android.R.id.home) {
			Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
			startActivity(intent);
		}		
		return super.onOptionsItemSelected(item);
	}
}
