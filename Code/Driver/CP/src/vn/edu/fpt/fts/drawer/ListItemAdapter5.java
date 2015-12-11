package vn.edu.fpt.fts.drawer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import vn.edu.fpt.fts.layout.R;

public class ListItemAdapter5 extends BaseAdapter{

	private Context context;
	private ArrayList<ListItem> listItems;
	
	public ListItemAdapter5(Context context, ArrayList<ListItem> listItems){
		this.context = context;
		this.listItems = listItems;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.listview_item_row_5, null);
        }
		TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        TextView txtDescription = (TextView) convertView.findViewById(R.id.txtDescription);
        TextView txtDescription1 = (TextView) convertView.findViewById(R.id.txtDescription1);
        
        txtTitle.setText(listItems.get(position).getTitle());
        txtDescription.setText(listItems.get(position).getDescription());
        txtDescription1.setText(listItems.get(position).getDate());
        
        return convertView;
	}

}
