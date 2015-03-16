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

import vn.edu.fpt.fts.activity.DealDetailActivity;
import vn.edu.fpt.fts.activity.MainActivity;
import vn.edu.fpt.fts.activity.OrderDetailActivity;
import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.fragment.R;
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

public class AlarmReceiver extends BroadcastReceiver {
	Context con;
	private static int oldSize, newSize;
	String email;
	private static ArrayList<ListItem> list = new ArrayList<ListItem>();

	private static final String SERVICE_URL = Common.IP_URL
			+ Common.Service_Notification_getNotificationByEmail;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		con = context;
		WebService ws = new WebService(WebService.POST_TASK, context,
				"Đang lấy dữ liệu ...");
		SharedPreferences preferences = context.getSharedPreferences("MyPrefs",
				Context.MODE_PRIVATE);
		email = preferences.getString("email", "");
		ws.addNameValuePair("email", email);
		ws.execute(new String[] { SERVICE_URL });
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
							Object intervent = obj.get("notification");
							if (intervent instanceof JSONArray) {
								JSONArray array = obj
										.getJSONArray("notification");
								for (int i = array.length() - 1; i >= 0; i--) {
									JSONObject item = array.getJSONObject(i);
									list.add(new ListItem(item
											.getString("idOfType"), item
											.getString("message"), item
											.getString("type")));
									// list.add(new ListItem(item
									// .getString("message"), "", ""));
								}
							} else if (intervent instanceof JSONObject) {
								JSONObject item = obj
										.getJSONObject("notification");
								list.add(new ListItem(item
										.getString("idOfType"), item
										.getString("message"), item
										.getString("type")));
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
								for (int i = array.length() - 1; i >= 0; i--) {
									JSONObject item = array.getJSONObject(i);
									list.add(new ListItem(item
											.getString("idOfType"), item
											.getString("message"), item
											.getString("type")));
								}
							} else if (intervent instanceof JSONObject) {
								JSONObject item = obj
										.getJSONObject("notification");
								list.add(new ListItem(item
										.getString("idOfType"), item
										.getString("message"), item
										.getString("type")));
							}
							newSize = list.size();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (oldSize < newSize) {
							displayNotification();
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

	public void displayNotification() {
		String contentText = list.get(0).getMessage();
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				con).setSmallIcon(R.drawable.ic_action_alarms)
				.setContentTitle("Đề nghị").setContentText(contentText)
				.setAutoCancel(true).setTicker(contentText);
		Intent resultIntent = new Intent();
		int id = 0;
		if (list.get(0).getType().equals("deal")) {
			resultIntent = new Intent(con, DealDetailActivity.class);
			id = Integer.parseInt(list.get(0).getIdOfType());
			resultIntent.putExtra("dealID", id);

		} else if (list.get(0).getType().equals("order")) {
			resultIntent = new Intent(con, OrderDetailActivity.class);
			id = Integer.parseInt(list.get(0).getIdOfType());
			resultIntent.putExtra("orderID", id);
		}

		// Intent resultIntent = new Intent(con, MainActivity.class);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(con);

		stackBuilder.addParentStack(MainActivity.class);

		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
				id, PendingIntent.FLAG_UPDATE_CURRENT);

		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) con
				.getSystemService(Context.NOTIFICATION_SERVICE);

		mNotificationManager.notify(id, mBuilder.build());
	}
}
