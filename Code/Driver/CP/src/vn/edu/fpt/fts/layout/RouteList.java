package vn.edu.fpt.fts.layout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RouteList extends Fragment {
	
	Calendar cal = Calendar.getInstance();
	HashMap<Long, Integer> map = new HashMap<Long, Integer>();
	private static final String url = "jdbc:jtds:sqlserver://10.0.3.2:1433;instance=MSSQLSERVER;DatabaseName=FTS";
	private static final String user = "sa";
	private static final String pass = "123456";
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ArrayList<String> array = new ArrayList<String>();
		try {
			array = new GetRouteList().execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    View myFragmentView = inflater.inflate(R.layout.activity_route_list, container, false);
	    ListView list1 = (ListView) myFragmentView.findViewById(R.id.listView1);
	    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.listview_item_row, array);
	    list1.setAdapter(adapter1);
	    list1.setOnItemClickListener((new AdapterView.OnItemClickListener() {
	    	@Override
	    	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	    			long arg3) {
	    		int id = map.get(arg3);
	    		FragmentManager mng = getActivity().getSupportFragmentManager();
	    		FragmentTransaction trs = mng.beginTransaction();
	    		CurrentRoute frag = new CurrentRoute();
	    		Bundle bundle = new Bundle();
	    		bundle.putString("id", String.valueOf(id));
	    		frag.setArguments(bundle);
	    		trs.replace(R.id.content_frame, frag);
	    		trs.addToBackStack(null);
	    		trs.commit();
	    	}
		}));
		return myFragmentView;
	}
	
	private class GetRouteList extends AsyncTask<String, Void, ArrayList<String>> {
		@Override
		protected ArrayList<String> doInBackground(String... params) {
			ArrayList<String> array = new ArrayList<String>();

			try {
				Class.forName("net.sourceforge.jtds.jdbc.Driver");

				Connection con = DriverManager.getConnection(url, user, pass);

				String result = "Database connection success\n";
				String sql = "SELECT * FROM dbo.Route WHERE Active = 1";
				PreparedStatement st = con.prepareStatement(sql);

				ResultSet rs = st.executeQuery();
				int i = 0;
				while (rs.next()) {
					String[] startP = rs.getString("StartingAddress").replace(", Vietnam", "").split(",");
					String startCity = startP[startP.length-1];
					String[] endP = rs.getString("DestinationAddress").replace(", Vietnam", "").split(",");
					String endCity = endP[endP.length-1];
					String item = "Lộ trình: " + startCity + " - " + endCity;
					array.add(item);
					map.put(new Long(i), rs.getInt("RouteID"));
					i++;
				}
			} catch (Exception e) {
				e.printStackTrace();
				// tv.setText(e.toString());
				return null;
			}
			return array;
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {

		}
	}
}
