package com.jetminds.pinyourplace;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;

public class PinYourPlace extends TabActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//setup tab layout
		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost
				
		Intent intent = new Intent(this, Home.class);
		tabHost.addTab(tabHost.newTabSpec("Home")
				.setIndicator("Home", res.getDrawable(R.drawable.ic_tab_home))
				.setContent(intent));

		Intent intent2 = new Intent(this, History.class);
		tabHost.addTab(tabHost
				.newTabSpec("History")
				.setIndicator("History", res.getDrawable(R.drawable.ic_tab_history))
				.setContent(intent2));

		Intent intent3 = new Intent(this, Settings.class);
		tabHost.addTab(tabHost
				.newTabSpec("Settings")
				.setIndicator("Settings", res.getDrawable(R.drawable.ic_tab_settings))
				.setContent(intent3));
		
		tabHost.setCurrentTab(0);

		// Set tabs Colors
		tabHost.setBackgroundColor(Color.BLACK);
		tabHost.getTabWidget().setBackgroundColor(Color.BLACK); 

	}
}