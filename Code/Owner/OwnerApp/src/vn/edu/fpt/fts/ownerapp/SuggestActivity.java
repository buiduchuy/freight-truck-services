package vn.edu.fpt.fts.ownerapp;

import java.util.ArrayList;

import vn.edu.fpt.fts.adapter.Model;
import vn.edu.fpt.fts.adapter.ModelAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SuggestActivity extends Activity {
	private String[] drivers = {"HCM - HN", "HCM - DN", "DN - VT"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggest);
		
//		ListView listView = (ListView) findViewById(R.id.listview_suggest);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>
//        		(this, android.R.layout.simple_list_item_1,drivers);     
//        listView.setAdapter(adapter);
		
		ModelAdapter adapter = new ModelAdapter(this, generateData());
		ListView listView = (ListView) findViewById(R.id.listview_suggest);
		listView.setAdapter(adapter);
	}

	private ArrayList<Model> generateData() {
		// TODO Auto-generated method stub
		ArrayList<Model> models = new ArrayList<Model>();
        
        models.add(new Model("Menu Item 1","1"));
        models.add(new Model("Menu Item 2","2"));
        models.add(new Model("Menu Item 3","12"));
 
        return models;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.suggest, menu);
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
		return super.onOptionsItemSelected(item);
	}
}
