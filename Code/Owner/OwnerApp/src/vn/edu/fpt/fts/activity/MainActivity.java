package vn.edu.fpt.fts.activity;

import vn.edu.fpt.fts.adapter.TabsPagerAdapter;
import vn.edu.fpt.fts.classes.AlarmReceiver;
import vn.edu.fpt.fts.classes.OrderAlarmReceiver;
import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.fragment.R;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends FragmentActivity implements TabListener {
	
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	ColorDrawable colorDrawable;
	private ActionBar actionBar;
	private String[] tabs = {"Hàng hóa", "Đơn hàng"};
	private AlarmManager alarmManager, alarmManager2;
	private PendingIntent pendingIntent, pendingIntent2;
	
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences = getSharedPreferences("MyPrefs",
				Context.MODE_PRIVATE);
		String email = preferences.getString("email", "");
        
        
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        actionBar.setTitle("FTS Owner - " + email);
//        colorDrawable.setColor(R.color.app_color);
//        actionBar.setBackgroundDrawable(getResources().getDrawable(R.color.app_color));
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        for(String tab_name : tabs) {
        	actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
        }
              
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				actionBar.setSelectedNavigationItem(position);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        
        // alarm receiver
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 10000;
        
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);

        
        // order alarm
        Intent intent2 = new Intent(MainActivity.this, OrderAlarmReceiver.class);
        pendingIntent2 = PendingIntent.getBroadcast(this, 0, intent2, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval2 = 300000; //60000
        alarmManager2.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval2, pendingIntent2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {			
			return true;
		}
		return super.onKeyDown(keyCode, event);
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


	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}
