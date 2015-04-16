/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.io.IOException;

import vn.edu.fpt.fts.common.Common;

/**
 * @author Huy
 *
 */
public class MAIN_PROCESS {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// System.out.println(Common.calculateGoodsPrice(2000, 8860 / 1000));
		
		System.out.println("Classpath: '" + System.getProperty( "java.class.path" ) + "'" );
		
		Common common = new Common();
		try {
			System.out.println(common.getPropValues());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
