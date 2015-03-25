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

import com.google.android.gms.internal.hi;

import vn.edu.fpt.fts.drawer.ListItem;
import vn.edu.fpt.fts.helper.Common;
import vn.edu.fpt.fts.layout.HistoryDetail;
import vn.edu.fpt.fts.layout.MainActivity;
import vn.edu.fpt.fts.layout.R;

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

public class OrderAlarmReceiver extends BroadcastReceiver {
	Context con;
	String driverID;
	private static ArrayList<ListItem> orderList = new ArrayList<ListItem>();
	
	private static final String SERVICE_URL = Constant.SERVICE_URL
			+ "Order/getOrderByDriverID";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		con = context;
		driverID = intent.getExtras().getString("driverID");
		WebService2 ws2 = new WebService2(WebService2.POST_TASK, context,
				"Đang xử lý...");
		ws2.addNameValuePair("driverID", driverID);
		ws2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
				new String[] { SERVICE_URL });
	}

	private class WebService2 extends AsyncTask<String, Integer, String> {

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
				try {
					JSONObject jsonObject = new JSONObject(response);
					Object obj = jsonObject.get("order");
					if (obj instanceof JSONArray) {
						JSONArray array = jsonObject.getJSONArray("order");
						for (int i = 0; i < array.length(); i++) {
							JSONObject jsonObject2 = array.getJSONObject(i);
							JSONObject jsonObject3 = jsonObject2
									.getJSONObject("deal");
							JSONObject jsonObject4 = jsonObject3
									.getJSONObject("goods");
							JSONObject jsonObject5 = jsonObject4
									.getJSONObject("goodsCategory");
							String deliveryTime = jsonObject4
									.getString("deliveryTime");
							String[] tmp = deliveryTime.split(" ");
							String status = jsonObject2
									.getString("orderStatusID");
							if (Common.expireDate(tmp[0]) && status.equals("1")) {
								orderList.add(new ListItem(jsonObject2
										.getString("orderID"), jsonObject5
										.getString("name"), jsonObject4
										.getString("weight"), jsonObject2
										.getString("price")));
							}
						}
					} else if (obj instanceof JSONObject) {
						JSONObject jsonObject3 = jsonObject
								.getJSONObject("deal");
						JSONObject jsonObject4 = jsonObject3
								.getJSONObject("goods");
						JSONObject jsonObject5 = jsonObject4
								.getJSONObject("goodsCategory");
						String deliveryTime = jsonObject4
								.getString("deliveryTime");
						String[] tmp = deliveryTime.split(" ");
						String status = jsonObject.getString("orderStatusID");
						if (Common.expireDate(tmp[0]) && status.equals("1")) {
							orderList.add(new ListItem(jsonObject
									.getString("orderID"), jsonObject5
									.getString("name"), jsonObject4
									.getString("weight"), jsonObject
									.getString("price")));
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				displayOrderNotification();
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

	public void displayOrderNotification() {
		for (ListItem item : orderList) {
			String contentText = item.getInfo() + " - " + item.getTitle()
					+ " kg - " + item.getDescription().replace(".0", "")
					+ " nghìn đồng";
			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
					con).setSmallIcon(R.drawable.ic_action_alarms)
					.setContentTitle("Xác nhận giao hàng")
					.setContentText(contentText)
					.setAutoCancel(true)
					.setTicker(contentText);
			Intent resultIntent = new Intent(con, MainActivity.class);
			int id = Integer.parseInt(item.getInfo());
			resultIntent.putExtra("orderID", item.getInfo());
			resultIntent.putExtra("driverID", driverID);

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
}
