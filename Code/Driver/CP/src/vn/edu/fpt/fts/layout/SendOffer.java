package vn.edu.fpt.fts.layout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

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
import org.json.JSONException;
import org.json.JSONObject;

import vn.edu.fpt.fts.classes.Constant;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendOffer extends Fragment {

	Button button;
	EditText price;
	EditText note;
	TextView content;
	private static final String SERVICE_URL = Constant.SERVICE_URL + "Deal/Create";
	private static final String SERVICE_URL2 = Constant.SERVICE_URL + "Goods/getGoodsByID";
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getActivity().setTitle("Gửi đề nghị");
		View v = inflater.inflate(R.layout.activity_send_offer, container, false);
		
		button = (Button) v.findViewById(R.id.button1);
		price = (EditText) v.findViewById(R.id.editText1);
		note = (EditText) v.findViewById(R.id.editText2);
		content = (TextView) v.findViewById(R.id.textView3);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Calendar calendar = Calendar.getInstance();
				String current = String.valueOf(calendar.get(Calendar.YEAR))
						+ "-" + String.valueOf(calendar.get(Calendar.MONTH)+1) + "-"
						+ String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + " "
						+ String.valueOf(calendar.get(Calendar.HOUR)) + ":"
						+ String.valueOf(calendar.get(Calendar.MINUTE)) + ":"
						+ String.valueOf(calendar.get(Calendar.SECOND));
				String pr = price.getText().toString();
				if(pr.equals("")){
					Toast.makeText(getActivity(), "Giá đề nghị không được để trống", Toast.LENGTH_SHORT).show();
				}
				else {
					WebService ws = new WebService(WebService.POST_TASK,
							getActivity(), "Đang xử lý ...");
					ws.addNameValuePair("price", pr);
					ws.addNameValuePair("notes", note.getText().toString());
					ws.addNameValuePair("createTime", current);
					ws.addNameValuePair("createBy", "driver");
					ws.addNameValuePair("routeID", getArguments().getString("routeID"));
					ws.addNameValuePair("goodsID", getArguments().getString("goodID"));
					if(getArguments().getString("refID") == null) {
						ws.addNameValuePair("refDealID", "0");
					}
					else {
						ws.addNameValuePair("refDealID", getArguments().getString("refID"));
					}
					ws.addNameValuePair("dealStatusID", "2");
					ws.addNameValuePair("active", "1");
					ws.execute(new String[] { SERVICE_URL });
				}
			}
		});
		return v;
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
			pDlg.dismiss();
			if(response.equals("Success")) {
				Toast.makeText(getActivity(), "Gửi đề nghị thành công", Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(getActivity(), "Gửi đề nghị thất bại", Toast.LENGTH_SHORT).show();
			}
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
