package vn.edu.fpt.fts.classes;


import vn.edu.fpt.fts.activity.CreateGoodsActivity;
import vn.edu.fpt.fts.fragment.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ConfirmCreateDialog extends DialogFragment {
	private Goods goods;

	public static ConfirmCreateDialog newInstance(Goods goods) {
		ConfirmCreateDialog dialog = new ConfirmCreateDialog();
		Bundle args = new Bundle();
		args.putString("cate", "Loại hàng: " + goods.getGoodsCategory());
		args.putString("pickTime", "Từ: " + goods.getPickupTime());
		args.putString("delTime", "Đến: " + goods.getDeliveryTime());
		args.putString("pickAdd", "Nhận hàng: " + goods.getPickupAddress());
		args.putString("delAdd", "Giao hàng: " + goods.getDeliveryAddress());
		args.putString("weight", "Khối lượng: " + goods.getWeight() + " kg");
		args.putString("price", "Giá: " + (int)goods.getPrice() + " nghìn");
		args.putString("note", "Ghi chú: " + goods.getNotes());
		dialog.setArguments(args);
		return dialog;
	}

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.create_goods_confirm, null);
		TextView tvCate = (TextView) view.findViewById(R.id.tvCategory);
		TextView tvPickTime = (TextView) view.findViewById(R.id.tvPickupDate);
		TextView tvDelTime = (TextView) view.findViewById(R.id.tvDeliverDate);
		TextView tvPickAdd = (TextView) view.findViewById(R.id.tvPickupAddr);
		TextView tvDelAdd = (TextView) view.findViewById(R.id.tvDeliverAddr);
		TextView tvWeight = (TextView) view.findViewById(R.id.tvWeight);
		TextView tvPrice = (TextView) view.findViewById(R.id.tvPrice);
		TextView tvNote = (TextView) view.findViewById(R.id.tvNote);
		tvCate.setText(getArguments().getString("cate"));
		tvPickTime.setText(getArguments().getString("pickTime"));
		tvDelTime.setText(getArguments().getString("delTime"));
		tvPickAdd.setText(getArguments().getString("pickAdd"));
		tvDelAdd.setText(getArguments().getString("delAdd"));
		tvWeight.setText(getArguments().getString("weight"));
		tvPrice.setText(getArguments().getString("price"));
		tvNote.setText(getArguments().getString("note"));
		builder.setTitle("Tạo hàng mới")
				.setView(view)
				.setPositiveButton("Tạo",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								((CreateGoodsActivity)getActivity()).postData();
							}
						})
				.setNegativeButton("Hủy",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						});
		return builder.create();
	}
}
