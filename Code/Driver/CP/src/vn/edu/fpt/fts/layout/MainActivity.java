package vn.edu.fpt.fts.layout;

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

import vn.edu.fpt.fts.classes.Constant;
import vn.edu.fpt.fts.drawer.ListItem;
import vn.edu.fpt.fts.drawer.ListNotiAdapter;
import vn.edu.fpt.fts.drawer.NavDrawerAdapter;
import vn.edu.fpt.fts.drawer.NavDrawerItem;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	private String[] mNavigationDrawerItemTitles;
	private TypedArray mNavigationImage;
	protected DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	ActionBarDrawerToggle mDrawerToggle;
	private NavDrawerAdapter adapter;
	private ArrayList<NavDrawerItem> array = new ArrayList<NavDrawerItem>();
	Handler handler;
	Runnable r;
	ArrayList<ListItem> list = new ArrayList<ListItem>();
	ArrayList<String> map;
	ListNotiAdapter notiAdapter;
	private static final String SERVICE_URL = Constant.SERVICE_URL
			+ "DealNotification/getDealNotificationByDriverID";
	int oldSize, newSize;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("Dịch vụ xe tải");
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		mNavigationDrawerItemTitles = getResources().getStringArray(
				R.array.navigation_drawer_items_array);
		mNavigationImage = getResources().obtainTypedArray(
				R.array.nav_drawer_icons);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		for (int i = 0; i < mNavigationDrawerItemTitles.length; i++) {
			array.add(new NavDrawerItem(mNavigationDrawerItemTitles[i],
					mNavigationImage.getResourceId(i, -1)));
		}

		mNavigationImage.recycle();

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		adapter = new NavDrawerAdapter(getApplicationContext(), array);

		mDrawerList.setAdapter(adapter);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.hello_world,
				R.string.hello_world) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		handler = new Handler();
		r = new Runnable() {
			public void run() {
				WebService ws = new WebService(WebService.POST_TASK,
						MainActivity.this, "Đang lấy dữ liệu ...");
				ws.addNameValuePair("driverID",
						getIntent().getStringExtra("driverID"));
				ws.execute(new String[] { SERVICE_URL });
				handler.postDelayed(r, 30000);
			}
		};
		handler.post(r);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		FragmentManager mng = getSupportFragmentManager();
		FragmentTransaction trs = mng.beginTransaction();
		RouteList frag1 = new RouteList();
		trs.replace(R.id.content_frame, frag1);
		trs.commit();

	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}

		private void selectItem(int position) {
			Fragment fragment = null;
			switch (position) {
			case 0:
				fragment = new RouteList();
				break;
			case 1:
				fragment = new TabDeals();
				break;
			case 2:
				fragment = new DealHistory();
				break;
			case 3:
				fragment = new History();
				break;
			case 4:
				fragment = new Notification();
				break;
			case 5:
				fragment = new SuggestList();
				break;
			case 6:
				fragment = new SuggestList();
				break;
			default:
				break;
			}

			if (fragment != null) {
				FragmentManager fragmentManager = getSupportFragmentManager();
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				transaction.replace(R.id.content_frame, fragment);
				transaction.addToBackStack(null);
				transaction.commit();
				mDrawerList.setItemChecked(position, true);
				mDrawerList.setSelection(position);
				setTitle(mNavigationDrawerItemTitles[position]);
				mDrawerLayout.closeDrawer(mDrawerList);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_create:
			setTitle("Tạo lộ trình mới");
			Intent intent = getIntent();
			intent.removeExtra("markerList");
			FragmentManager mng = getSupportFragmentManager();
			FragmentTransaction trs = mng.beginTransaction();
			CreateRoute frag1 = new CreateRoute();
			trs.replace(R.id.content_frame, frag1, "createRoute");
			trs.addToBackStack("createRoute");
			trs.commit();
			return true;
		default:
			break;
		}
		return false;
	}

	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
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
								list.add(new ListItem(
										item.getString("message"), "", ""));
							}
						} else if (intervent instanceof JSONObject) {
							JSONObject item = obj
									.getJSONObject("dealNotification");
							list.add(new ListItem(item.getString("message"),
									"", ""));
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
								list.add(new ListItem(
										item.getString("message"), "", ""));
							}
						} else if (intervent instanceof JSONObject) {
							JSONObject item = obj
									.getJSONObject("dealNotification");
							list.add(new ListItem(item.getString("message"),
									"", ""));
						}
						newSize = list.size();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (oldSize < newSize) {
						Toast.makeText(
								MainActivity.this,
								"Bạn có " + (newSize - oldSize)
										+ " thông báo mới", Toast.LENGTH_SHORT)
								.show();
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
