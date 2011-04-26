package com.jetminds.pinyourplace;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;

public class HistoryActivity extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.historylistview);
	  	  
      /* Find Tablelayout defined in main.xml */
      TableLayout tl = (TableLayout)findViewById(R.id.myTableLayout);
           /* Create a new row to be added. */
           TableRow tr = new TableRow(this);
           tr.setLayoutParams(new LayoutParams(
                          LayoutParams.FILL_PARENT,
                          LayoutParams.WRAP_CONTENT));
                /* Create a Button to be the row-content. */
                Button b = new Button(this);
                b.setText("Dynamic Button");
                b.setLayoutParams(new LayoutParams(
                          LayoutParams.FILL_PARENT,
                          LayoutParams.WRAP_CONTENT));
                /* Add Button to row. */
                tr.addView(b);
      /* Add row to TableLayout. */
      tl.addView(tr,new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
	}


}
