/**
 * 
 */
package vn.edu.fpt.fts.process;

/**
 * @author Huy
 *
 */
public class MAIN_PROCESS {

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MatchingProcess matching = new MatchingProcess();
		MapsProcess mapProcess = new MapsProcess();

		// Vung Tau
		// Double latGoods = 10.4025053;
		// Double longGoods = 107.1255859;

		Double latGoods = 10.853132;
		Double longGoods = 106.626289;

		// Quang Trung, TP.HCM
		// Double latGoods = 10.853132;
		// Double longGoods = 106.626289;

		// Quang Trung, TP.HCM
		Double latStart = 10.853132;
		Double longStart = 106.626289;

		// Xuan Loc, Dong Nai
		Double latEnd = 10.9234099;
		Double longEnd = 107.4084806;

		// GoodsDAO goodsDao = new GoodsDAO();
		LatLng goodsStartLocation = new LatLng();
		goodsStartLocation.setLatitude(21.0248455);
		goodsStartLocation.setLongitude(105.8287365);

		LatLng goodsFinishLocation = new LatLng();
		goodsFinishLocation.setLatitude(10.824391);
		goodsFinishLocation.setLongitude(106.628505);


		System.out.println(mapProcess.checkDistance(9, goodsStartLocation,
				goodsFinishLocation, 2));

	}

}
