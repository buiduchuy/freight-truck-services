package vn.edu.fpt.fts.adapter;

import java.util.ArrayList;

import vn.edu.fpt.fts.classes.DealModel;
import vn.edu.fpt.fts.fragment.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DealModelAdapter extends ArrayAdapter<DealModel> {
	private final Context context;
	private final ArrayList<DealModel> dealModels;

	public DealModelAdapter(Context context, ArrayList<DealModel> dealModels) {
		super(context, R.layout.deal_item, dealModels);
		this.context = context;
		this.dealModels = dealModels;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = null;
		rowView = inflater.inflate(R.layout.deal_item, parent, false);
		TextView name = (TextView) rowView.findViewById(R.id.txtName);
		TextView date = (TextView) rowView.findViewById(R.id.txtDate);
		TextView price = (TextView) rowView.findViewById(R.id.txtPrice);
		TextView status = (TextView) rowView.findViewById(R.id.txtStatus);
		name.setText(dealModels.get(position).getName());
		date.setText(dealModels.get(position).getDate());
		price.setText(dealModels.get(position).getPrice());
		status.setText(dealModels.get(position).getStatus());
		return rowView;
	}

}
