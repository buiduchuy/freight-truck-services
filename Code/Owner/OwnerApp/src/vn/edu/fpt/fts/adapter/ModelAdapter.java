package vn.edu.fpt.fts.adapter;

import java.util.ArrayList;

import vn.edu.fpt.fts.fragment.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ModelAdapter extends ArrayAdapter<Model> {
	private final Context context;
	private final ArrayList<Model> modelsArrayList;

	public ModelAdapter(Context context, ArrayList<Model> modelsArrayList) {
		// TODO Auto-generated constructor stub
		super(context, R.layout.target_item, modelsArrayList);

		this.context = context;
		this.modelsArrayList = modelsArrayList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = null;
		rowView = inflater.inflate(R.layout.target_item, parent, false);
		TextView titleView = (TextView) rowView.findViewById(R.id.item_title);
        TextView counterView = (TextView) rowView.findViewById(R.id.item_counter);
        titleView.setText(modelsArrayList.get(position).getTitle());
        counterView.setText(modelsArrayList.get(position).getCounter());
        return rowView;
	}
}
