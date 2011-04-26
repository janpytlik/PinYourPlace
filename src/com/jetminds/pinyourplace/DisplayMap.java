package com.jetminds.pinyourplace;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.jetminds.pinyourplace.route.RoadProvider;

public class DisplayMap extends MapActivity {
	
	private MapView mapView;
	private MapController mapController;
	private List<GeoPoint> points = new ArrayList<GeoPoint>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaymap);
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapController = mapView.getController();
		
		
		// handle GPS		
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		MyLocationListener locationListener = new MyLocationListener();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

	}
		
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    
    
	private class MyLocationListener implements LocationListener {
		
		private double actualLatitude;
		private double actualLongitute;
		private double lastLatitude;
		private double lastLongitute;

		@Override
		public void onLocationChanged(Location loc) {
			if (loc != null) {
				
				System.out.println(loc.getSpeed());

                actualLatitude = loc.getLatitude();
                actualLongitute = loc.getLongitude();

	            if (lastLatitude != 0 || lastLongitute != 0) {
	            	//ask google for path
    				String url = RoadProvider.getUrl(lastLatitude, lastLongitute, actualLatitude, actualLongitute);
    				InputStream is = getConnection(url);
   					points.addAll(RoadProvider.getRoute(is));
    				mHandler.sendEmptyMessage(0);
	            } 
	            
	            GeoPoint p = new GeoPoint((int) (actualLatitude * 1E6), (int) (actualLongitute * 1E6));
                mapController.animateTo(p);
                mapController.setZoom(14);                
                mapView.invalidate();

                //save last values
                lastLatitude = actualLatitude;
                lastLongitute = actualLongitute;
	        }
		}

		@Override
		public void onProviderDisabled(String arg0) {
		}

		@Override
		public void onProviderEnabled(String arg0) {
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		}
	}

	
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			MapOverlay mapOverlay = new MapOverlay(points, mapView);
			List<Overlay> listOfOverlays = mapView.getOverlays();
			listOfOverlays.clear();
			listOfOverlays.add(mapOverlay);
			mapView.invalidate();
		};
	};

	private InputStream getConnection(String url) {
		InputStream is = null;
		try {
			URLConnection conn = new URL(url).openConnection();
			is = conn.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}	
}

class MapOverlay extends com.google.android.maps.Overlay {

	List<GeoPoint> mPoints;

	public MapOverlay(List<GeoPoint> points, MapView mv) {

		if (!points.isEmpty()) {
			mPoints = points;
			
//			int moveToLat = (mPoints.get(0).getLatitudeE6() + (mPoints.get(
//					mPoints.size() - 1).getLatitudeE6() - mPoints.get(0)
//					.getLatitudeE6()) / 2);
//			int moveToLong = (mPoints.get(0).getLongitudeE6() + (mPoints.get(
//					mPoints.size() - 1).getLongitudeE6() - mPoints.get(0)
//					.getLongitudeE6()) / 2);
//			
//			System.out.println(moveToLat + " , " + moveToLong);
//			GeoPoint moveTo = new GeoPoint(moveToLat, moveToLong);
//
//			MapController mapController = mv.getController();
//			mapController.animateTo(moveTo);
//			mapController.setZoom(14);
		}
	}

	@Override
	public boolean draw(Canvas canvas, MapView mv, boolean shadow, long when) {
		super.draw(canvas, mv, shadow);
		drawPath(mv, canvas);
		return true;
	}

	public void drawPath(MapView mv, Canvas canvas) {
		int x1 = -1, y1 = -1, x2 = -1, y2 = -1;
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(5);
		if (mPoints != null && mPoints.size() > 0) {
			for (int i = 0; i < mPoints.size(); i++) {
				Point point = new Point();
				mv.getProjection().toPixels(mPoints.get(i), point);
				x2 = point.x;
				y2 = point.y;
				if (i > 0) {
					canvas.drawLine(x1, y1, x2, y2, paint);
				}
				x1 = x2;
				y1 = y2;
			}
		}
	}
}
