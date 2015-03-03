/**
 * 
 */
package vn.edu.fpt.fts.dao;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import vn.edu.fpt.fts.common.Common;

/**
 * @author Huy
 *
 */
public class MAIN_DAO {

	/**
	 * @param args
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws XPathExpressionException 
	 */
	public static void main(String[] args) throws XPathExpressionException, IOException, ParserConfigurationException, SAXException {
		Common c= new Common();
		String address ="126 Nguyễn Thị Minh Khai, Hồ Chí Minh, Việt Nam";
		System.out.println(c.latGeoCoding(address));
		System.out.println(c.lngGeoCoding(address));
	}

}
