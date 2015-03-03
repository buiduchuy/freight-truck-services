package vn.edu.fpt.fts.layout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

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

import vn.edu.fpt.fts.classes.Constant;
import vn.edu.fpt.fts.drawer.ListItem;
import vn.edu.fpt.fts.drawer.ListItemAdapter;
import vn.edu.fpt.fts.drawer.ListItemAdapter2;

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
import android.widget.Toast;

public class RouteList extends Fragment {

	Calendar cal = Calendar.getInstance();
	ArrayList<ListItem> list;
	ArrayList<String> map;
	ListItemAdapter2 adapter;
	ListView list1;
	View myFragmentView;
	private static final String SERVICE_URL = Constant.SERVICE_URL
			+ "Route/get";

	@SuppressLint("UseSparseArrays")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		list = new ArrayList<ListItem>();
		map = new ArrayList<String>();
		getActivity().setTitle("Danh sách lộ trình");
		WebService ws = new WebService(WebService.GET_TASK, getActivity(),
				"Đang lấy dữ liệu ...");
		ws.execute(new String[] { SERVICE_URL });
		myFragmentView = inflater.inflate(R.layout.activity_route_list,
				container, false);
		list1 = (ListView) myFragmentView.findViewById(R.id.listView1);
		list1.setOnItemClickListener((new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				int id = Integer.parseInt(map.get((int) arg3));
				FragmentManager mng = getActivity().getSupportFragmentManager();
				FragmentTransaction trs = mng.beginTransaction();
				CurrentRoute frag = new CurrentRoute();
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

	private class WebService extends AsyncTask<String, Integer, String> {

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

		@Override
		protected void onPostExecute(String response) {
			// Xu li du lieu tra ve sau khi insert thanh cong
			// handleResponse(response);
			JSONObject obj;

			String title = "", description = "";
			if (!response.equals("null")) {
				try {
					obj = new JSONObject(response);
					Object intervent1 = obj.get("route");
					if (intervent1 instanceof JSONArray) {
						JSONArray array = obj.getJSONArray("route");
						for (int i = array.length() - 1; i >= 0; i--) {
							JSONObject item = array.getJSONObject(i);
							if (item.getString("driverID").equals(
									getActivity().getIntent().getStringExtra(
											"driverID"))
									&& item.getString("active").equals("1")) {
								Object intervent;

								String[] start = item
										.getString("startingAddress")
										.replaceAll("(?i), Vietnam", "")
										.replaceAll("(?i), Viet Nam", "")
										.replaceAll("(?i), Việt Nam", "")
										.split(",");
								title = start[start.length - 1].trim();

								if (item.has("routeMarkers")) {
									intervent = item.get("routeMarkers");

									if (intervent instanceof JSONArray) {
										JSONArray catArray = item
												.getJSONArray("routeMarkers");
										for (int j = 0; j < catArray.length(); j++) {
											JSONObject cat = catArray
													.getJSONObject(j);
											String[] point = cat
													.getString(
															"routeMarkerLocation")
													.replaceAll(
															"(?i), Vietnam", "")
													.replaceAll(
															"(?i), Viet Nam",
															"")
													.replaceAll(
															"(?i), Việt Nam",
															"").split(",");
											if (!point[point.length - 1]
													.equals("")) {
												title += " - "
														+ point[point.length - 1]
																.trim();
											}
										}
									} else if (intervent instanceof JSONObject) {
										JSONObject cat = item
												.getJSONObject("routeMarkers");
										String[] point = cat
												.getString(
														"routeMarkerLocation")
												.replaceAll("(?i), Vietnam", "")
												.replaceAll("(?i), Viet Nam",
														"")
												.replaceAll("(?i), Việt Nam",
														"").split(",");
										if (!point[point.length - 1].equals("")) {
											title += " - "
													+ point[point.length - 1]
															.trim();
										}
									}
								}

								String[] end = item
										.getString("destinationAddress")
										.replaceAll("(?i), Vietnam", "")
										.replaceAll("(?i), Viet Nam", "")
										.replaceAll("(?i), Việt Nam", "")
										.split(",");
								title += " - " + end[end.length - 1].trim();
								SimpleDateFormat format = new SimpleDateFormat(
										"yyyy-MM-dd hh:mm:ss");
								Date finishDate = new Date();
								try {
									Date startDate = format.parse(item
											.getString("startTime"));
									format.applyPattern("dd/MM/yyyy");
									String sd = format.format(startDate);
									format.applyPattern("yyyy-MM-dd hh:mm:ss");
									finishDate = format.parse(item
											.getString("finishTime"));
									format.applyPattern("dd/MM/yyyy");
									String fd = format.format(finishDate);
									description = sd + " - " + fd;
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								ListItem itm = new ListItem(title, description,
										"");
								list.add(itm);
								if (finishDate.before(Calendar.getInstance()
										.getTime())) {
									list.remove(itm);
								}
								map.add(item.getString("routeID"));
							}
						}
					} else if (intervent1 instanceof JSONObject) {
						JSONObject item = obj.getJSONObject("route");
						if (item.getString("driverID").equals(
								getActivity().getIntent().getStringExtra(
										"driverID"))
								&& item.getString("active").equals("1")) {
							Object intervent;

							String[] start = item.getString("startingAddress")
									.replaceAll("(?i), Vietnam", "")
									.replaceAll("(?i), Viet Nam", "")
									.replaceAll("(?i), Việt Nam", "")
									.split(",");
							title = start[start.length - 1].trim();

							if (item.has("routeMarkers")) {
								intervent = item.get("routeMarkers");

								if (intervent instanceof JSONArray) {
									JSONArray catArray = item
											.getJSONArray("routeMarkers");
									for (int j = 0; j < catArray.length(); j++) {
										JSONObject cat = catArray
												.getJSONObject(j);
										String[] point = cat
												.getString(
														"routeMarkerLocation")
												.replaceAll("(?i), Vietnam", "")
												.replaceAll("(?i), Viet Nam",
														"")
												.replaceAll("(?i), Việt Nam",
														"").split(",");
										if (!point[point.length - 1].equals("")) {
											title += " - "
													+ point[point.length - 1]
															.trim();
										}
									}
								} else if (intervent instanceof JSONObject) {
									JSONObject cat = item
											.getJSONObject("routeMarkers");
									String[] point = cat
											.getString("routeMarkerLocation")
											.replaceAll("(?i), Vietnam", "")
											.replaceAll("(?i), Viet Nam", "")
											.replaceAll("(?i), Việt Nam", "")
											.split(",");
									if (!point[point.length - 1].equals("")) {
										title += " - "
												+ point[point.length - 1]
														.trim();
									}
								}
							}

							String[] end = item.getString("destinationAddress")
									.replaceAll("(?i), Vietnam", "")
									.replaceAll("(?i), Viet Nam", "")
									.replaceAll("(?i), Việt Nam", "")
									.split(",");
							title += " - " + end[end.length - 1].trim();
							SimpleDateFormat format = new SimpleDateFormat(
									"yyyy-MM-dd hh:mm:ss");
							Date finishDate = new Date();
							try {
								Date startDate = format.parse(item
										.getString("startTime"));
								format.applyPattern("dd/MM/yyyy");
								String sd = format.format(startDate);
								format.applyPattern("yyyy-MM-dd hh:mm:ss");
								finishDate = format.parse(item
										.getString("finishTime"));
								format.applyPattern("dd/MM/yyyy");
								String fd = format.format(finishDate);
								description = sd + " - " + fd;
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							ListItem itm = new ListItem(title, description, "");
							list.add(itm);
							if (finishDate.before(Calendar.getInstance()
									.getTime())) {
								list.remove(itm);
							}
							map.add(item.getString("routeID"));
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			adapter = new ListItemAdapter2(getActivity(), list);
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
