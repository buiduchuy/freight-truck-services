package vn.edu.fpt.fts.layout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import vn.edu.fpt.fts.classes.Constant;

public class SuggestList extends Fragment {

	private class WebService extends AsyncTask<String, Integer, String> {

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;
		public static final int GET_TASK = 2;

		public static final int POST_TASK = 1;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 5000;

		private static final String TAG = "WebServiceTask";

		private Context mContext = null;
		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		private ProgressDialog pDlg = null;

		private String processMessage = "Processing...";

		private int taskType = GET_TASK;

		public WebService(int taskType, Context mContext, String processMessage) {

			this.taskType = taskType;
			this.mContext = mContext;
			this.processMessage = processMessage;
		}

		public void addNameValuePair(String name, String value) {

			params.add(new BasicNameValuePair(name, value));
		}

		@Override
		protected String doInBackground(String... urls) {
			String url = urls[0];
			String result = "";

			HttpResponse response = doResponse(url);

			if (response.getEntity() == null) {
				return result;
			} else {
				try {
					result = inputStreamToString(response.getEntity()
							.getContent());

				} catch (IllegalStateException e) {
					Log.e(TAG, e.getLocalizedMessage(), e);

				} catch (IOException e) {
					Log.e(TAG, e.getLocalizedMessage(), e);
				}

			}

			return result;
		}

		private HttpResponse doResponse(String url) {

			// Use our connection and data timeouts as parameters for our
			// DefaultHttpClient
			HttpClient httpclient = new DefaultHttpClient(getHttpParams());

			HttpResponse response = null;

			try {
				switch (taskType) {

				case POST_TASK:
					HttpPost httppost = new HttpPost(url);
					// Add parameters
					httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

					response = httpclient.execute(httppost);
					break;
				case GET_TASK:
					HttpGet httpget = new HttpGet(url);
					response = httpclient.execute(httpget);
					break;
				}
			} catch (Exception e) {

				Log.e(TAG, e.getLocalizedMessage(), e);

			}

			return response;
		}

		// Establish connection and socket (data retrieval) timeouts
		private HttpParams getHttpParams() {

			HttpParams htpp = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
			HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

			return htpp;
		}

		private String inputStreamToString(InputStream is) {

			String line = "";
			StringBuilder total = new StringBuilder();

			// Wrap a BufferedReader around the InputStream
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			try {
				// Read response until the end
				while ((line = rd.readLine()) != null) {
					total.append(line);
				}
			} catch (IOException e) {
				Log.e(TAG, e.getLocalizedMessage(), e);
			}

			// Return full string
			return total.toString();
		}

		@Override
		protected void onPostExecute(String response) {
			// Xu li du lieu tra ve sau khi insert thanh cong
			// handleResponse(response);
			JSONObject obj;
			try {
				obj = new JSONObject(response);
				JSONArray array = obj.getJSONArray("route");
				for (int i = 0; i < array.length(); i++) {
					JSONObject item = array.getJSONObject(i);
					String[] start = item.getString("startingAddress").replace(", Vietnam", "").split(",");
					String[] end = item.getString("destinationAddress").replace(", Vietnam", "").split(",");
					String itm = "Lộ trình: " + start[start.length - 1] + " - " + end[end.length - 1] ;
					list.add(itm);
					map.put(new Long(i), Integer.parseInt(item.getString("routeID")));
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.listview_item_row, list);
			    list1.setAdapter(adapter);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pDlg.dismiss();
		}

		@Override
		protected void onPreExecute() {
			showProgressDialog();

		}

		private void showProgressDialog() {

			pDlg = new ProgressDialog(mContext);
			pDlg.setMessage(processMessage);
			pDlg.setProgressDrawable(mContext.getWallpaper());
			pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDlg.setCancelable(false);
			pDlg.show();

		}
	}
	private static final String SERVICE_URL = Constant.SERVICE_URL + "Route/get";
	ArrayList<String> list;
	ListView list1;
	
	@SuppressLint("UseSparseArrays")
	HashMap<Long, Integer> map = new HashMap<Long, Integer>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		list = new ArrayList<String>();
		getActivity().setTitle("Gợi ý hệ thống");
		WebService ws = new WebService(WebService.GET_TASK,
				getActivity(), "Đang lấy dữ liệu ...");
		ws.execute(new String[] { SERVICE_URL });
	    View myFragmentView = inflater.inflate(R.layout.activity_suggest_list, container, false);
	    list1 = (ListView) myFragmentView.findViewById(R.id.listView1);
	    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.listview_item_row, list);
	    list1.setAdapter(adapter1);
	    list1.setOnItemClickListener((new AdapterView.OnItemClickListener() {
	    	@Override
	    	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	    			long arg3) {
	    		int id = map.get(arg3);
	    		FragmentManager mng = getActivity().getSupportFragmentManager();
	    		FragmentTransaction trs = mng.beginTransaction();
	    		SystemSuggest frag = new SystemSuggest();
	    		Bundle bundle = new Bundle();
	    		bundle.putString("routeID", String.valueOf(id));
	    		frag.setArguments(bundle);
	    		trs.replace(R.id.content_frame, frag);
	    		trs.addToBackStack(null);
	    		trs.commit();
	    	}
		}));
		return myFragmentView;
	}
}
