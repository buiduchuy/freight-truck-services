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

import vn.edu.fpt.fts.classes.AlarmReceiver;
import vn.edu.fpt.fts.classes.Constant;
import vn.edu.fpt.fts.drawer.ListItem;
import vn.edu.fpt.fts.drawer.ListNotiAdapter;
import vn.edu.fpt.fts.drawer.NavDrawerAdapter;
import vn.edu.fpt.fts.drawer.NavDrawerItem;
import vn.edu.fpt.fts.helper.ConnectivityHelper;
import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.os.PowerManager.WakeLock;
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
	private PendingIntent pendingIntent;

	private static final String SERVICE_URL = Constant.SERVICE_URL
			+ "DealNotification/getDealNotificationByDriverID";
	int oldSize, newSize;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar actionBar = getActionBar();

		actionBar.setIcon(R.drawable.ic_action_place);
		actionBar.setTitle("Lộ trình");

		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE
				| ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
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

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
		alarmIntent
				.putExtra("driverID", getIntent().getStringExtra("driverID"));

		pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
				alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		int interval = 60000;

		manager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), interval, pendingIntent);

		if (getIntent().getStringExtra("dealID") == null) {
			FragmentManager mng = getSupportFragmentManager();
			FragmentTransaction trs = mng.beginTransaction();
			RouteList frag1 = new RouteList();
			trs.replace(R.id.content_frame, frag1);
			trs.commit();
		} else {
			String id = getIntent().getStringExtra("dealID");
			NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			mNotificationManager.cancel(Integer.parseInt(id));
			String status = getIntent().getStringExtra("status");
			String sender = getIntent().getStringExtra("sender");
			if(status.equals("1")) {
				if(sender.equals("driver")) {
					FragmentManager mng = getSupportFragmentManager();
					FragmentTransaction trs = mng.beginTransaction();
					CancelOffer frag = new CancelOffer();
					Bundle bundle = new Bundle();
					bundle.putString("dealID", id);
					frag.setArguments(bundle);
					trs.replace(R.id.content_frame, frag);
					trs.addToBackStack(null);
					trs.commit();
				}
				else if(sender.equals("owner")) {
					FragmentManager mng = getSupportFragmentManager();
					FragmentTransaction trs = mng.beginTransaction();
					OfferResponse frag = new OfferResponse();
					Bundle bundle = new Bundle();
					bundle.putString("dealID", id);
					frag.setArguments(bundle);
					trs.replace(R.id.content_frame, frag);
					trs.addToBackStack(null);
					trs.commit();
				}
			}
			else {
				FragmentManager mng = getSupportFragmentManager();
				FragmentTransaction trs = mng.beginTransaction();
				DealHistoryDetail frag = new DealHistoryDetail();
				Bundle bundle = new Bundle();
				bundle.putString("dealID", id);
				frag.setArguments(bundle);
				trs.replace(R.id.content_frame, frag);
				trs.addToBackStack(null);
				trs.commit();
			}
		}
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
				SharedPreferences share = getSharedPreferences("driver",
						Context.MODE_PRIVATE);
				Editor editor = share.edit();
				editor.remove("driverID");
				editor.commit();
				Intent intent = new Intent(getApplicationContext(), Login.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
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
}
