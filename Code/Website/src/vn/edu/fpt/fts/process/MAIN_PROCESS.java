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
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MatchingProcess matching = new MatchingProcess();

		// Vung Tau
		// Double latGoods = 10.4025053;
		// Double longGoods = 107.1255859;

		Double latGoods = 10.826469;
		Double longGoods = 106.6799706;

		// Quang Trung, TP.HCM
		// Double latGoods = 10.853132;
		// Double longGoods = 106.626289;

		// Quang Trung, TP.HCM
		Double latStart = 10.853132;
		Double longStart = 106.626289;

		// Xuan Loc, Dong Nai
		Double latEnd = 10.9234099;
		Double longEnd = 107.4084806;

		System.out.println(matching.checkDistance(latGoods, longGoods,
				latStart, longStart, latEnd, longEnd, 0.5));

	}

}
