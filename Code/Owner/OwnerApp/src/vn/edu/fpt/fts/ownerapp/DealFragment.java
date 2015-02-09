package vn.edu.fpt.fts.ownerapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DealFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_deal, container, false);
		String[] deals = {"Giao dịch #11", "Giao dịch #13", "Giao dịch #14"};
		ListView listView = (ListView)rootView.findViewById(R.id.listview_deal_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>
				(getActivity(), android.R.layout.simple_list_item_1, deals);
		listView.setAdapter(adapter);
		return rootView;
	}
}
