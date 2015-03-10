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

import vn.edu.fpt.fts.drawer.ListItem;
import vn.edu.fpt.fts.helper.ConnectivityHelper;
import vn.edu.fpt.fts.layout.Login;
import vn.edu.fpt.fts.layout.MainActivity;
import vn.edu.fpt.fts.layout.R;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

public class NotificationService extends Service {
	private WakeLock mWakeLock;
	ArrayList<ListItem> list = new ArrayList<ListItem>();
	int oldSize, newSize;
	
	private String SERVICE_URL = Constant.SERVICE_URL
			+ "DealNotification/getDealNotificationByDriverID";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void handleIntent(Intent intent) {
		PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "FLAG");
		mWakeLock.acquire();

		ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		if (!cm.getBackgroundDataSetting()) {
			stopSelf();
			return;
		}

		new WebService().execute(SERVICE_URL);
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
			if (ConnectivityHelper.CheckConnectivity(getBaseContext())) {
				String url = urls[0];

				HttpResponse response = doResponse(url);

				if (response.getEntity() == null) {
					return result;
				} else {
					try {
						result = inputStreamToString(response.getEntity()
								.getContent());

					} catch (IllegalStateException e) {

					} catch (IOException e) {

					}

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
											.getString("message"), "", ""));
								}
							} else if (intervent instanceof JSONObject) {
								JSONObject item = obj
										.getJSONObject("dealNotification");
								list.add(new ListItem(
										item.getString("message"), "", ""));
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
											.getString("message"), "", ""));
								}
							} else if (intervent instanceof JSONObject) {
								JSONObject item = obj
										.getJSONObject("dealNotification");
								list.add(new ListItem(
										item.getString("message"), "", ""));
							}
							newSize = list.size();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (oldSize < newSize) {
							Toast.makeText(getBaseContext(),
									"Có thông báo mới", Toast.LENGTH_LONG)
									.show();
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

	/**
	 * This is deprecated, but you have to implement it if you're planning on
	 * supporting devices with an API level lower than 5 (Android 2.0).
	 */
	@Override
	public void onStart(Intent intent, int startId) {
		handleIntent(intent);
	}

	/**
	 * This is called on 2.0+ (API level 5 or higher). Returning
	 * START_NOT_STICKY tells the system to not restart the service if it is
	 * killed because of poor resource (memory/cpu) conditions.
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		handleIntent(intent);
		return START_NOT_STICKY;
	}

	/**
	 * In onDestroy() we release our wake lock. This ensures that whenever the
	 * Service stops (killed for resources, stopSelf() called, etc.), the wake
	 * lock will be released.
	 */
	public void onDestroy() {
		super.onDestroy();
		mWakeLock.release();
	}
	
	public void displayNotification() {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setTicker("Alert!")
				.setSmallIcon(R.drawable.ic_action_alarms)
				.setContentTitle("Thông báo")
				.setContentText("Có thông báo mới");

		Intent resultIntent = new Intent(this, Login.class);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

		stackBuilder.addParentStack(Login.class);

		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		mNotificationManager.notify(0, mBuilder.build());
	}
}
