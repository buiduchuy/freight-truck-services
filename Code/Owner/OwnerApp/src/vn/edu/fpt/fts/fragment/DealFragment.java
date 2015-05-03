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

import vn.edu.fpt.fts.activity.DealDetailActivity;
import vn.edu.fpt.fts.adapter.DealModelAdapter;
import vn.edu.fpt.fts.classes.Deal;
import vn.edu.fpt.fts.classes.DealModel;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DealFragment extends Fragment {
	private String goodsID;
	private DealModelAdapter adapter;
	private List<Deal> list;
	private ListView listView;
	private TextView tvGone;

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
		// wst.execute(new String[] { url });
		wst.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
				new String[] { url });

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				int pos = listView.getPositionForView(view);
				int dealID = list.get(pos).getDealID();
				int dealStatus = list.get(pos).getDealStatusID();
				String createBy = list.get(pos).getCreateBy();
				int routeID = list.get(pos).getRouteID();
				int goodsID = list.get(pos).getGoodsID();
				double price = list.get(pos).getPrice();
				String note = list.get(pos).getNotes();
				Intent intent = new Intent(getActivity(),
						DealDetailActivity.class);
				intent.putExtra("dealID", dealID);
				intent.putExtra("dealStatus", dealStatus);
				intent.putExtra("createBy", createBy);
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
		private static final int SOCKET_TIMEOUT = 50000;

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
					Object obj = jsonObject.get("deal");

					String[] price = null;
					ArrayList<DealModel> dealModels = new ArrayList<DealModel>();
					if (obj instanceof JSONArray) {
						JSONArray array = jsonObject.getJSONArray("deal");
						list = new ArrayList<Deal>();

						for (int i = 0; i < array.length(); i++) {
							JSONObject jsonObject2 = array.getJSONObject(i);
							Deal deal = new Deal();
							int check = Integer.parseInt(jsonObject2
									.getString("dealStatusID"));
							int check2 = Integer.parseInt(jsonObject2
									.getString("active"));
							if (check != 2 && check != 3 && check != 4
									&& check2 != 0 && check2 != 2) {
								deal.setActive(Integer.parseInt(jsonObject2
										.getString("active")));
								deal.setCreateBy(jsonObject2
										.getString("createBy"));
								deal.setCreateTime(jsonObject2
										.getString("createTime"));
								deal.setDealID(Integer.parseInt(jsonObject2
										.getString("dealID")));
								deal.setDealStatusID(Integer
										.parseInt(jsonObject2
												.getString("dealStatusID")));
								deal.setGoodsID(Integer.parseInt(jsonObject2
										.getString("goodsID")));
								deal.setNotes(jsonObject2.getString("notes"));
								deal.setPrice(Double.parseDouble(jsonObject2
										.getString("price")));
								deal.setRouteID(Integer.parseInt(jsonObject2
										.getString("routeID")));
								list.add(deal);
								JSONObject jsonObject3 = jsonObject2
										.getJSONObject("route");
								String start = jsonObject3
										.getString("startingAddress");
								start = Common.formatLocation(start);
								String[] starts = start.split(",");
								String end = jsonObject3
										.getString("destinationAddress");
								end = Common.formatLocation(end);
								String[] ends = end.split(",");
								String date = jsonObject2
										.getString("createTime");
								String[] tmp = date.split(" ");
								String dPrice = jsonObject2.getString("price")
										.replace(".0", " nghìn");
								String status = "";
								String count = jsonObject2
										.getString("createBy");
								if (count.equals("driver")) {
									JSONObject jsonObject4 = jsonObject3
											.getJSONObject("driver");
									String name = jsonObject4
											.getString("lastName")
											+ " "
											+ jsonObject4
													.getString("firstName");
									status = "Tài xế " + name
											+ " đã gửi đề nghị: ";
								} else if (count.equals("owner")) {
									status = "Bạn đã đã gửi đề nghị: ";
								}
								DealModel dealModel = new DealModel(
										starts[starts.length - 1] + " - "
												+ ends[ends.length - 1],
										Common.formatDateFromString(tmp[0]),
										dPrice, status);
								dealModels.add(dealModel);
							}

						}
					} else if (obj instanceof JSONObject) {
						// price = new String[1];
						JSONObject jsonObject2 = jsonObject
								.getJSONObject("deal");
						list = new ArrayList<Deal>();
						Deal deal = new Deal();
						int check = Integer.parseInt(jsonObject2
								.getString("dealStatusID"));
						int check2 = Integer.parseInt(jsonObject2
								.getString("active"));
						if (check != 2 && check != 3 && check != 4
								&& check2 != 0 && check2 != 2) {
							deal.setActive(Integer.parseInt(jsonObject2
									.getString("active")));
							deal.setCreateBy(jsonObject2.getString("createBy"));
							deal.setCreateTime(jsonObject2
									.getString("createTime"));
							deal.setDealID(Integer.parseInt(jsonObject2
									.getString("dealID")));
							deal.setDealStatusID(Integer.parseInt(jsonObject2
									.getString("dealStatusID")));
							deal.setGoodsID(Integer.parseInt(jsonObject2
									.getString("goodsID")));
							deal.setNotes(jsonObject2.getString("notes"));
							deal.setPrice(Double.parseDouble(jsonObject2
									.getString("price")));
							deal.setRouteID(Integer.parseInt(jsonObject2
									.getString("routeID")));
							list.add(deal);
							JSONObject jsonObject3 = jsonObject2
									.getJSONObject("route");
							String start = jsonObject3
									.getString("startingAddress");
							start = Common.formatLocation(start);
							String[] starts = start.split(",");
							String end = jsonObject3
									.getString("destinationAddress");
							end = Common.formatLocation(end);
							String[] ends = end.split(",");
							String date = jsonObject2.getString("createTime");
							String[] tmp = date.split(" ");
							String dPrice = jsonObject2.getString("price")
									.replace(".0", " nghìn");
							String status = "";
							String count = jsonObject2.getString("createBy");
							if (count.equals("driver")) {
								JSONObject jsonObject4 = jsonObject3
										.getJSONObject("driver");
								String name = jsonObject4.getString("lastName")
										+ " "
										+ jsonObject4.getString("firstName");
								status = "Tài xế " + name + " đã gửi đề nghị: ";
							} else if (count.equals("owner")) {
								status = "Bạn đã đã gửi đề nghị: ";
							}
							DealModel dealModel = new DealModel(
									starts[starts.length - 1] + " - "
											+ ends[ends.length - 1],
									Common.formatDateFromString(tmp[0]),
									dPrice, status);
							dealModels.add(dealModel);
						}
					}

					if (dealModels.size() == 0) {
						tvGone = (TextView) getActivity().findViewById(
								R.id.textview_gone);
						tvGone.setVisibility(View.VISIBLE);
					}
					adapter = new DealModelAdapter(getActivity(), dealModels);
					listView.setAdapter(adapter);
					// for (Deal d : list) {
					// ArrayList<DealModel> dealModels = new
					// ArrayList<DealModel>();
					//
					// }
					// int size = list.size();
					// if (size != 0) {
					// price = new String[list.size()];
					//
					// for (int i = 0; i < list.size(); i++) {
					// price[i] = "Giao dịch giá: " + (int)
					// list.get(i).getPrice() + " nghìn đồng";
					// }
					// // String a = price[0];
					//
					// if (price[0] == null) {
					// Toast.makeText(getActivity(),
					// "Không có giao dịch mới",
					// Toast.LENGTH_LONG).show();
					// } else {
					// adapter = new ArrayAdapter<String>(getActivity(),
					// android.R.layout.simple_list_item_1, price);
					// listView.setAdapter(adapter);
					// }
					// } else {
					// tvGone =
					// (TextView)getActivity().findViewById(R.id.textview_gone);
					// tvGone.setVisibility(View.VISIBLE);
					// }

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
