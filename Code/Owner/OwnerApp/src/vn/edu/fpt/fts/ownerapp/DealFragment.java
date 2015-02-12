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

import vn.edu.fpt.fts.classes.Deal;
import vn.edu.fpt.fts.common.Common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DealFragment extends Fragment {
	private String goodsID;
	private ArrayAdapter<String> adapter;
	private List<Deal> list;
	private ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_deal, container,
				false);
		listView = (ListView) rootView.findViewById(R.id.listview_deal_list);

		goodsID = getActivity().getIntent().getStringExtra("goodsID");

		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK,
				getActivity(), "Đang xử lý...");
		wst.addNameValuePair("gouteID", goodsID);
		String url = Common.IP_URL + Common.Service_Deal_GetByGoodsID;
		wst.execute(new String[] { url });

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				int pos = listView.getPositionForView(view);				
				int dealID = list.get(pos).getDealID();
				int dealStatus = list.get(pos).getDealStatusID();
				int routeID = list.get(pos).getRouteID();
				int goodsID = list.get(pos).getGoodsID();
				double price = list.get(pos).getPrice();
				String note = list.get(pos).getNotes();
				Intent intent = new Intent(getActivity(), DealDetailActivity.class);
				intent.putExtra("dealID", dealID);
				intent.putExtra("dealStatus", dealStatus);
				intent.putExtra("routeID", routeID);
				intent.putExtra("goodsID", goodsID);
				intent.putExtra("price", price);
				intent.putExtra("note", note);
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
				JSONArray array = jsonObject.getJSONArray("deal");
				list = new ArrayList<Deal>();
				String[] price = new String[array.length()];
				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonObject2 = array.getJSONObject(i);
					Deal deal = new Deal();
					deal.setActive(Integer.parseInt(jsonObject2
							.getString("active")));
					deal.setCreateBy(jsonObject2.getString("createBy"));
					deal.setCreateTime(jsonObject2.getString("createTime"));
					deal.setDealID(Integer.parseInt(jsonObject2
							.getString("dealID")));
					deal.setDealStatusID(Integer.parseInt(jsonObject2
							.getString("dealStatusID")));
					deal.setGoodsID(Integer.parseInt(jsonObject2
							.getString("goodsID")));
					deal.setNotes(jsonObject2.getString("notes"));
					deal.setPrice(Double.parseDouble(jsonObject2
							.getString("price")));
					deal.setRefDealID(Integer.parseInt(jsonObject2
							.getString("refDealID")));
					deal.setRouteID(Integer.parseInt(jsonObject2
							.getString("routeID")));
					list.add(deal);
				}

				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getDealStatusID() == 3
							|| list.get(i).getDealStatusID() == 4 || list.get(i).getActive() != 1) {
						list.remove(i);
					}
					price[i] = (int) list.get(i).getPrice() + "";
				}

				adapter = new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_list_item_1, price);
				listView.setAdapter(adapter);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(getActivity(), "Giao dịch không thể tải được",
						Toast.LENGTH_LONG).show();
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
