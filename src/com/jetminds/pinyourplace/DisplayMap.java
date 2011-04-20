package com.jetminds.pinyourplace;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class DisplayMap extends MapActivity {
	
	private MapView mapView;
	private MapController mapController;

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

		@Override
		public void onLocationChanged(Location loc) {
			if (loc != null) {
	            Toast.makeText(getBaseContext(), 
	                "Location changed : Lat: " + loc.getLatitude() + 
	                " Lng: " + loc.getLongitude(), 
	                Toast.LENGTH_SHORT).show();
	            
	            GeoPoint p = new GeoPoint(
                        (int) (loc.getLatitude() * 1E6), 
                        (int) (loc.getLongitude() * 1E6));
                mapController.animateTo(p);
                mapController.setZoom(16);                
                mapView.invalidate();	            
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
    
}
