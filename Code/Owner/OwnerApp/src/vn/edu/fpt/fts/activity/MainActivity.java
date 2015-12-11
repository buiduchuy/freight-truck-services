package vn.edu.fpt.fts.activity;

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
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import vn.edu.fpt.fts.adapter.TabsPagerAdapter;
import vn.edu.fpt.fts.classes.AlarmReceiver;
import vn.edu.fpt.fts.classes.OrderAlarmReceiver;
import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.fragment.R;

public class MainActivity extends FragmentActivity implements TabListener {

	private class WebServiceTask extends AsyncTask<String, Integer, String> {

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;
		public static final int GET_TASK = 2;

		public static final int POST_TASK = 1;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 10000;

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
					httppost.setEntity(new UrlEncodedFormEntity(params));

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
			if (response.contains("firstName")) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					String ownerName = jsonObject.getString("firstName");
					actionBar.setTitle("FTS Owner - " + ownerName);
					SharedPreferences preferences = getSharedPreferences("MyPrefs",
							Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = preferences.edit();
					editor.putString("ownerName", ownerName);					
					editor.commit();
				} catch (JSONException e) {
					// TODO: handle exception
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
	private ActionBar actionBar;
	private AlarmManager alarmManager, alarmManager2;
	ColorDrawable colorDrawable;
	private TabsPagerAdapter mAdapter;
	private PendingIntent pendingIntent, pendingIntent2;
	private String[] tabs = { "Hàng hóa", "Đơn hàng" };

	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SharedPreferences preferences = getSharedPreferences("MyPrefs",
				Context.MODE_PRIVATE);
		String email = preferences.getString("email", "");

		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		// actionBar.setDisplayShowTitleEnabled(false);
		// actionBar.setDisplayShowCustomEnabled(true);		
		// View customView = LayoutInflater.from(this).inflate(
		// R.layout.custom_view, null);
		// TextView title = (TextView) customView.findViewById(R.id.title);
		// TextView name = (TextView) customView.findViewById(R.id.name);
		// title.setText(R.string.app_name);
		// name.setText(email);
		// actionBar.setCustomView(customView);
		// colorDrawable.setColor(R.color.app_color);
		// actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.app_color));
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				actionBar.setSelectedNavigationItem(position);
			}
		});

		// alarm receiver
		Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
		pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
				PendingIntent.FLAG_CANCEL_CURRENT);
		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		int interval = 10000;

		alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), interval, pendingIntent);

		// order alarm
		Intent intent2 = new Intent(MainActivity.this, OrderAlarmReceiver.class);
		pendingIntent2 = PendingIntent.getBroadcast(this, 0, intent2,
				PendingIntent.FLAG_CANCEL_CURRENT);
		alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		int interval2 = 300000; // 60000
		alarmManager2.setInexactRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), interval2, pendingIntent2);

		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK, this,
				"");
		String url = Common.IP_URL + Common.Service_Owner_getName;
		wst.addNameValuePair("email", email);
		wst.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
				new String[] { url });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
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

		if (id == R.id.create_goods) {
			Intent intent = new Intent(this, CreateGoodsActivity.class);
			startActivity(intent);
		}
		if (id == R.id.action_history) {
			Intent intent = new Intent(this, HistoryActivity.class);
			startActivity(intent);
		}
		if (id == R.id.action_logout) {
			alarmManager.cancel(pendingIntent);
			alarmManager2.cancel(pendingIntent2);
			Common.logout(MainActivity.this);
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
		if (id == android.R.id.home) {
			finish();
			startActivity(getIntent());
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}
}
