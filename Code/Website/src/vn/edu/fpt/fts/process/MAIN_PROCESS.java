/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Huy
 *
 */
public class MAIN_PROCESS {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MapsProcess mp = new MapsProcess();
		List<String> listMarker = new ArrayList<String>();
		listMarker.add("danang");
		String url = mp.makeURL("hanoi", listMarker, "hochiminh");
		String jsonResult = mp.getJSONFromUrl(url);
		System.out.println(url);
		// LatLng latLng = mp.parseJsonRouteEnd(jsonResult);
		List<LatLng> list = mp.parseJsonList(jsonResult);

		for (LatLng latLng : list) {
			
			System.out.println(latLng.getLatitude() + ","
					+ latLng.getLongitude());
		}

	}
}
