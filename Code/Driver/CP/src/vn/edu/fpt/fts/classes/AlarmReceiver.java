package vn.edu.fpt.fts.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

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

import vn.edu.fpt.fts.drawer.ListItem;
import vn.edu.fpt.fts.helper.ConnectivityHelper;
import vn.edu.fpt.fts.layout.Login;
import vn.edu.fpt.fts.layout.MainActivity;
import vn.edu.fpt.fts.layout.R;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
	Context con;
	Intent resultIntent;
	private static int oldSize, newSize;
	String id;
	private static ArrayList<ListItem> list = new ArrayList<ListItem>();

	private static final String SERVICE_URL = Constant.SERVICE_URL
			+ "DealNotification/getDealNotificationByDriverID";
	private static final String SERVICE_URL2 = Constant.SERVICE_URL
			+ "Deal/getDealByID";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		con = context;
		if (ConnectivityHelper.CheckConnectivity(context)) {
			WebService ws = new WebService(WebService.POST_TASK, context,
					"Đang lấy dữ liệu ...");
			id = intent.getExtras().getString("driverID");
			ws.addNameValuePair("driverID",
					intent.getExtras().getString("driverID"));
			ws.execute(new String[] { SERVICE_URL });
		}
	}

	private class WebService extends AsyncTask<String, Integer, String> {

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

		public WebService(int taskType, Context mContext, String processMessage) {

			this.taskType = taskType;
			this.mContext = mContext;
			this.processMessage = processMessage;
		}

		public void addNameValuePair(String name, String value) {

			params.add(new BasicNameValuePair(name, value));
		}

		private void showProgressDialog() {

		}

		@Override
		protected void onPreExecute() {
			showProgressDialog();

		}

		protected String doInBackground(String... urls) {
			String result = "";
			String url = urls[0];

			HttpResponse response = doResponse(url);

			if (response == null) {
				return result;
			} else {
				try {

					result = inputStreamToString(response.getEntity()
							.getContent());

				} catch (IllegalStateException e) {

				} catch (IOException e) {

				}

			}
			return result;
		}

		@Override
		protected void onPostExecute(String response) {
			// Xu li du lieu tra ve sau khi insert thanh cong
			// handleResponse(response);
			if (!response.equals("") && !response.equals("null")) {
				if (list != null) {
					oldSize = list.size();
					if (oldSize == 0) {
						try {
							list = new ArrayList<ListItem>();
							JSONObject obj = new JSONObject(response);
							Object intervent = obj.get("dealNotification");
							if (intervent instanceof JSONArray) {
								JSONArray array = obj
										.getJSONArray("dealNotification");
								for (int i = array.length() - 1; i >= 0; i--) {
									JSONObject item = array.getJSONObject(i);
									list.add(new ListItem(item
											.getString("message"), item
											.getString("dealID")));
								}
							} else if (intervent instanceof JSONObject) {
								JSONObject item = obj
										.getJSONObject("dealNotification");
								list.add(new ListItem(item
										.getString("message"), item
										.getString("dealID")));
							}
							oldSize = list.size();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						try {
							list = new ArrayList<ListItem>();
							JSONObject obj = new JSONObject(response);
							Object intervent = obj.get("dealNotification");
							if (intervent instanceof JSONArray) {
								JSONArray array = obj
										.getJSONArray("dealNotification");
								for (int i = array.length() - 1; i >= 0; i--) {
									JSONObject item = array.getJSONObject(i);
									list.add(new ListItem(item
											.getString("message"), item
											.getString("dealID")));
								}
							} else if (intervent instanceof JSONObject) {
								JSONObject item = obj
										.getJSONObject("dealNotification");
								list.add(new ListItem(item
										.getString("message"), item
										.getString("dealID")));
							}
							newSize = list.size();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (oldSize < newSize) {
							for (int i = 0; i < (newSize - oldSize); i++) {
								WebService2 ws = new WebService2(WebService.POST_TASK, con,
										"Đang lấy dữ liệu ...");
								ws.addNameValuePair("dealID", list.get(i).getTitle());
								ws.addNameValuePair("message", list.get(i).getInfo());
								ws.execute(new String[] { SERVICE_URL2 });
							}
						}
					}
				}
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
		private static final int CONN_TIMEOUT = 30000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 15000;

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
			try {
				obj = new JSONObject(response);
				String dealID = params.get(0).getValue();
				String message = params.get(1).getValue();
				String status = obj.getString("dealStatusID");
				String sender = obj.getString("createBy");
				displayNotification(message, dealID, status, sender);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			} catch (ConnectTimeoutException e) {
//					runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//						Toast.makeText(getActivity(),
//								"Không thể kết nối tới máy chủ",
//								Toast.LENGTH_SHORT).show();
//					}
//				});
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

	public void displayNotification(String message, String dealID, String status, String sender) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				con).setTicker("Thông báo!")
				.setSmallIcon(R.drawable.ic_action_alarms_white)
				.setContentTitle("Thông báo")
				.setContentText(message);

		resultIntent = new Intent(con, MainActivity.class);
		resultIntent.putExtra("driverID", id);
		resultIntent.putExtra("dealID", dealID);
		resultIntent.putExtra("status", status);
		resultIntent.putExtra("sender", sender);
		
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(con);

		stackBuilder.addParentStack(MainActivity.class);

		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) con
				.getSystemService(Context.NOTIFICATION_SERVICE);

		mNotificationManager.notify(Integer.parseInt(dealID), mBuilder.build());
	}
}
