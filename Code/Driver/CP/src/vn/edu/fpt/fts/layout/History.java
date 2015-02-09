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

public class History extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		String[] deals = {"Giao dịch A", "Giao dịch B", "Giao dịch C"};
		View myFragmentView = inflater.inflate(R.layout.activity_history, container, false);
	    ListView list = (ListView) myFragmentView.findViewById(R.id.listView3);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.listview_item_row, deals);
	    list.setAdapter(adapter);
	    list.setOnItemClickListener((new AdapterView.OnItemClickListener() {
	    	@Override
	    	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	    			long arg3) {
	    		FragmentManager mng = getActivity().getSupportFragmentManager();
	    		FragmentTransaction trs = mng.beginTransaction();
	    		HistoryDetail frag = new HistoryDetail();
	    		trs.replace(R.id.content_frame, frag);
	    		trs.addToBackStack(null);
	    		trs.commit();
	    	}
		}));
		return myFragmentView;
	}
}
