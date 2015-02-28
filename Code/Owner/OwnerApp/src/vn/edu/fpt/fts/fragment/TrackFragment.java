package vn.edu.fpt.fts.fragment;


import vn.edu.fpt.fts.ownerapp.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TrackFragment extends Fragment {
	private String[] deals = {"Giao dịch #11", "Giao dịch #34", "Giao dịch #45"};
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_track, container, false);
		
		ListView listView = (ListView)rootView.findViewById(R.id.listview_track);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>
				(rootView.getContext(), android.R.layout.simple_list_item_1,deals);
		listView.setAdapter(adapter);
		
		
		return rootView;
	}
}
