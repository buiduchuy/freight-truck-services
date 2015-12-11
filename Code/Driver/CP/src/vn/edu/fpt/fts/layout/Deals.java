package vn.edu.fpt.fts.layout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
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
import android.app.ActionBar;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import vn.edu.fpt.fts.classes.Constant;
import vn.edu.fpt.fts.drawer.ListItem;
import vn.edu.fpt.fts.drawer.ListItemAdapter4;

public class Deals extends Fragment {
	private class WebService extends AsyncTask<String, Integer, String> {

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 30000;
		public static final int GET_TASK = 2;

		public static final int POST_TASK = 1;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 60000;

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
			} catch (ConnectTimeoutException e) {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(),
								"Không thể kết nối tới máy chủ",
								Toast.LENGTH_SHORT).show();
					}
				});
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
			if (!response.equals("null")) {
				try {
					int count = 1;
					obj = new JSONObject(response);
					Object intervent = obj.get("deal");
					if (intervent instanceof JSONArray) {
						JSONArray array = obj.getJSONArray("deal");
						object = array;
						for (int i = 0; i < array.length(); i++) {
							JSONObject item = array.getJSONObject(i);

							JSONObject gd = item.getJSONObject("goods");
							if (gd.getString("active").equals("1")) {
								String title = "";
								String[] start = gd.getString("pickupAddress")
										.replaceAll("(?i), Vietnam", "")
										.replaceAll("(?i), Viet Nam", "")
										.replaceAll("(?i), Việt Nam", "")
										.split(",");
								title = start[start.length - 1].trim();

								String[] end = gd.getString("deliveryAddress")
										.replaceAll("(?i), Vietnam", "")
										.replaceAll("(?i), Viet Nam", "")
										.replaceAll("(?i), Việt Nam", "")
										.split(",");
								title += " - " + end[end.length - 1].trim();
								SimpleDateFormat format = new SimpleDateFormat(
										"yyyy-MM-dd hh:mm:ss");
								Date createDate = format.parse(item
										.getString("createTime"));
								Calendar cal = Calendar.getInstance();
								cal.setTime(createDate);
								cal.add(Calendar.MONTH, 3);
								Date timeout = cal.getTime();
								format.applyPattern("dd/MM/yyyy");
								String createD = format.format(createDate);
								JSONObject rt = item.getJSONObject("route");
								String title2 = "";

								String[] start1 = rt
										.getString("startingAddress")
										.replaceAll("(?i), Vietnam", "")
										.replaceAll("(?i), Viet Nam", "")
										.replaceAll("(?i), Việt Nam", "")
										.split(",");

								title2 = start1[start1.length - 1].trim();

								String[] end2 = rt
										.getString("destinationAddress")
										.replaceAll("(?i), Vietnam", "")
										.replaceAll("(?i), Viet Nam", "")
										.replaceAll("(?i), Việt Nam", "")
										.split(",");
								title2 += " - " + end2[end2.length - 1].trim();

								if (Calendar.getInstance().getTime()
										.before(timeout)) {
									JSONObject owner = item.getJSONObject(
											"goods").getJSONObject("owner");
									list.add(new ListItem(
											"Bạn đã gửi đề nghị cho "
													+ owner.getString("email"),
											"Lộ trình: " + title2, "Hàng hóa: "
													+ title, "Giá đề nghị: "
													+ item.getString("price")
															.replace(".0", "")
													+ " nghìn đồng", createD));
									count++;
									map.add(item.getString("dealID"));
								}
							}
						}
					} else if (intervent instanceof JSONObject) {
						JSONObject item = obj.getJSONObject("deal");
						object = item;

						JSONObject gd = item.getJSONObject("goods");
						if (gd.getString("active").equals("1")) {
							String title = "";
							String[] start = gd.getString("pickupAddress")
									.replaceAll("(?i), Vietnam", "")
									.replaceAll("(?i), Viet Nam", "")
									.replaceAll("(?i), Việt Nam", "")
									.split(",");
							title = start[start.length - 1].trim();

							String[] end = gd.getString("deliveryAddress")
									.replaceAll("(?i), Vietnam", "")
									.replaceAll("(?i), Viet Nam", "")
									.replaceAll("(?i), Việt Nam", "")
									.split(",");
							title += " - " + end[end.length - 1].trim();
							SimpleDateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd hh:mm:ss");
							Date createDate = format.parse(item
									.getString("createTime"));
							Calendar cal = Calendar.getInstance();
							cal.setTime(createDate);
							cal.add(Calendar.MONTH, 3);
							Date timeout = cal.getTime();
							format.applyPattern("dd/MM/yyyy");
							String createD = format.format(createDate);
							JSONObject rt = item.getJSONObject("route");
							String title2 = "";

							String[] start1 = rt.getString("startingAddress")
									.replaceAll("(?i), Vietnam", "")
									.replaceAll("(?i), Viet Nam", "")
									.replaceAll("(?i), Việt Nam", "")
									.split(",");

							title2 = start1[start1.length - 1].trim();

							String[] end2 = rt.getString("destinationAddress")
									.replaceAll("(?i), Vietnam", "")
									.replaceAll("(?i), Viet Nam", "")
									.replaceAll("(?i), Việt Nam", "")
									.split(",");
							title2 += " - " + end2[end2.length - 1].trim();

							if (Calendar.getInstance().getTime()
									.before(timeout)) {
								JSONObject owner = item.getJSONObject("goods")
										.getJSONObject("owner");
								list.add(new ListItem("Bạn đã gửi đề nghị cho "
										+ owner.getString("email"),
										"Lộ trình: " + title2, "Hàng hóa: "
												+ title, "Giá đề nghị: "
												+ item.getString("price")
														.replace(".0", "")
												+ " nghìn đồng", createD));
								count++;
								map.add(item.getString("dealID"));
							}
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			adapter = new ListItemAdapter4(getActivity(), list);
			list1.setEmptyView(myFragmentView.findViewById(R.id.emptyElement));
			list1.setAdapter(adapter);
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
	public class WebService2 extends AsyncTask<String, Integer, String> {

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 30000;
		public static final int GET_TASK = 2;

		public static final int POST_TASK = 1;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 30000;

		private static final String TAG = "WebServiceTask";

		private Context mContext = null;
		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		private ProgressDialog pDlg = null;

		private String processMessage = "Processing...";

		private int taskType = GET_TASK;

		public WebService2(int taskType, Context mContext, String processMessage) {

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
			} catch (ConnectTimeoutException e) {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(),
								"Không thể kết nối tới máy chủ",
								Toast.LENGTH_SHORT).show();
					}
				});
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
			if (Integer.parseInt(response) > 0) {
				Toast.makeText(getActivity(), "Hủy đề nghị thành công",
						Toast.LENGTH_SHORT).show();
				FragmentManager mng = getActivity().getSupportFragmentManager();
				FragmentTransaction trs = mng.beginTransaction();
				Fragment fragment = new TabDeals();
				trs.replace(R.id.content_frame, fragment);
				trs.addToBackStack(null);
				trs.commit();
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
	private static final String SERVICE_URL = Constant.SERVICE_URL
			+ "Deal/getPendingDealCreateByDriverByDriverID";
	private static final String SERVICE_URL2 = Constant.SERVICE_URL
			+ "Deal/cancel";
	ListItemAdapter4 adapter;
	ArrayList<ListItem> list;
	ListView list1;
	@SuppressLint("UseSparseArrays")
	ArrayList<String> map = new ArrayList<String>();

	View myFragmentView;

	Object object;

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getTitle().equals("Hủy")) {
			int id = Integer.parseInt(map.get(item.getItemId()));
			if (object instanceof JSONArray) {
				JSONArray array = (JSONArray) object;
				for (int i = 0; i < array.length(); i++) {
					try {
						JSONObject obj = array.getJSONObject(i);
						if (obj.getString("dealID").equals(String.valueOf(id))) {
							WebService2 ws = new WebService2(
									WebService2.POST_TASK, getActivity(),
									"Đang xử lý ...");
							ws.addNameValuePair("dealID",
									obj.getString("dealID"));
							ws.addNameValuePair("price", obj.getString("price"));
							ws.addNameValuePair("notes", obj.getString("notes"));
							SimpleDateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd hh:mm");
							String createTime = format.format(Calendar
									.getInstance().getTime());
							ws.addNameValuePair("createTime", createTime);
							ws.addNameValuePair("createBy", "driver");
							ws.addNameValuePair(
									"routeID",
									obj.getJSONObject("route").getString(
											"routeID"));
							ws.addNameValuePair(
									"goodsID",
									obj.getJSONObject("goods").getString(
											"goodsID"));
							String refID = obj.getString("refDealID");
							if (refID.equals("0")) {
								ws.addNameValuePair("refDealID", "");
							} else {
								ws.addNameValuePair("refDealID", refID);
							}
							ws.addNameValuePair("active", "1");
							ws.execute(new String[] { SERVICE_URL2 });
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				JSONObject obj = (JSONObject) object;
				WebService2 ws = new WebService2(WebService2.POST_TASK,
						getActivity(), "Đang xử lý ...");
				try {
					ws.addNameValuePair("dealID", obj.getString("dealID"));
					ws.addNameValuePair("price", obj.getString("price"));
					ws.addNameValuePair("notes", obj.getString("notes"));
					SimpleDateFormat format = new SimpleDateFormat(
							"yyyy-MM-dd hh:mm");
					String createTime = format.format(Calendar.getInstance()
							.getTime());
					ws.addNameValuePair("createTime", createTime);
					ws.addNameValuePair("createBy", "driver");
					ws.addNameValuePair("routeID", obj.getJSONObject("route")
							.getString("routeID"));
					ws.addNameValuePair("goodsID", obj.getJSONObject("goods")
							.getString("goodsID"));
					String refID = obj.getString("refDealID");
					if (refID.equals("0")) {
						ws.addNameValuePair("refDealID", "");
					} else {
						ws.addNameValuePair("refDealID", refID);
					}
					ws.addNameValuePair("active", "1");
					ws.execute(new String[] { SERVICE_URL2 });
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		menu.setHeaderTitle("Lựa chọn");
		menu.add(0, info.position, 0, "Hủy");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		getActivity().getActionBar().setTitle("Đề nghị");
		getActivity().getActionBar().setIcon(
				R.drawable.ic_action_sort_by_size_white);
		list = new ArrayList<ListItem>();
		WebService ws = new WebService(WebService.POST_TASK, getActivity(),
				"Đang xử lý ...");
		ws.addNameValuePair("driverID", getActivity().getIntent()
				.getStringExtra("driverID"));
		ws.execute(new String[] { SERVICE_URL });
		myFragmentView = inflater.inflate(R.layout.activity_deals, container,
				false);
		list1 = (ListView) myFragmentView.findViewById(R.id.listView1);
		list1.setOnItemClickListener((new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				int id = Integer.parseInt(map.get((int) arg3));
				NotificationManager mNotificationManager = (NotificationManager) getActivity()
						.getSystemService(Context.NOTIFICATION_SERVICE);
				mNotificationManager.cancel(id);

				FragmentManager mng = getActivity().getSupportFragmentManager();
				FragmentTransaction trs = mng.beginTransaction();
				CancelOffer frag = new CancelOffer();
				Bundle bundle = new Bundle();
				bundle.putString("dealID", String.valueOf(id));
				frag.setArguments(bundle);
				trs.replace(R.id.content_frame, frag);
				trs.addToBackStack(null);
				trs.commit();
			}
		}));
		registerForContextMenu(list1);
		return myFragmentView;
	}

}
