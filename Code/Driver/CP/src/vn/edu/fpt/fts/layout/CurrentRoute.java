package vn.edu.fpt.fts.layout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import test.example.cp.R;
import vn.edu.fpt.fts.classes.Constant;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CurrentRoute extends Fragment {

	TextView contentView;
	String id;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getActivity().setTitle("Thông tin lộ trình");
		View v = inflater.inflate(R.layout.activity_current_route, container,
				false);
		contentView = (TextView) v.findViewById(R.id.textView3);
		Bundle bundle = getArguments();
		id = bundle.getString("id");
		try {
			new GetRoute().execute(id).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Button button = (Button) v.findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
	    		FragmentManager mng = getActivity().getSupportFragmentManager();
	    		FragmentTransaction trs = mng.beginTransaction();
	    		ChangeRoute frag = new ChangeRoute();
	    		Bundle bundle = new Bundle();
	    		bundle.putString("id", id);
	    		frag.setArguments(bundle);
	    		trs.replace(R.id.content_frame, frag, "changeRoute");
	    		trs.addToBackStack("changeRoute");
	    		trs.commit();
			}
		});
		Button button2 = (Button) v.findViewById(R.id.button2);
		button2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
	    		try {
					boolean result = new DeleteRoute().execute().get();
					if(result) {
						Toast.makeText(getActivity(), "Đã hủy lộ trình thành công", Toast.LENGTH_SHORT).show();
					}
					else {
						Toast.makeText(getActivity(), "Hủy lộ trình thất bại", Toast.LENGTH_SHORT).show();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		return v;
	}

	private class GetRoute extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			ArrayList<String> array = new ArrayList<String>();

			try {
				Class.forName("net.sourceforge.jtds.jdbc.Driver");

				Connection con = DriverManager.getConnection(Constant.url, Constant.user, Constant.pass);

				String result = "Database connection success\n";
				String sql = "SELECT * FROM dbo.Route WHERE RouteID = "
						+ params[0];
				PreparedStatement st = con.prepareStatement(sql);

				ResultSet rs = st.executeQuery();
				
				sql = "SELECT * FROM dbo.RouteMarker WHERE RouteID = "
						+ params[0];
				st = con.prepareStatement(sql);

				ResultSet rs2 = st.executeQuery();
				
				sql = "SELECT * FROM dbo.RouteGoodsCategory WHERE RouteID = "
						+ params[0];
				st = con.prepareStatement(sql);

				ResultSet rs3 = st.executeQuery();
				
				while (rs.next()) {
					String content = "Địa điểm bắt đầu: "
							+ rs.getString("StartingAddress") + "\n";
					int i = 1;
					while (rs2.next()) {
						content += "Địa điểm đi qua " + i + ": " + rs2.getString("RouteMarkerLocation") + "\n";
						i++;
					};
					content += "Địa điểm kết thúc: " + rs.getString("DestinationAddress")
							+ "\nNgày bắt đầu: " + rs.getString("StartTime")
							+ "\nNgày kết thúc: " + rs.getString("FinishTime")
							+ "\nKhối lượng có thể chở: " + rs.getString("Weight") + " tấn\nHàng không chở: ";
					if(!rs3.isBeforeFirst()) {
						content += "không có loại hàng nào";
					}
					else {
						while (rs3.next()) {
							if(rs3.getInt("GoodsCategoryID") == 1) {
								content += "hàng đông lạnh, ";
							}
							else if(rs3.getInt("GoodsCategoryID") == 2) {
								content += "hàng dễ vỡ, ";
							}
							else if(rs3.getInt("GoodsCategoryID") == 3) {
								content += "hàng dễ cháy nổ, ";
							}
							else if(rs3.getInt("GoodsCategoryID") == 4) {
								content += "các loại hàng khác, ";
							}
						}
						content = content.substring(0, content.length() - 2);
					}
					contentView.setText(content);
				}
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
				// tv.setText(e.toString());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
	}
	
	private class DeleteRoute extends AsyncTask<String, Void, Boolean> {
		@Override
		protected Boolean doInBackground(String... params) {
			ArrayList<String> array = new ArrayList<String>();

			try {
				Class.forName("net.sourceforge.jtds.jdbc.Driver");

				Connection con = DriverManager.getConnection(Constant.url, Constant.user, Constant.pass);
				
				String sql = "UPDATE dbo.Route SET Active = 0 WHERE RouteID = " + id;
				PreparedStatement st = con.prepareStatement(sql);
				st.executeUpdate();
				
				sql = "UPDATE dbo.Deal SET Active = 'False' WHERE RouteID = " + id;
				st = con.prepareStatement(sql);
				st.executeUpdate();
				
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
	}
}
