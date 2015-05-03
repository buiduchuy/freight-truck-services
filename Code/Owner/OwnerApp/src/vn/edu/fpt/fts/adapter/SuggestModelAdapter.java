package vn.edu.fpt.fts.adapter;

import java.util.ArrayList;

import vn.edu.fpt.fts.classes.SuggestModel;
import vn.edu.fpt.fts.fragment.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SuggestModelAdapter extends ArrayAdapter<SuggestModel> {

	private final Context context;
	private final ArrayList<SuggestModel> suggestModels;

	public SuggestModelAdapter(Context context,
			ArrayList<SuggestModel> suggestModels) {
		super(context, R.layout.suggest_item, suggestModels);
		this.context = context;
		this.suggestModels = suggestModels;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = null;
		rowView = inflater.inflate(R.layout.suggest_item, parent, false);
		TextView name = (TextView) rowView.findViewById(R.id.txtName);
		TextView driver = (TextView) rowView.findViewById(R.id.txtDriver);
		TextView date = (TextView) rowView.findViewById(R.id.txtDate);
		name.setText(suggestModels.get(position).getName());
		driver.setText(suggestModels.get(position).getDriver());
		date.setText(suggestModels.get(position).getDate());
		return rowView;
	}
}
