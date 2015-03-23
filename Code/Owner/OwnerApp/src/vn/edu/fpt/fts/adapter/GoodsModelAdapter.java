package vn.edu.fpt.fts.adapter;

import java.util.ArrayList;

import vn.edu.fpt.fts.classes.GoodsModel;
import vn.edu.fpt.fts.fragment.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GoodsModelAdapter extends ArrayAdapter<GoodsModel> {
	private final Context context;
	private final ArrayList<GoodsModel> goodsModels;

	public GoodsModelAdapter(Context context, ArrayList<GoodsModel> goodsModels) {
		super(context, R.layout.goods_item, goodsModels);
		this.context = context;
		this.goodsModels = goodsModels;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = null;
		rowView = inflater.inflate(R.layout.goods_item, parent, false);
		TextView name = (TextView) rowView.findViewById(R.id.txtNames);
		TextView price = (TextView) rowView.findViewById(R.id.txtPrice);
		TextView date = (TextView) rowView.findViewById(R.id.txtDate);
		TextView weight = (TextView) rowView.findViewById(R.id.txtWeight);
		name.setText(goodsModels.get(position).getName());
		price.setText(goodsModels.get(position).getPrice());
		date.setText(goodsModels.get(position).getDate());
		weight.setText(goodsModels.get(position).getWeight());
		return rowView;
	}
}
