package com.jetminds.pinyourplace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PinYourPlace extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// handle GO button click
		Button goButton = (Button) this.findViewById(R.id.goButton);
		goButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				Intent i = new Intent(PinYourPlace.this, DisplayMap.class);
				startActivity(i);
			}

		});
	}
}