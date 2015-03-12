package vn.edu.fpt.fts.layout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
import vn.edu.fpt.fts.drawer.ListItemAdapter2;
import vn.edu.fpt.fts.drawer.ListNotiAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Notification extends Fragment {

	ArrayList<ListItem> list;
	ArrayList<String> map;
	ListNotiAdapter adapter;
	ListView list1;
	View myFragmentView;
	private static final String SERVICE_URL = Constant.SERVICE_URL
			+ "DealNotification/getDealNotificationByDriverID";

	@SuppressLint("UseSparseArrays")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		list = new ArrayList<ListItem>();
		map = new ArrayList<String>();
		getActivity().getActionBar().setTitle("Thông báo");
		myFragmentView = inflater.inflate(R.layout.activity_notification,
				container, false);
		list1 = (ListView) myFragmentView.findViewById(R.id.ListNotification);
		list1.setOnItemClickListener((new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				int id = Integer.parseInt(map.get((int) arg3));
				FragmentManager mng = getActivity().getSupportFragmentManager();
				FragmentTransaction trs = mng.beginTransaction();
				CurrentRoute frag = new CurrentRoute();
				Bundle bundle = new Bundle();
				bundle.putString("routeID", String.valueOf(id));
				frag.setArguments(bundle);
				trs.replace(R.id.content_frame, frag);
				trs.addToBackStack(null);
				trs.commit();
			}
		}));
		WebService ws = new WebService(WebService.POST_TASK, getActivity(),
				"Đang lấy dữ liệu ...");
		ws.addNameValuePair("driverID", getActivity().getIntent()
				.getStringExtra("driverID"));
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
		private static final int SOCKET_TIMEOUT = 15000;

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
			try {
				list = new ArrayList<ListItem>();
				JSONObject obj = new JSONObject(response);
				Object intervent = obj.get("dealNotification");
				if (intervent instanceof JSONArray) {
					JSONArray array = obj.getJSONArray("dealNotification");
					for (int i = array.length() - 1; i >= 0; i--) {
						JSONObject item = array.getJSONObject(i);
						list.add(new ListItem(item.getString("message"), "", ""));
					}
				} else if (intervent instanceof JSONObject) {
					JSONObject item = obj.getJSONObject("dealNotification");
					list.add(new ListItem(item.getString("message"), "", ""));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			adapter = new ListNotiAdapter(getActivity(), list);
			String res = response;
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
