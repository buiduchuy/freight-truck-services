package vn.edu.fpt.fts.ownerapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

import vn.edu.fpt.fts.adapter.ModelAdapter;
import vn.edu.fpt.fts.classes.Goods;
import vn.edu.fpt.fts.classes.Route;
import vn.edu.fpt.fts.common.Common;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GoodsFragment extends Fragment {	
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private String ownerid;
	private List<String> goodsID, goodsCategoryID;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_goods, container,
				false);

		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK,
				getActivity(), "Đang xử lý...");
		String url = Common.IP_URL + Common.Service_Goods_Get;
		SharedPreferences preferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
		ownerid = preferences.getString("ownerID", "");
		wst.addNameValuePair("ownerID", ownerid);
		wst.execute(new String[] { url });

		goodsID = new ArrayList<String>();
		goodsCategoryID = new ArrayList<String>();
		
		listView = (ListView) rootView.findViewById(R.id.listview_goods);
		

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				int pos = listView.getPositionForView(view);
				
				Intent intent = new Intent(view.getContext(),
						GoodsDetailActivity.class);
				String tmp = goodsCategoryID.get(pos);
				String tmp2 = goodsID.get(pos);
				
				
				intent.putExtra("goodsID", goodsID.get(pos));
				intent.putExtra("goodsCategoryID", goodsCategoryID.get(pos));
				
				startActivity(intent);
			}
		});

		return rootView;
	}

	private class WebServiceTask extends AsyncTask<String, Integer, String> {

		public static final int POST_TASK = 1;
		public static final int GET_TASK = 2;

		private static final String TAG = "WebServiceTask";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 5000;

		private int taskType = GET_TASK;
		private Context mContext = null;
		private String processMessage = "Processing...";

		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		private ProgressDialog pDlg = null;

		public WebServiceTask(int taskType, Context mContext,
				String processMessage) {

			this.taskType = taskType;
			this.mContext = mContext;
			this.processMessage = processMessage;
		}

		public void addNameValuePair(String name, String value) {

			params.add(new BasicNameValuePair(name, value));
		}

		private void showProgressDialog() {

			pDlg = new ProgressDialog(mContext);
			pDlg.setMessage(processMessage);
			pDlg.setProgressDrawable(mContext.getWallpaper());
			pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDlg.setCancelable(false);
			pDlg.show();

		}

		@Override
		protected void onPreExecute() {

			showProgressDialog();

		}

		protected String doInBackground(String... urls) {

			String url = urls[0];
			String result = "";

			HttpResponse response = doResponse(url);

			if (response == null) {
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

		@Override
		protected void onPostExecute(String response) {
			// Xu li du lieu tra ve sau khi insert thanh cong
			// handleResponse(response);
			try {				
				JSONObject jsonObject = new JSONObject(response);
				JSONArray array = jsonObject.getJSONArray("goods");				
				String[] result = new String[array.length()];
				for (int i = 0; i < array.length(); i++) {
//					Goods goods = new Goods();					
//					goods.setGoodsID(Integer.parseInt(jsonObject2.getString("goodsID")));
//					goods.setWeight(Integer.parseInt(jsonObject2.getString("weight")));
//					goods.setPrice(Double.parseDouble(jsonObject2.getString("price")));
//					goods.setPickupTime(jsonObject2.getString("pickupTime"));
//					goods.setPickupAddress(jsonObject2.getString("pickupAddress"));
//					goods.setDeliveryTime(jsonObject2.getString("deliveryTime"));
//					goods.setDeliveryAddress(jsonObject2.getString("deliveryAddress"));
//					try {goods.setPickupMarkerLongtitude(Float.parseFloat(jsonObject2.getString("pickupMarkerLongtitude")));} catch (JSONException e) {goods.setPickupMarkerLongtitude(0);}
//					try {goods.setPickupMarkerLatidute(Float.parseFloat(jsonObject2.getString("pickupMarkerLatidute")));} catch (JSONException e) {goods.setPickupMarkerLatidute(0);}
//					try {goods.setDeliveryMarkerLongtitude(Float.parseFloat(jsonObject2.getString("deliveryMarkerLongtitude")));} catch (JSONException e) {goods.setDeliveryMarkerLongtitude(0);}
//					try {goods.setDeliveryMarkerLatidute(Float.parseFloat(jsonObject2.getString("deliveryMarkerLatidute")));} catch (JSONException e) {goods.setDeliveryMarkerLatidute(0);}
//					try {goods.setNotes(jsonObject2.getString("notes"));} catch (JSONException e) {goods.setNotes("");}
//					goods.setCreateTime(jsonObject2.getString("createTime"));
//					goods.setActive(Integer.parseInt(jsonObject2.getString("active")));
//					goods.setOwnerID(Integer.parseInt(jsonObject2.getString("ownerID")));
//					goods.setGoodsCategoryID(Integer.parseInt(jsonObject2.getString("goodsCategoryID")));
//					list.add(goods);
					JSONObject jsonObject2 = array.getJSONObject(i);
					String a = jsonObject2.getString("goodsID");
					
					
					goodsID.add(jsonObject2.getString("goodsID"));
					goodsCategoryID.add(jsonObject2.getString("goodsCategoryID"));
					
					String createTime = jsonObject2.getString("createTime");
					String tmp[] = createTime.split(" ");
					createTime = tmp[0].replace("-", "") + jsonObject2.getString("goodsID");
					
					
					JSONObject jsonObject3 = jsonObject2.getJSONObject("goodsCategory");
					result[i] = jsonObject3.getString("name") + " " + "#" + createTime;
					
				}
				adapter = new ArrayAdapter<String>(
						getActivity(), android.R.layout.simple_list_item_1,
						result);
				listView.setAdapter(adapter);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, e.getLocalizedMessage());
			}
			pDlg.dismiss();
			
			

		}

		// Establish connection and socket (data retrieval) timeouts
		private HttpParams getHttpParams() {

			HttpParams htpp = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
			HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

			return htpp;
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
					httppost.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));

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

	}
}
