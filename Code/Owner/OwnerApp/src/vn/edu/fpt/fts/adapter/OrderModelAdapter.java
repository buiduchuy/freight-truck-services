package vn.edu.fpt.fts.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import vn.edu.fpt.fts.classes.OrderModel;
import vn.edu.fpt.fts.fragment.R;

public class OrderModelAdapter extends ArrayAdapter<OrderModel> {
	private final Context context;
	private final ArrayList<OrderModel> orderModels;

	public OrderModelAdapter(Context context, ArrayList<OrderModel> orderModels) {
		super(context, R.layout.order_item, orderModels);
		this.context = context;
		this.orderModels = orderModels;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = null;
		rowView = inflater.inflate(R.layout.order_item, parent, false);
		TextView name = (TextView) rowView.findViewById(R.id.txtName);
		TextView price = (TextView) rowView.findViewById(R.id.txtPrice);
		TextView date = (TextView) rowView.findViewById(R.id.txtDate);
		TextView weight = (TextView) rowView.findViewById(R.id.txtWeight);
		TextView status = (TextView) rowView.findViewById(R.id.txtStatus);
		name.setText(orderModels.get(position).getName());
		price.setText(orderModels.get(position).getPrice());
		date.setText(orderModels.get(position).getDate());
		weight.setText(orderModels.get(position).getWeight());
		status.setText(orderModels.get(position).getStatus());
		return rowView;
	}
}
