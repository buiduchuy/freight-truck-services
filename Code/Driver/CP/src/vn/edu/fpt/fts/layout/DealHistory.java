package vn.edu.fpt.fts.layout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
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

import vn.edu.fpt.fts.classes.Constant;
import vn.edu.fpt.fts.drawer.ListItem;
import vn.edu.fpt.fts.drawer.ListItemAdapter;
import vn.edu.fpt.fts.drawer.ListItemAdapter3;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class DealHistory extends android.support.v4.app.Fragment {

	@SuppressLint("UseSparseArrays")
	ArrayList<String> map = new ArrayList<String>();
	ArrayList<ListItem> list;
	ListItemAdapter3 adapter;
	ListView list1;
	View myFragmentView;
	private static final String SERVICE_URL = Constant.SERVICE_URL
			+ "Deal/getDealByDriverID";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		getActivity().getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE
				| ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
		getActivity().getActionBar().setIcon(R.drawable.ic_action_event_white);
		getActivity().getActionBar().setTitle("Lịch sử");
		list = new ArrayList<ListItem>();
		WebService ws = new WebService(WebService.POST_TASK, getActivity(),
				"Đang xử lý ...");
		ws.addNameValuePair("driverID", getActivity().getIntent()
				.getStringExtra("driverID"));
		ws.execute(new String[] { SERVICE_URL });
		myFragmentView = inflater.inflate(R.layout.activity_deal_history,
				container, false);
		list1 = (ListView) myFragmentView.findViewById(R.id.listView1);
		list1.setOnItemClickListener((new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				int id = Integer.parseInt(map.get((int) arg3));
				FragmentManager mng = getActivity().getSupportFragmentManager();
				FragmentTransaction trs = mng.beginTransaction();
				DealHistoryDetail frag = new DealHistoryDetail();
				Bundle bundle = new Bundle();
				bundle.putString("dealID", String.valueOf(id));
				frag.setArguments(bundle);
				trs.replace(R.id.content_frame, frag);
				trs.addToBackStack(null);
				trs.commit();
			}
		}));
		return myFragmentView;
	}

	private class WebService extends AsyncTask<String, Integer, String> {

		public static final int POST_TASK = 1;
		public static final int GET_TASK = 2;

		private static final String TAG = "WebServiceTask";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 60000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 60000;

		private int taskType = GET_TASK;
		private Context mContext = null;
		private String processMessage = "Processing...";

		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		private ProgressDialog pDlg = null;

		public WebService(int taskType, Context mContext, String processMessage) {

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
			JSONObject obj;
			if (!response.equals("null")) {
				try {
					int count = 1;
					String status = "";
					obj = new JSONObject(response);
					JSONArray array = obj.getJSONArray("deal");
					for (int i = 0; i < array.length(); i++) {
						JSONObject item = array.getJSONObject(i);
						if (!item.getString("dealStatusID").equals("1")) {
							if (item.getString("createBy").equals("driver")) {
								JSONObject rt = item.getJSONObject("route");
								String title = "";
								String[] start = rt
										.getString("startingAddress")
										.replaceAll("(?i), Vietnam", "")
										.replaceAll("(?i), Viet Nam", "")
										.replaceAll("(?i), Việt Nam", "")
										.split(",");
								title = start[start.length - 1].trim();

								if (obj.has("routeMarkers")) {
									Object intervent = obj.get("routeMarkers");
									if (intervent instanceof JSONArray) {
										JSONArray catArray = obj
												.getJSONArray("routeMarkers");
										for (int j = 0; j < catArray.length(); j++) {
											JSONObject cat = catArray
													.getJSONObject(j);
											if (!cat.getString(
													"routeMarkerLocation")
													.equals("")) {
												title += " - "
														+ cat.getString("routeMarkerLocation");
											}
										}
									} else if (intervent instanceof JSONObject) {
										JSONObject cat = obj
												.getJSONObject("routeMarkers");
										title += " - "
												+ cat.getString("routeMarkerLocation");
									}
								}

								String[] end = rt
										.getString("destinationAddress")
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
								JSONObject owner = item.getJSONObject("goods")
										.getJSONObject("owner");
								if (item.getString("dealStatusID").equals("2")) {
									status = "Đã chấp nhận";
									list.add(new ListItem(owner.getString("email")
											+ " gửi đề nghị cho lộ trình:", title,
											status, createD));
								} else if (item.getString("dealStatusID")
										.equals("3")) {
									status = "Đã từ chối";
									list.add(new ListItem(owner.getString("email")
											+ " gửi đề nghị cho lộ trình:", title,
											status, createD));
								} else if (item.getString("dealStatusID")
										.equals("4")) {
									status = "Đã hủy";
									list.add(new ListItem(
											"Bạn đã gửi đề nghị cho "
													+ owner.getString("email")
													+ ":", title, status,
											createD));
								}
								count++;
								map.add(item.getString("dealID"));
							} else if (item.getString("createBy").equals(
									"owner")) {
								JSONObject gd = item.getJSONObject("goods");
								if (gd.getString("active").equals("1")) {
									String title = "";
									String[] start = gd
											.getString("pickupAddress")
											.replaceAll("(?i), Vietnam", "")
											.replaceAll("(?i), Viet Nam", "")
											.replaceAll("(?i), Việt Nam", "")
											.split(",");
									title = start[start.length - 1].trim();

									String[] end = gd
											.getString("deliveryAddress")
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
									JSONObject owner = item.getJSONObject(
											"goods").getJSONObject("owner");
									if (item.getString("dealStatusID").equals(
											"2")) {
										status = "Đã được chấp nhận";
										list.add(new ListItem(
												"Bạn đã gửi đề nghị cho "
														+ owner.getString("email")
														+ ":", title, status,
												createD));
									} else if (item.getString("dealStatusID")
											.equals("3")) {
										status = "Đã bị từ chối";
										list.add(new ListItem(
												"Bạn đã gửi đề nghị cho "
														+ owner.getString("email")
														+ ":", title, status,
												createD));
									} else if (item.getString("dealStatusID")
											.equals("4")) {
										list.add(new ListItem(owner.getString("email")
												+ " gửi đề nghị cho lộ trình:", title,
												status, createD));
										status = "Đã bị hủy";
									}
									count++;
									map.add(item.getString("dealID"));
								}
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
			adapter = new ListItemAdapter3(getActivity(), list);
			list1.setEmptyView(myFragmentView.findViewById(R.id.emptyElement));
			list1.setAdapter(adapter);
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
			} catch (SocketTimeoutException e) {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(),
								"Không thể kết nối tới máy chủ",
								Toast.LENGTH_SHORT).show();
					}
				});
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
