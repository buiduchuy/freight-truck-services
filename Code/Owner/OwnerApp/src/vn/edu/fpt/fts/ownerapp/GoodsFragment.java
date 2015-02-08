package vn.edu.fpt.fts.ownerapp;

import com.example.ownerapp.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class GoodsFragment extends Fragment {
	private String[] goods = {"Bàn ghế", "Thức ăn", "Thiết bị gia dụng"};
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_goods, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_goods);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
        		(rootView.getContext(), android.R.layout.simple_list_item_1,goods);     
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view,
        			int position, long id) {
        		// TODO Auto-generated method stub
        		Intent intent = new Intent(view.getContext(), GoodsDetailActivity.class);
        		startActivity(intent);
        	}
		});
		
		return rootView;
	}
}
