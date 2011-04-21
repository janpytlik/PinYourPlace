package com.jetminds.pinyourplace.route;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.google.android.maps.GeoPoint;

public class RoadProvider {


	public static List<GeoPoint> getRoute(InputStream is) {
		MyHandler handler = new MyHandler();
		try {
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			parser.parse(is, handler);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return handler.getPoints();
	}

	public static String getUrl(double fromLat, double fromLon, double toLat, double toLon) {
		StringBuffer urlString = new StringBuffer();
		urlString.append("http://maps.google.com/maps?f=d&hl=en");
		urlString.append("&saddr=");// from
		urlString.append(Double.toString(fromLat));
		urlString.append(",");
		urlString.append(Double.toString(fromLon));
		urlString.append("&daddr=");// to
		urlString.append(Double.toString(toLat));
		urlString.append(",");
		urlString.append(Double.toString(toLon));
		urlString.append("&ie=UTF8&0&om=0&output=kml");
		return urlString.toString();
	}
}

class MyHandler extends DefaultHandler {
	
	private List<GeoPoint> points;
	private boolean isCoordinates = false;
	private boolean isInsideGeometryCollection = false;
	
	private StringBuilder builder;

	public List<GeoPoint> getPoints() {
		return this.points;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		builder.append(ch, start, length);
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		super.endElement(uri, localName, name);
		if (isCoordinates && isInsideGeometryCollection) {
			if (name.equalsIgnoreCase("coordinates")) {
				
				String[] sourcePoints = builder.toString().split(" ");
				
				for (String str : sourcePoints) {
					
					int lon = 0;
					int lat = 0;
					String[] xyParsed = str.split(",");					
					if (xyParsed[0] !=null) {
						lon = Integer.parseInt(xyParsed[0].replace(".", ""));
					}
					if (xyParsed[1] !=null) {
						lat = Integer.parseInt(xyParsed[1].replace(".", ""));
					}				

					if (lon != 0 && lat !=0) {
						points.add(new GeoPoint(lat, lon));
					}
				}
				
				isInsideGeometryCollection = false;
			}			
		}
		builder.setLength(0);
		isCoordinates = false;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		points = new ArrayList<GeoPoint>();
		builder = new StringBuilder();
	}

	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, name, attributes);
		if (name.equalsIgnoreCase("coordinates")) {
			isCoordinates = true;
		} else if (name.equalsIgnoreCase("GeometryCollection")) {
			isInsideGeometryCollection = true;
		}
	}	
	

}
