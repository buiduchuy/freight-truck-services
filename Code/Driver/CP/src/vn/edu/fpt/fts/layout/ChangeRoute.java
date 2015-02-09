package vn.edu.fpt.fts.layout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import vn.edu.fpt.fts.helper.PlacesAutoCompleteAdapter;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class ChangeRoute extends Fragment {

	EditText startDate;
	EditText startHour;
	EditText endDate;
	AutoCompleteTextView start;
	AutoCompleteTextView p1;
	AutoCompleteTextView p2;
	AutoCompleteTextView end;
	PlacesAutoCompleteAdapter startAdapter;
	PlacesAutoCompleteAdapter p1Adapter;
	PlacesAutoCompleteAdapter p2Adapter;
	PlacesAutoCompleteAdapter endAdapter;
	CheckBox check1;
	CheckBox check2;
	CheckBox check3;
	TextView link;
	CheckBox frozen;
	CheckBox broken;
	CheckBox flammable;
	CheckBox others;
	EditText payload;
	View v;
	ArrayList<LatLng> locations;
	ArrayList<String> pos = new ArrayList<String>();
	Calendar cal = Calendar.getInstance();
	LocationManager locationManager;
	private static final String url = "jdbc:jtds:sqlserver://10.0.3.2:1433;instance=MSSQLSERVER;DatabaseName=FTS";
	private static final String user = "sa";
	private static final String pass = "123456";
	String id;

	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Fragment fragment = getActivity().getSupportFragmentManager()
				.findFragmentByTag("changeRoute");
		if (fragment != null) {
			getActivity().getSupportFragmentManager()
					.findFragmentByTag("changeRoute").setRetainInstance(true);
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Intent intent = getActivity().getIntent();
		locations = (ArrayList<LatLng>) intent
				.getSerializableExtra("markerList2");
		if (locations == null || locations.size() == 0) {
			getActivity().getSupportFragmentManager()
					.findFragmentByTag("changeRoute").getRetainInstance();
		} else {
			for (LatLng p : locations) {
				try {
					String address = new GetAddress().execute(p.latitude,
							p.longitude).get();
					pos.add(address);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			start.setText(pos.get(0));
			end.setText(pos.get(pos.size() - 1));
			if (pos.size() == 3) {
				p1.setText(pos.get(1));
			} else if (pos.size() == 4) {
				p1.setText(pos.get(1));
				p2.setText(pos.get(2));
			}
		}
		intent.removeExtra("markerList2");
		pos.clear();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getActivity().setTitle("Thay đổi lộ trình");
		v = inflater.inflate(R.layout.activity_change_route, container, false);
		startDate = (EditText) v.findViewById(R.id.editText2);
		startHour = (EditText) v.findViewById(R.id.editText3);
		payload = (EditText) v.findViewById(R.id.editText8);
		cal = Calendar.getInstance();
		String date = String.valueOf(cal.get(Calendar.DAY_OF_MONTH) + "/"
				+ String.valueOf(cal.get(Calendar.MONTH) + 1) + "/"
				+ String.valueOf(cal.get(Calendar.YEAR)));
		startDate.setText(date);
		endDate = (EditText) v.findViewById(R.id.editText4);
		link = (TextView) v.findViewById(R.id.textView7);

		frozen = (CheckBox) v.findViewById(R.id.checkBox1);
		broken = (CheckBox) v.findViewById(R.id.checkBox2);
		flammable = (CheckBox) v.findViewById(R.id.checkBox3);
		others = (CheckBox) v.findViewById(R.id.checkBox4);

		startAdapter = new PlacesAutoCompleteAdapter(getActivity(),
				R.layout.listview_item_row);
		p1Adapter = new PlacesAutoCompleteAdapter(getActivity(),
				R.layout.listview_item_row);
		p2Adapter = new PlacesAutoCompleteAdapter(getActivity(),
				R.layout.listview_item_row);
		endAdapter = new PlacesAutoCompleteAdapter(getActivity(),
				R.layout.listview_item_row);
		start = (AutoCompleteTextView) v.findViewById(R.id.start);
		p1 = (AutoCompleteTextView) v.findViewById(R.id.point1);
		p2 = (AutoCompleteTextView) v.findViewById(R.id.point2);
		end = (AutoCompleteTextView) v.findViewById(R.id.end);

		start.setAdapter(startAdapter);
		p1.setAdapter(p1Adapter);
		p2.setAdapter(p2Adapter);
		end.setAdapter(endAdapter);

		Button button = (Button) v.findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					String startPoint = start.getText().toString();
					String Point1 = p1.getText().toString();
					String Point2 = p2.getText().toString();
					String endPoint = end.getText().toString();
					String startD = startDate.getText().toString() + " "
							+ startHour.getText().toString();
					String endD = endDate.getText().toString();
					String frz = String.valueOf(frozen.isChecked());
					String brk = String.valueOf(broken.isChecked());
					String flm = String.valueOf(flammable.isChecked());
					String oth = String.valueOf(others.isChecked());
					String load = payload.getText().toString();

					if (startPoint.equals("")) {
						Toast.makeText(getActivity(),
								"Điểm bắt đầu không được để trống.",
								Toast.LENGTH_SHORT).show();
					} else if (startPoint.length() > 100) {
						Toast.makeText(
								getActivity(),
								"Điểm bắt đầu chỉ dài tối đa 100 ký tự. Vui lòng nhập lại.",
								Toast.LENGTH_SHORT).show();
					} else if (Point1.length() > 100) {
						Toast.makeText(
								getActivity(),
								"Điểm đi qua 1 chỉ dài tối đa 100 ký tự. Vui lòng nhập lại.",
								Toast.LENGTH_SHORT);
					} else if (Point2.length() > 100) {
						Toast.makeText(
								getActivity(),
								"Điểm đi qua 2 chỉ dài tối đa 100 ký tự. Vui lòng nhập lại.",
								Toast.LENGTH_SHORT).show();
					} else if (endPoint.equals("")) {
						Toast.makeText(getActivity(),
								"Điểm kết thúc không được để trống.",
								Toast.LENGTH_SHORT).show();
					} else if (endPoint.length() > 100) {
						Toast.makeText(
								getActivity(),
								"Điểm kết thúc chỉ dài tối đa 100 ký tự. Vui lòng nhập lại.",
								Toast.LENGTH_SHORT).show();
					} else if (startD.equals("")) {
						Toast.makeText(getActivity(),
								"Ngày bắt đầu không được để trống.",
								Toast.LENGTH_SHORT).show();
					} else if (endD.equals("")) {
						Toast.makeText(getActivity(),
								"Ngày kết thúc không được để trống.",
								Toast.LENGTH_SHORT).show();
					} else if (Date.parse(startD) > Date.parse(endD)) {
						Toast.makeText(getActivity(),
								"Ngày bắt đầu phải sớm hơn ngày kết thúc.",
								Toast.LENGTH_SHORT).show();
					} else if (payload.equals("")) {
						Toast.makeText(getActivity(),
								"Khối lượng chở không được để trống.",
								Toast.LENGTH_SHORT).show();
					} else if (Integer.parseInt(load) > 20) {
						Toast.makeText(getActivity(),
								"Khối lượng chở không vượt quá 20 tấn",
								Toast.LENGTH_SHORT).show();
					} else {
						Boolean result = new UpdateRoute().execute(startPoint,
								Point1, Point2, endPoint, startD, endD, load,
								frz, brk, flm, oth).get();
						if (result) {
							Toast.makeText(getActivity(),
									"Cập nhật thành công", Toast.LENGTH_SHORT)
									.show();
						} else {
							Toast.makeText(getActivity(), "Cập nhật thất bại",
									Toast.LENGTH_SHORT).show();
						}
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

		if (start.getText().toString().equals("")) {
			locationManager = (LocationManager) getActivity().getSystemService(
					Context.LOCATION_SERVICE);
			LocationListener locationListener = new LocationListener() {
				public void onLocationChanged(Location location) {
					String current;
					try {
						current = new GetAddress()
								.execute(location.getLatitude(),
										location.getLongitude()).get();
						start.setText(current);
						locationManager.removeUpdates(this);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				public void onStatusChanged(String provider, int status,
						Bundle extras) {
				}

				public void onProviderEnabled(String provider) {
				}

				public void onProviderDisabled(String provider) {
				}
			};
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		}
		startDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog dialog = new DatePickerDialog(getActivity(),
						startListener, cal.get(Calendar.YEAR), cal
								.get(Calendar.MONTH), cal.get(Calendar.DATE));
				DatePicker picker = dialog.getDatePicker();
				Calendar calendar = Calendar.getInstance();
				picker.setMinDate(calendar.getTimeInMillis() - 1000);
				calendar.add(Calendar.MONTH, 1);
				picker.setMaxDate(calendar.getTimeInMillis());
				dialog.show();
			}
		});

		startHour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TimePickerDialog dialog = new TimePickerDialog(getActivity(),
						startHourListener, cal.get(Calendar.HOUR_OF_DAY), cal
								.get(Calendar.MINUTE), true);
				dialog.show();
			}
		});

		endDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog dialog = new DatePickerDialog(getActivity(),
						endListener, cal.get(Calendar.YEAR), cal
								.get(Calendar.MONTH), cal.get(Calendar.DATE));
				DatePicker picker = dialog.getDatePicker();
				Calendar calendar = Calendar.getInstance();
				picker.setMinDate(calendar.getTimeInMillis() - 1000);
				calendar.add(Calendar.MONTH, 1);
				picker.setMaxDate(calendar.getTimeInMillis());
				dialog.show();
			}
		});
		link.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (start.getText().toString().equals("")
						|| end.getText().toString().equals("")) {
					Toast.makeText(
							getActivity().getBaseContext(),
							"Vui lòng nhập địa điểm bắt đầu và kết thúc trước khi tùy chỉnh",
							3).show();
				} else {
					Intent intent = getActivity().getIntent();

					intent.putExtra("start", start.getText().toString());
					intent.putExtra("p1", p1.getText().toString());
					intent.putExtra("p2", p2.getText().toString());
					intent.putExtra("end", end.getText().toString());

					intent.putExtra("sender", "changeRoute");
					FragmentManager mng = getActivity()
							.getSupportFragmentManager();
					FragmentTransaction trs = mng.beginTransaction();
					CustomizeRoute frag1 = new CustomizeRoute();
					trs.replace(R.id.content_frame, frag1);
					trs.addToBackStack(null);
					trs.commit();
				}
			}
		});
		id = getArguments().getString("id");
		try {
			new GetRoute().execute(id).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return v;
	}

	private DatePickerDialog.OnDateSetListener startListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			startDate
					.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
		}
	};

	private TimePickerDialog.OnTimeSetListener startHourListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			startHour.setText(hourOfDay + ":" + minute);
		}
	};

	private DatePickerDialog.OnDateSetListener endListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			endDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
		}
	};

	TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			startAdapter.getFilter().filter(s);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}
	};

	TextWatcher watcher2 = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			endAdapter.getFilter().filter(s);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}
	};

	TextWatcher watcher3 = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			p1Adapter.getFilter().filter(s);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}
	};

	TextWatcher watcher4 = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			p2Adapter.getFilter().filter(s);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}
	};

	private class GetAddress extends AsyncTask<Double, Void, String> {
		private final AndroidHttpClient ANDROID_HTTP_CLIENT = AndroidHttpClient
				.newInstance(GetAddress.class.getName());

		@Override
		protected String doInBackground(Double... locations) {
			String googleMapUrl = "http://maps.googleapis.com/maps/api/geocode/json?latlng="
					+ locations[0] + "," + locations[1] + "&sensor=false";

			try {
				JSONObject googleMapResponse = new JSONObject(
						ANDROID_HTTP_CLIENT.execute(new HttpGet(googleMapUrl),
								new BasicResponseHandler()));
				JSONArray results = (JSONArray) googleMapResponse
						.get("results");
				JSONObject result = results.getJSONObject(0);
				String address = result.getString("formatted_address");
				ANDROID_HTTP_CLIENT.close();
				return address;
			} catch (Exception ignored) {
				ignored.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			ANDROID_HTTP_CLIENT.close();
		}
	}

	private class UpdateRoute extends AsyncTask<String, Void, Boolean> {
		@Override
		protected Boolean doInBackground(String... params) {
			android.os.Debug.waitForDebugger();
			String response = "";

			try {
				Class.forName("net.sourceforge.jtds.jdbc.Driver");

				Connection con = DriverManager.getConnection(url, user, pass);

				String result = "Database connection success\n";
				String sql = "UPDATE dbo.Route SET " + "StartingAddress=N'"
						+ params[0] + "', " + "DestinationAddress=N'"
						+ params[3] + "', StartTime=" + "CONVERT(datetime, '"
						+ params[4] + "', 103), FinishTime="
						+ "CONVERT(datetime, '" + params[5] + "', 103), Notes="
						+ null + ", Weight=" + params[6] + " WHERE RouteID = "
						+ id;
				PreparedStatement st = con.prepareStatement(sql);

				st.executeUpdate();

				if (!params[1].equals("")) {
					sql = "UPDATE dbo.RouteMarker SET RouteMarkerLocation = "
							+ "N'" + params[1] + "' " + "WHERE RouteID = " + id;
					st = con.prepareStatement(sql);
					st.executeUpdate();
				}

				if (!params[2].equals("")) {
					sql = "UPDATE dbo.RouteMarker SET RouteMarkerLocation = "
							+ "N'" + params[2] + "' " + "WHERE RouteID = " + id;
					st = con.prepareStatement(sql);
					st.executeUpdate();
				}

				if (params[7].equals("true")) {
					sql = "SELECT * FROM RouteGoodsCategory WHERE GoodsCategoryID = 1 AND RouteID = "
							+ id;
					st = con.prepareStatement(sql);
					ResultSet rs = st.executeQuery();
					if (!rs.isBeforeFirst()) {
						sql = "INSERT INTO dbo.RouteGoodsCategory VALUES ("
								+ id + ", 1)";
						st = con.prepareStatement(sql);
						st.executeUpdate();
					}
				} else {
					sql = "SELECT * FROM dbo.RouteGoodsCategory WHERE GoodsCategoryID = 1 AND RouteID = "
							+ id;
					st = con.prepareStatement(sql);
					ResultSet rs = st.executeQuery();
					if (rs.isBeforeFirst()) {
						sql = "DELETE FROM dbo.RouteGoodsCategory WHERE GoodsCategoryID = 1 AND RouteID = "
								+ id;
						st = con.prepareStatement(sql);
						st.executeUpdate();
					}
				}

				if (params[8].equals("true")) {
					sql = "SELECT * FROM RouteGoodsCategory WHERE GoodsCategoryID = 2 AND RouteID = "
							+ id;
					st = con.prepareStatement(sql);
					ResultSet rs = st.executeQuery();
					if (!rs.isBeforeFirst()) {
						sql = "INSERT INTO dbo.RouteGoodsCategory VALUES ("
								+ id + ", 2)";
						st = con.prepareStatement(sql);
						st.executeUpdate();
					}
				} else {
					sql = "SELECT * FROM dbo.RouteGoodsCategory WHERE GoodsCategoryID = 2 AND RouteID = "
							+ id;
					st = con.prepareStatement(sql);
					ResultSet rs = st.executeQuery();
					if (rs.isBeforeFirst()) {
						sql = "DELETE FROM dbo.RouteGoodsCategory WHERE GoodsCategoryID = 2 AND RouteID = "
								+ id;
						st = con.prepareStatement(sql);
						st.executeUpdate();
					}
				}
				if (params[9].equals("true")) {
					sql = "SELECT * FROM RouteGoodsCategory WHERE GoodsCategoryID = 3 AND RouteID = "
							+ id;
					st = con.prepareStatement(sql);
					ResultSet rs = st.executeQuery();
					if (!rs.isBeforeFirst()) {
						sql = "INSERT INTO dbo.RouteGoodsCategory VALUES ("
								+ id + ", 3)";
						st = con.prepareStatement(sql);
						st.executeUpdate();
					}
				} else {
					sql = "SELECT * FROM dbo.RouteGoodsCategory WHERE GoodsCategoryID = 3 AND RouteID = "
							+ id;
					st = con.prepareStatement(sql);
					ResultSet rs = st.executeQuery();
					if (rs.isBeforeFirst()) {
						sql = "DELETE FROM dbo.RouteGoodsCategory WHERE GoodsCategoryID = 3 AND RouteID = "
								+ id;
						st = con.prepareStatement(sql);
						st.executeUpdate();
					}
				}

				if (params[10].equals("true")) {
					sql = "SELECT * FROM RouteGoodsCategory WHERE GoodsCategoryID = 4 AND RouteID = "
							+ id;
					st = con.prepareStatement(sql);
					ResultSet rs = st.executeQuery();
					if (!rs.isBeforeFirst()) {
						sql = "INSERT INTO dbo.RouteGoodsCategory VALUES ("
								+ id + ", 4)";
						st = con.prepareStatement(sql);
						st.executeUpdate();
					}
				} else {
					sql = "SELECT * FROM dbo.RouteGoodsCategory WHERE GoodsCategoryID = 4 AND RouteID = "
							+ id;
					st = con.prepareStatement(sql);
					ResultSet rs = st.executeQuery();
					if (rs.isBeforeFirst()) {
						sql = "DELETE FROM dbo.RouteGoodsCategory WHERE GoodsCategoryID = 4 AND RouteID = "
								+ id;
						st = con.prepareStatement(sql);
						st.executeUpdate();
					}
				}
				sql = "UPDATE dbo.Deal SET Active = false WHERE RouteID = "
						+ id;
				st = con.prepareStatement(sql);
				st.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
				// tv.setText(e.toString());
				return false;
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {

		}
	}

	private class GetRoute extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			ArrayList<String> array = new ArrayList<String>();

			try {
				Class.forName("net.sourceforge.jtds.jdbc.Driver");

				Connection con = DriverManager.getConnection(url, user, pass);

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
					start.setText(rs.getString("StartingAddress"));
					end.setText(rs.getString("DestinationAddress"));
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					startDate.setText(sdf.format(rs.getDate("StartTime")));
					endDate.setText(sdf.format(rs.getDate("FinishTime")));
					sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm");
					Date date = sdf.parse(rs.getString("StartTime"));
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					startHour.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":"
							+ calendar.get(Calendar.MINUTE));
					payload.setText(rs.getString("Weight"));
				}
				int i = 0;
				while (rs2.next()) {
					if (i == 0) {
						p1.setText(rs2.getString("RouteMarkerLocation"));
					} else {
						p2.setText(rs2.getString("RouteMarkerLocation"));
					}
					i++;
				}

				while (rs3.next()) {
					if (rs3.getInt("GoodsCategoryID") == 1) {
						frozen.setChecked(true);
					} else if (rs3.getInt("GoodsCategoryID") == 2) {
						broken.setChecked(true);
					} else if (rs3.getInt("GoodsCategoryID") == 3) {
						flammable.setChecked(true);
					} else if (rs3.getInt("GoodsCategoryID") == 4) {
						others.setChecked(true);
					}
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
}
