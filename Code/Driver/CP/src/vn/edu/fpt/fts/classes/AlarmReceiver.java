package vn.edu.fpt.fts.classes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import vn.edu.fpt.fts.drawer.ListItem;
import vn.edu.fpt.fts.helper.ConnectivityHelper;
import vn.edu.fpt.fts.layout.MainActivity;
import vn.edu.fpt.fts.layout.R;

public class AlarmReceiver extends BroadcastReceiver {
	private class WebService extends AsyncTask<String, Integer, String> {

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 30000;
		public static final int GET_TASK = 2;

		public static final int POST_TASK = 1;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 30000;

		private static final String TAG = "WebServiceTask";

		private Context mContext = null;
		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		private ProgressDialog pDlg = null;

		private String processMessage = "Processing...";

		private int taskType = GET_TASK;

		public WebService(int taskType, Context mContext, String processMessage) {

			this.taskType = taskType;
			this.mContext = mContext;
			this.processMessage = processMessage;
		}

		public void addNameValuePair(String name, String value) {

			params.add(new BasicNameValuePair(name, value));
		}

		@Override
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

			}

			// Return full string
			return total.toString();
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
							Object intervent = obj.get("notification");
							if (intervent instanceof JSONArray) {
								JSONArray array = obj
										.getJSONArray("notification");
								for (int i = 0; i < array.length(); i++) {
									JSONObject item = array.getJSONObject(i);
									list.add(new ListItem(item
											.getString("message"), item
											.getString("idOfType"), item
											.getString("statusOfType"), item
											.getString("type")));
								}
							} else if (intervent instanceof JSONObject) {
								JSONObject item = obj
										.getJSONObject("notification");
								list.add(new ListItem(
										item.getString("message"), item
												.getString("idOfType"), item
												.getString("statusOfType"),
										item.getString("type")));
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
							Object intervent = obj.get("notification");
							if (intervent instanceof JSONArray) {
								JSONArray array = obj
										.getJSONArray("notification");
								for (int i = 0; i < array.length(); i++) {
									JSONObject item = array.getJSONObject(i);
									list.add(new ListItem(item
											.getString("message"), item
											.getString("idOfType"), item
											.getString("statusOfType"), item
											.getString("type")));
								}
							} else if (intervent instanceof JSONObject) {
								JSONObject item = obj
										.getJSONObject("notification");
								list.add(new ListItem(
										item.getString("message"), item
												.getString("idOfType"), item
												.getString("statusOfType"),
										item.getString("type")));
							}
							newSize = list.size();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (oldSize < newSize) {
							for (int i = 0; i < (newSize - oldSize); i++) {
								displayNotification(list.get(i).getInfo(), list
										.get(i).getTitle(), list.get(i)
										.getDescription(), list.get(i)
										.getDate());
							}
						}
					}
				}
			}
		}

		@Override
		protected void onPreExecute() {
			showProgressDialog();

		}

		private void showProgressDialog() {

		}
	}
	public static ArrayList<ListItem> list = new ArrayList<ListItem>();
	private static int oldSize, newSize;
	private static final String SERVICE_URL = Constant.SERVICE_URL
			+ "Notification/getNotificationByEmail";
	Context con;

	String email, id;

	Intent resultIntent;

	public void displayNotification(String message, String dealID,
			String status, String type) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				con).setTicker("Thông báo!")
				.setSmallIcon(R.drawable.ic_stat_untitled_1)
				.setContentTitle("Thông báo").setContentText(message);

		resultIntent = new Intent(con, MainActivity.class);
		resultIntent.putExtra("driverID", id);
		resultIntent.putExtra("email", email);
		resultIntent.putExtra("dealID", dealID);
		resultIntent.putExtra("status", status);
		resultIntent.putExtra("type", type);

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

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		con = context;
		if (ConnectivityHelper.CheckConnectivity(context)) {
			WebService ws = new WebService(WebService.POST_TASK, context,
					"Đang lấy dữ liệu ...");
			id = intent.getExtras().getString("driverID");
			SharedPreferences share = context.getSharedPreferences("driver",
					Context.MODE_PRIVATE);
			email = share.getString("email", "");
			ws.addNameValuePair("email", email);
			ws.execute(new String[] { SERVICE_URL });
		}
	}
}
