package vn.edu.fpt.fts.layout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.edu.fpt.fts.classes.Constant;
import vn.edu.fpt.fts.drawer.ListItem;
import vn.edu.fpt.fts.drawer.ListItemAdapter;
import vn.edu.fpt.fts.drawer.ListItemAdapter2;
import vn.edu.fpt.fts.drawer.ListItemAdapter3;
import vn.edu.fpt.fts.drawer.ListItemAdapter5;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SystemSuggest extends Fragment {

	ArrayList<ListItem> list;
	ListItemAdapter5 adapter;
	@SuppressLint("UseSparseArrays")
	HashMap<Long, Integer> map = new HashMap<Long, Integer>();
	ListView list1;
	View myFragmentView;
	TextView title;
	private static final String SERVICE_URL = Constant.SERVICE_URL
			+ "Goods/getSuggestionGoods";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		list = new ArrayList<ListItem>();
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		String route = getArguments().getString("route");
		getActivity().getActionBar().setTitle("Gợi ý");
		getActivity().getActionBar().setIcon(R.drawable.ic_action_copy_white);
		myFragmentView = inflater.inflate(R.layout.activity_system_suggest,
				container, false);
		list1 = (ListView) myFragmentView.findViewById(R.id.listView1);
		title = (TextView) myFragmentView.findViewById(R.id.txtTitle);
		title.setText("Lộ trình: " + route);
		list1.setOnItemClickListener((new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				int id = map.get(arg3);
				FragmentManager mng = getActivity().getSupportFragmentManager();
				FragmentTransaction trs = mng.beginTransaction();
				SendOffer frag = new SendOffer();
				Bundle bundle = new Bundle();
				bundle.putString("goodID", String.valueOf(id));
				bundle.putString("routeID", getArguments().getString("routeID"));
				frag.setArguments(bundle);
				trs.replace(R.id.content_frame, frag);
				trs.addToBackStack(null);
				trs.commit();
			}
		}));
		WebService ws = new WebService(WebService.POST_TASK, getActivity(),
				"Đang lấy dữ liệu ...");
		ws.addNameValuePair("routeID", getArguments().getString("routeID"));
		ws.execute(new String[] { SERVICE_URL });
		return myFragmentView;
	}

	private class WebService extends AsyncTask<String, Integer, String> {

		public static final int POST_TASK = 1;
		public static final int GET_TASK = 2;

		private static final String TAG = "WebServiceTask";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 30000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 30000;

		private int taskType = GET_TASK;
		private Context mContext = null;
		private String processMessage = "Processing...";

		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		private ProgressDialog pDlg = null;

		public WebService(int taskType, Context mContext, String processMessage) {

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
			JSONObject obj;
			if (!response.equals("null")) {
				try {
					obj = new JSONObject(response);
					if (obj.has("goods")) {
						Object intervent = obj.get("goods");
						DecimalFormat formatter = new DecimalFormat();
						DecimalFormatSymbols symbol = new DecimalFormatSymbols();
						symbol.setGroupingSeparator('.');
						formatter.setDecimalFormatSymbols(symbol);
						if (intervent instanceof JSONArray) {
							JSONArray array = obj.getJSONArray("goods");
							for (int i = 0; i < array.length(); i++) {
								JSONObject item = array.getJSONObject(i);
								String title = "";
								String[] start = item
										.getString("pickupAddress")
										.replaceAll("(?i), Vietnam", "")
										.replaceAll("(?i), Viet Nam", "")
										.replaceAll("(?i), Việt Nam", "")
										.split(",");
								title = start[start.length - 1].trim();

								String[] end = item
										.getString("deliveryAddress")
										.replaceAll("(?i), Vietnam", "")
										.replaceAll("(?i), Viet Nam", "")
										.replaceAll("(?i), Việt Nam", "")
										.split(",");
								title += " - " + end[end.length - 1].trim();
								list.add(new ListItem(item.getJSONObject(
										"goodsCategory").getString("name")
										+ " "
										+ item.getString("weight")
										+ " kg", title, "Giá của chủ hàng: "
										+ formatter.format(Integer
												.parseInt(item.getString(
														"price").replace(".0",
														"")
														+ "000")) + " đồng"));
								map.put(Long.valueOf(i), Integer.parseInt(item
										.getString("goodsID")));
							}
						} else if (intervent instanceof JSONObject) {
							JSONObject item = obj.getJSONObject("goods");
							String title = "";
							String[] start = item.getString("pickupAddress")
									.replaceAll("(?i), Vietnam", "")
									.replaceAll("(?i), Viet Nam", "")
									.replaceAll("(?i), Việt Nam", "")
									.split(",");
							title = start[start.length - 1].trim();

							String[] end = item.getString("deliveryAddress")
									.replaceAll("(?i), Vietnam", "")
									.replaceAll("(?i), Viet Nam", "")
									.replaceAll("(?i), Việt Nam", "")
									.split(",");
							title += " - " + end[end.length - 1].trim();
							list.add(new ListItem(item.getJSONObject(
									"goodsCategory").getString("name")
									+ " " + item.getString("weight") + " kg",
									title, "Giá của chủ hàng: "
											+ formatter.format(Integer
													.parseInt(item.getString(
															"price").replace(
															".0", "")
															+ "000")) + " đồng"));
							map.put(Long.valueOf(0),
									Integer.parseInt(item.getString("goodsID")));
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			adapter = new ListItemAdapter5(getActivity(), list);
			list1.setEmptyView(myFragmentView.findViewById(R.id.emptyElement));
			list1.setAdapter(adapter);
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
			} catch (ConnectTimeoutException e) {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(),
								"Không thể kết nối tới máy chủ",
								Toast.LENGTH_SHORT).show();
					}
				});
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
