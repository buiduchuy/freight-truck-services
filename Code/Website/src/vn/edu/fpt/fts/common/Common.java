/**
 * 
 */
package vn.edu.fpt.fts.common;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Huy
 *
 */
public final class Common {

	public static final String CLASSSQLSERVERDRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	// public static final String CONNECTION =
	//"jdbc:sqlserver://localhost:1433;databaseName=FTS";
	public static final String CONNECTION = "jdbc:sqlserver://fts2015.cloudapp.net:1433;databaseName=FTS";
	public static final String usernamedb = "sa";
	 //public static final String passworddb = "123456";
	public static final String passworddb = "huy2108.";

	// Status of Goods, Route, Deal, Order
	public static final int deactivate = 0;
	public static final int activate = 1;

	// Status of Deal
	public static final int deal_pending = 1;
	public static final int deal_accept = 2;
	public static final int deal_decline = 3;
	public static final int deal_cancel = 4;

	// Status of Order
	public static final int order_pending = 1;
	public static final int order_driver = 2;
	public static final int order_owner = 3;
	public static final int order_staff = 4;

	// Max allow distance for matching goods and routes
	public static final int maxAllowDistance = 10;
	// Price for 1 kilometer
	public static final double perKilometer = 15;
	public static final double perKilogram = 0.2;
	public static final double priceCreateGood = 15;
	public static final String API_KEY = "AIzaSyD_etqEdI3WY_xfwnnJNuzT8uLalBofaT0";

	public String changeFormatDate(String dateInput, String oldFormat,
			String newFormat) {
		try {
			SimpleDateFormat sdfSource = new SimpleDateFormat(oldFormat);
			Date date = sdfSource.parse(dateInput);
			SimpleDateFormat sdfDestination = new SimpleDateFormat(newFormat);
			dateInput = sdfDestination.format(date);
			return dateInput.toString();

		} catch (ParseException pe) {
			return dateInput;
		}
	}

	private static final String GEOCODER_REQUEST_PREFIX_FOR_XML = "http://maps.google.com/maps/api/geocode/xml";

	public float latGeoCoding(String address) throws IOException,
			XPathExpressionException, ParserConfigurationException,
			SAXException {

		// prepare a URL to the geocoder
		URL url = new URL(GEOCODER_REQUEST_PREFIX_FOR_XML + "?address="
				+ URLEncoder.encode(address, "UTF-8") + "&sensor=false");

		// prepare an HTTP connection to the geocoder
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		Document geocoderResultDocument = null;
		try {
			// open the connection and get results as InputSource.
			conn.connect();
			InputSource geocoderResultInputSource = new InputSource(
					conn.getInputStream());

			// read result and parse into XML Document
			geocoderResultDocument = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(geocoderResultInputSource);
		} finally {
			conn.disconnect();
		}

		// prepare XPath
		XPath xpath = XPathFactory.newInstance().newXPath();

		// extract the result
		NodeList resultNodeList = null;

		// a) obtain the formatted_address field for every result
		resultNodeList = (NodeList) xpath.evaluate(
				"/GeocodeResponse/result/formatted_address",
				geocoderResultDocument, XPathConstants.NODESET);
		for (int i = 0; i < resultNodeList.getLength(); ++i) {

		}

		// b) extract the locality for the first result
		resultNodeList = (NodeList) xpath
				.evaluate(
						"/GeocodeResponse/result[1]/address_component[type/text()='locality']/long_name",
						geocoderResultDocument, XPathConstants.NODESET);
		for (int i = 0; i < resultNodeList.getLength(); ++i) {
		}

		// c) extract the coordinates of the first result
		resultNodeList = (NodeList) xpath.evaluate(
				"/GeocodeResponse/result[1]/geometry/location/*",
				geocoderResultDocument, XPathConstants.NODESET);
		float lat = Float.NaN;
		float lng = Float.NaN;
		for (int i = 0; i < resultNodeList.getLength(); ++i) {
			Node node = resultNodeList.item(i);
			if ("lat".equals(node.getNodeName()))
				lat = Float.parseFloat(node.getTextContent());
			if ("lng".equals(node.getNodeName()))
				lng = Float.parseFloat(node.getTextContent());
		}
		return lat;
	}

	public float lngGeoCoding(String address) throws IOException,
			XPathExpressionException, ParserConfigurationException,
			SAXException {

		// prepare a URL to the geocoder
		URL url = new URL(GEOCODER_REQUEST_PREFIX_FOR_XML + "?address="
				+ URLEncoder.encode(address, "UTF-8") + "&sensor=false");

		// prepare an HTTP connection to the geocoder
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		Document geocoderResultDocument = null;
		try {
			// open the connection and get results as InputSource.
			conn.connect();
			InputSource geocoderResultInputSource = new InputSource(
					conn.getInputStream());

			// read result and parse into XML Document
			geocoderResultDocument = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(geocoderResultInputSource);
		} finally {
			conn.disconnect();
		}

		// prepare XPath
		XPath xpath = XPathFactory.newInstance().newXPath();

		// extract the result
		NodeList resultNodeList = null;

		// a) obtain the formatted_address field for every result
		resultNodeList = (NodeList) xpath.evaluate(
				"/GeocodeResponse/result/formatted_address",
				geocoderResultDocument, XPathConstants.NODESET);
		for (int i = 0; i < resultNodeList.getLength(); ++i) {

		}

		// b) extract the locality for the first result
		resultNodeList = (NodeList) xpath
				.evaluate(
						"/GeocodeResponse/result[1]/address_component[type/text()='locality']/long_name",
						geocoderResultDocument, XPathConstants.NODESET);
		for (int i = 0; i < resultNodeList.getLength(); ++i) {
		}

		// c) extract the coordinates of the first result
		resultNodeList = (NodeList) xpath.evaluate(
				"/GeocodeResponse/result[1]/geometry/location/*",
				geocoderResultDocument, XPathConstants.NODESET);
		float lat = Float.NaN;
		float lng = Float.NaN;
		for (int i = 0; i < resultNodeList.getLength(); ++i) {
			Node node = resultNodeList.item(i);
			if ("lat".equals(node.getNodeName()))
				lat = Float.parseFloat(node.getTextContent());
			if ("lng".equals(node.getNodeName()))
				lng = Float.parseFloat(node.getTextContent());
		}
		return lng;
	}
	public double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		  double theta = lon1 - lon2;
		  double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		  dist = Math.acos(dist);
		  dist = rad2deg(dist);
		  dist = dist * 60 * 1.1515;
		  if (unit == "K") {
		    dist = dist * 1.609344;
		  } else if (unit == "N") {
		  	dist = dist * 0.8684;
		    }
		  return (dist);
		}

		/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
		/*::  This function converts decimal degrees to radians             :*/
		/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	public double deg2rad(double deg) {
		  return (deg * Math.PI / 180.0);
		}

		/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
		/*::  This function converts radians to decimal degrees             :*/
		/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	public  double rad2deg(double rad) {
		  return (rad * 180 / Math.PI);
		}
}
