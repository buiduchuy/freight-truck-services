package vn.edu.fpt.fts.layout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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

import vn.edu.fpt.fts.classes.Constant;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CurrentRoute extends Fragment {

	private static final String SERVICE_URL = Constant.SERVICE_URL
			+ "Route/getRouteByID";
	TextView contentView;
	String id;
	String part1;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getActivity().setTitle("Thông tin lộ trình");
		View v = inflater.inflate(R.layout.activity_current_route, container,
				false);
		contentView = (TextView) v.findViewById(R.id.textView3);
		Bundle bundle = getArguments();
		id = bundle.getString("routeID");
		WebService ws = new WebService(WebService.POST_TASK, getActivity(),
				"Đang xử lý ...");
		ws.addNameValuePair("routeID", id);
		ws.execute(new String[] { SERVICE_URL });
		Button button = (Button) v.findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager mng = getActivity().getSupportFragmentManager();
				FragmentTransaction trs = mng.beginTransaction();
				ChangeRoute frag = new ChangeRoute();
				Bundle bundle = new Bundle();
				bundle.putString("id", id);
				frag.setArguments(bundle);
				trs.replace(R.id.content_frame, frag, "changeRoute");
				trs.addToBackStack("changeRoute");
				trs.commit();
			}
		});
		Button button2 = (Button) v.findViewById(R.id.button2);
		button2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		return v;
	}

	private class WebService extends AsyncTask<String, Integer, String> {

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
			JSONObject obj;
			try {
				obj = new JSONObject(response);
				Object intervent;
				part1 = "Địa điểm bắt đầu: " + obj.getString("startingAddress")
						+ "\n";

				if (obj.has("routeMarkers")) {
					intervent = obj.get("routeMarkers");
					if (intervent instanceof JSONArray) {
						JSONArray catArray = obj.getJSONArray("routeMarkers");
						for (int j = 0; j < catArray.length(); j++) {
							JSONObject cat = catArray.getJSONObject(j);
							part1 += "Địa điểm đi qua " + (j + 1) + ": "
									+ cat.getString("routeMarkerLocation")
									+ "\n";
						}
					} else if (intervent instanceof JSONObject) {
						JSONObject cat = obj.getJSONObject("routeMarkers");
						part1 += "Địa điểm đi qua 1: "
								+ cat.getString("routeMarkerLocation") + "\n";
					}
				}

				part1 += "Địa điểm kết thúc: "
						+ obj.getString("destinationAddress") + "\n"
						+ "Thời gian bắt đầu: " + obj.getString("startTime")
						+ "\n" + "Thời gian kết thúc: "
						+ obj.getString("finishTime") + "\n"
						+ "Khối lượng có thể chở: " + obj.getString("weight")
						+ " tấn\nLoại hàng không chở: ";

				if (obj.has("goodsCategory")) {
					intervent = obj.get("goodsCategory");
					if (intervent instanceof JSONArray) {
						JSONArray goodArray = obj.getJSONArray("goodsCategory");
						for (int j = 0; j < goodArray.length(); j++) {
							JSONObject good = goodArray.getJSONObject(j);
							part1 += good.getString("name") + ", ";
						}
						part1 = part1.substring(0, part1.length() - 2);
					} else if (intervent instanceof JSONObject) {
						JSONObject good = obj.getJSONObject("goodsCategory");
						part1 += good.getString("name");
					}
				} else {
					part1 += "không có loại hàng nào";
				}

				contentView.setText(part1);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
