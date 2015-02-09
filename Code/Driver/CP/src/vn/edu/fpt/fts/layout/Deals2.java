package vn.edu.fpt.fts.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Deals2 extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		String[] sent = {"Hàng A", "Hàng B", "Hàng C"};
	    View myFragmentView = inflater.inflate(R.layout.activity_deals2, container, false);
	    ListView list1 = (ListView) myFragmentView.findViewById(R.id.listView2);
	    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.listview_item_row, sent);
	    list1.setAdapter(adapter1);
	    list1.setOnItemClickListener((new AdapterView.OnItemClickListener() {
	    	@Override
	    	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	    			long arg3) {
	    		FragmentManager mng = getActivity().getSupportFragmentManager();
	    		FragmentTransaction trs = mng.beginTransaction();
	    		OfferResponse frag = new OfferResponse();
	    		trs.replace(R.id.content_frame, frag);
	    		trs.addToBackStack(null);
	    		trs.commit();
	    	}
		}));
		return myFragmentView;
	}
}
