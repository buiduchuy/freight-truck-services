package test.example.cp;

import java.util.List;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

public class Deals extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		String[] sent = {"Hàng A", "Hàng B", "Hàng C"};
	    View myFragmentView = inflater.inflate(R.layout.activity_deals, container, false);
	    ListView list1 = (ListView) myFragmentView.findViewById(R.id.listView1);
	    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.listview_item_row, sent);
	    list1.setAdapter(adapter1);
	    list1.setOnItemClickListener((new AdapterView.OnItemClickListener() {
	    	@Override
	    	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	    			long arg3) {
	    		FragmentManager mng = getActivity().getSupportFragmentManager();
	    		FragmentTransaction trs = mng.beginTransaction();
	    		CancelOffer frag = new CancelOffer();
	    		trs.replace(R.id.content_frame, frag);
	    		trs.addToBackStack(null);
	    		trs.commit();
	    	}
		}));
		return myFragmentView;
	}
}
