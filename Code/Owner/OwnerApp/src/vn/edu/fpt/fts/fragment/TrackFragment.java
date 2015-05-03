package vn.edu.fpt.fts.fragment;

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

import vn.edu.fpt.fts.activity.OrderDetailActivity;
import vn.edu.fpt.fts.adapter.GoodsModelAdapter;
import vn.edu.fpt.fts.adapter.OrderModelAdapter;
import vn.edu.fpt.fts.classes.OrderModel;
import vn.edu.fpt.fts.common.Common;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;

public class TrackFragment extends Fragment {
	private ListView listView;
	private OrderModelAdapter adapter;
	private String ownerID;
	List<String> orderID;
	private TextView tvGone;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_track, container,
				false);
		orderID = new ArrayList<String>();
		listView = (ListView) rootView.findViewById(R.id.listview_order);

		SharedPreferences preferences = getActivity().getSharedPreferences(
				"MyPrefs", Context.MODE_PRIVATE);
		ownerID = preferences.getString("ownerID", "");
		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK,
				getActivity(), "Đang xử lý...");
		String url = Common.IP_URL + Common.Service_Order_get;
		wst.addNameValuePair("ownerID", ownerID);
		// wst.execute(new String[] { url });
		wst.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
				new String[] { url });

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				int pos = listView.getPositionForView(view);

				Intent intent = new Intent(view.getContext(),
						OrderDetailActivity.class);
				intent.putExtra("orderID", orderID.get(pos));
				String test = orderID.get(pos);
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
		private static final int SOCKET_TIMEOUT = 30000;

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
			if (response.equals("null")) {
				tvGone = (TextView) getActivity().findViewById(
						R.id.textview_gone);
				tvGone.setVisibility(View.VISIBLE);
			} else {
				try {
					JSONObject jsonObject = new JSONObject(response);
					Object obj = jsonObject.get("order");
					List<String> lv = new ArrayList<String>();
					ArrayList<OrderModel> orderModels = new ArrayList<OrderModel>();
					// String[] result = new String[array.length()];
					if (obj instanceof JSONArray) {
						JSONArray array = jsonObject.getJSONArray("order");
						for (int i = 0; i < array.length(); i++) {
							JSONObject jsonObject2 = array.getJSONObject(i);
							JSONObject jsonObject3 = jsonObject2
									.getJSONObject("deal");
							JSONObject jsonObject4 = jsonObject3
									.getJSONObject("goods");
							JSONObject jsonObject5 = jsonObject4
									.getJSONObject("goodsCategory");
							String categoryName = jsonObject5.getString("name");
							String number = jsonObject4.getString("createTime");
							String[] tmp = number.split(" ");
							number = " #" + tmp[0].replace("-", "")
									+ jsonObject4.getString("goodsID");
							String object = categoryName + number;
							lv.add(object);
							orderID.add(jsonObject2.getString("orderID"));
							// new list
							String price = jsonObject2.getString("price")
									.replace(".0", "") + " nghìn";
							String weight = jsonObject4.getString("weight")
									+ " kg";
							String pickupDate = jsonObject4
									.getString("pickupTime");
							String deliverDate = jsonObject4
									.getString("deliveryTime");
							String[] tmp1 = pickupDate.split(" ");
							String[] tmp2 = deliverDate.split(" ");
							String date = Common.formatDateFromString(tmp1[0])
									+ " - "
									+ Common.formatDateFromString(tmp2[0]);
							int count = Integer.parseInt(jsonObject2
									.getString("orderStatusID"));
							String status = "";
							switch (count) {
							case 1:
								status = "Chưa trả tiền";
								break;
							case 2:
								status = "Đã trả tiền";
								break;
							case 3:
								status = "Đang chở hàng";
								break;
							case 4:
								status = "Đã giao hàng";
								break;
							case 5:
								status = "Đã bị hủy";
								break;
							case 6:
								status = "Đã hoàn tiền";
								break;
							case 7:
								status = "Đã giao hàng";
								break;
							}
							OrderModel orderModel = new OrderModel(
									categoryName, weight, date, price, status);
							orderModels.add(orderModel);

						}
					} else if (obj instanceof JSONObject) {
						JSONObject jsonObject2 = jsonObject
								.getJSONObject("order");
						JSONObject jsonObject3 = jsonObject2
								.getJSONObject("deal");
						JSONObject jsonObject4 = jsonObject3
								.getJSONObject("goods");
						JSONObject jsonObject5 = jsonObject4
								.getJSONObject("goodsCategory");
						String categoryName = jsonObject5.getString("name");
						String number = jsonObject4.getString("createTime");
						String[] tmp = number.split(" ");
						number = " #" + tmp[0].replace("-", "")
								+ jsonObject4.getString("goodsID");
						String object = categoryName + number;
						lv.add(object);
						orderID.add(jsonObject2.getString("orderID"));
						// new list
						String price = jsonObject2.getString("price").replace(
								".0", "")
								+ " nghìn";
						String weight = jsonObject4.getString("weight") + " kg";
						String pickupDate = jsonObject4.getString("pickupTime");
						String deliverDate = jsonObject4
								.getString("deliveryTime");
						String[] tmp1 = pickupDate.split(" ");
						String[] tmp2 = deliverDate.split(" ");
						String date = Common.formatDateFromString(tmp1[0])
								+ " - " + Common.formatDateFromString(tmp2[0]);
						int count = Integer.parseInt(jsonObject2
								.getString("orderStatusID"));
						String status = "";
						switch (count) {
						case 1:
							status = "Chưa trả tiền";
							break;
						case 2:
							status = "Đã trả tiền";
							break;
						case 3:
							status = "Đang chở hàng";
							break;
						case 4:
							status = "Đã giao hàng";
							break;
						case 5:
							status = "Đã bị hủy";
							break;
						case 6:
							status = "Đã hoàn tiền";
							break;
						case 7:
							status = "Đã giao hàng";
							break;
						}
						OrderModel orderModel = new OrderModel(categoryName,
								weight, date, price, status);
						orderModels.add(orderModel);

					}
					if (orderModels.size() == 0) {
						tvGone = (TextView) getActivity().findViewById(
								R.id.textview_gone);
						tvGone.setVisibility(View.VISIBLE);
					}

					adapter = new OrderModelAdapter(getActivity(), orderModels);
					listView.setAdapter(adapter);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.e(TAG, e.getLocalizedMessage());
					tvGone = (TextView) getActivity().findViewById(
							R.id.textview_gone);
					tvGone.setVisibility(View.VISIBLE);
				}
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
