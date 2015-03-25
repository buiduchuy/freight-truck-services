package vn.edu.fpt.fts.drawer;

import java.util.ArrayList;

import vn.edu.fpt.fts.layout.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListItemAdapter4 extends BaseAdapter{

	private Context context;
	private ArrayList<ListItem> listItems;
	
	public ListItemAdapter4(Context context, ArrayList<ListItem> listItems){
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
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.listview_item_row_4, null);
        }
		TextView txtInfo = (TextView) convertView.findViewById(R.id.txtInfo);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        TextView txtTitle2 = (TextView) convertView.findViewById(R.id.txtTitle2);
        TextView txtDescription = (TextView) convertView.findViewById(R.id.txtDescription);
        TextView txtDate = (TextView) convertView.findViewById(R.id.txtDate);
        
        txtInfo.setText(listItems.get(position).getInfo());
        txtTitle.setText(listItems.get(position).getTitle());
        txtTitle2.setText(listItems.get(position).getTitle2());
        txtDescription.setText(listItems.get(position).getDescription());
        txtDate.setText(listItems.get(position).getDate());
        
        return convertView;
	}

}
