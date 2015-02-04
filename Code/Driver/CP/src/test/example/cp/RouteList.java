package test.example.cp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RouteList extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		String[] sent = {"Lộ trình: TPHCM - Cà Mau", "Lộ trình: TPHCM - Long An", "Lộ trình: Long An - Cà Mau"};
	    View myFragmentView = inflater.inflate(R.layout.activity_route_list, container, false);
	    ListView list1 = (ListView) myFragmentView.findViewById(R.id.listView1);
	    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.listview_item_row, sent);
	    list1.setAdapter(adapter1);
	    list1.setOnItemClickListener((new AdapterView.OnItemClickListener() {
	    	@Override
	    	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	    			long arg3) {
	    		FragmentManager mng = getActivity().getSupportFragmentManager();
	    		FragmentTransaction trs = mng.beginTransaction();
	    		CurrentRoute frag = new CurrentRoute();
	    		trs.replace(R.id.content_frame, frag);
	    		trs.addToBackStack(null);
	    		trs.commit();
	    	}
		}));
		return myFragmentView;
	}
}