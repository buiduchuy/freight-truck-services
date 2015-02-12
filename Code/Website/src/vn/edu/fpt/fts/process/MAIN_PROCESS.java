///**
// * 
// */
//package vn.edu.fpt.fts.process;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
//import sun.net.www.http.HttpClient;
//
///**
// * @author Huy
// *
// */
//@SuppressWarnings("deprecation")
//public class MAIN_PROCESS {
//
//	/**
//	 * @param args
//	 * @throws ParseException 
//	 */
//	public static void main(String[] args) throws ParseException {
//		// TODO Auto-generated method stub
//		MatchingProcess matching = new MatchingProcess();
//
//		// Vung Tau
//		// Double latGoods = 10.4025053;
//		// Double longGoods = 107.1255859;
//
//		Double latGoods = 10.826469;
//		Double longGoods = 106.6799706;
//
//		// Quang Trung, TP.HCM
//		// Double latGoods = 10.853132;
//		// Double longGoods = 106.626289;
//
//		// Quang Trung, TP.HCM
//		Double latStart = 10.853132;
//		Double longStart = 106.626289;
//
//		// Xuan Loc, Dong Nai
//		Double latEnd = 10.9234099;
//		Double longEnd = 107.4084806;
//
//		System.out.println(matching.checkDistance(latGoods, longGoods,
//				latStart, longStart, latEnd, longEnd, 0.5));
//
//		StepRoute stepRoute = new StepRoute();
//
//		List<StepRoute> listStepRoute = new ArrayList<StepRoute>();
//		
//		
//		InputStream inputStream = null;
//		String json = "";
//
//		try {
//			DefaultHttpClient client = new DefaultHttpClient();
//			HttpPost post = new HttpPost(
//					"https://maps.googleapis.com/maps/api/geocode/json?address=10115,germany");
//			HttpResponse response = client.execute(post);
//			HttpEntity entity = response.getEntity();
//			inputStream = entity.getContent();
//		} catch (Exception e) {
//		}
//
//		try {
//			BufferedReader reader = new BufferedReader(new InputStreamReader(
//					inputStream, "utf-8"), 8);
//			StringBuilder sbuild = new StringBuilder();
//			String line = null;
//			while ((line = reader.readLine()) != null) {
//				sbuild.append(line);
//			}
//			inputStream.close();
//			json = sbuild.toString();
//		} catch (Exception e) {
//		}
//
//		// now parse
//		JSONParser parser = new JSONParser();
//		Object obj = parser.parse(json);
//		JSONObject jb = (JSONObject) obj;
//
//		// now read
//		JSONArray jsonObject1 = (JSONArray) jb.get("results");
//		JSONObject jsonObject2 = (JSONObject) jsonObject1.get(0);
//		JSONObject jsonObject3 = (JSONObject) jsonObject2.get("geometry");
//		JSONObject location = (JSONObject) jsonObject3.get("location");
//
//		System.out.println("Lat = " + location.get("lat"));
//		System.out.println("Lng = " + location.get("lng"));
//
//	}
//
//}
