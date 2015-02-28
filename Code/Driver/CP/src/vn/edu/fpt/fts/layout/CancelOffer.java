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
import vn.edu.fpt.fts.helper.Common;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CancelOffer extends Fragment {

	private static final String SERVICE_URL = Constant.SERVICE_URL
			+ "Deal/getDealByID";

	private static final String SERVICE_URL2 = Constant.SERVICE_URL
			+ "Deal/cancel";

	TextView startPlace, endPlace, startTime, endTime, price, note, weight;
	String routeID, goodID, refID;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getActivity().setTitle("Thông tin đề nghị");
		WebService ws = new WebService(WebService.POST_TASK, getActivity(),
				"Đang xử lý ...");
		ws.addNameValuePair("dealID", getArguments().getString("dealID"));
		ws.execute(new String[] { SERVICE_URL });
		View v = inflater.inflate(R.layout.activity_cancel_offer, container,
				false);
		startPlace = (TextView) v.findViewById(R.id.textView2);
		endPlace = (TextView) v.findViewById(R.id.textView4);
		startTime = (TextView) v.findViewById(R.id.textView6);
		endTime = (TextView) v.findViewById(R.id.textView8);
		price = (TextView) v.findViewById(R.id.textView10);
		note = (TextView) v.findViewById(R.id.textView12);
		weight = (TextView) v.findViewById(R.id.textView14);
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
				JSONObject good = obj.getJSONObject("goods");
				startPlace.setText(good.getString("pickupAddress"));
				endPlace.setText(good.getString("deliveryAddress"));
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				Date start = format.parse(good.getString("pickupTime"));
				Date end = format.parse(good.getString("deliveryTime"));
				format.applyPattern("dd/MM/yyyy");
				startTime.setText(format.format(start));
				endTime.setText(format.format(end));
				price.setText((int) Double.parseDouble(obj.getString("price"))
						+ " ngàn đồng");
				weight.setText(good.getString("weight") + " kg");
				if (good.has("notes")) {
					if (good.getString("notes").equals("")
							|| good.getString("notes").equalsIgnoreCase("null")) {
						note.setText("Không có");
					} else {
						note.setText(good.getString("notes"));
					}
				} else {
					note.setText("Không có");
				}
				routeID = obj.getString("routeID");
				goodID = obj.getString("goodsID");
				refID = obj.getString("refDealID");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
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

	private class WebService2 extends AsyncTask<String, Integer, String> {

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

		public WebService2(int taskType, Context mContext, String processMessage) {

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
			if (Integer.parseInt(response) > 0) {
				Toast.makeText(getActivity(), "Hủy đề nghị thành công",
						Toast.LENGTH_SHORT).show();
				FragmentManager mng = getActivity().getSupportFragmentManager();
				FragmentTransaction trs = mng.beginTransaction();
				Fragment fragment = new TabDeals();
				trs.replace(R.id.content_frame, fragment);
				trs.addToBackStack(null);
				trs.commit();
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

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menu.findItem(R.id.action_create).setVisible(false);
		MenuItem item = menu.add(Menu.NONE, R.id.action_cancelOffer, 99,
				R.string.delete);
		item.setActionView(R.layout.actionbar_cancel);
		item.getActionView().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				WebService2 ws = new WebService2(WebService.POST_TASK,
						getActivity(), "Đang xử lý ...");
				ws.addNameValuePair("dealID", getArguments()
						.getString("dealID"));
				ws.addNameValuePair("price", price.getText().toString()
						.replace(" ngàn đồng", ""));
				ws.addNameValuePair("notes", note.getText().toString());
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm");
				String createTime = format.format(Calendar.getInstance()
						.getTime());
				ws.addNameValuePair("createTime", createTime);
				ws.addNameValuePair("createBy", "driver");
				ws.addNameValuePair("routeID", routeID);
				ws.addNameValuePair("goodsID", goodID);
				if (refID.equals("0")) {
					ws.addNameValuePair("refDealID", "");
				} else {
					ws.addNameValuePair("refDealID", refID);
				}
				ws.addNameValuePair("active", "1");
				ws.execute(new String[] { SERVICE_URL2 });
			}
		});
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT
				| MenuItem.SHOW_AS_ACTION_ALWAYS);
		item.setIcon(R.drawable.ic_action_discard);
		super.onCreateOptionsMenu(menu, inflater);
	}
}
