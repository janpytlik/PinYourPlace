package com.jetminds.pinyourplace;

import java.sql.SQLException;
import java.sql.Timestamp;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

public class History extends ListActivity {

	private DBAdapter dbAdapter;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.historytab);
		
		dbAdapter = new DBAdapter(this);
		Cursor cursor = null;
		try {
			dbAdapter.open();
			cursor = dbAdapter.getTripList();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MyAdapter mAdapter = new MyAdapter(this, cursor);
        // set this adapter as your ListActivity's adapter
        this.setListAdapter(mAdapter);

	}
	
	@Override
	protected void onDestroy() {
		if (dbAdapter != null) dbAdapter.close();
		super.onDestroy();
	}

    private class MyAdapter extends ResourceCursorAdapter {

        public MyAdapter(Context context, Cursor cur) {
            super(context, R.layout.historyrow, cur);
        }

        @Override
        public View newView(Context context, Cursor cur, ViewGroup parent) {
            LayoutInflater li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return li.inflate(R.layout.historyrow, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cur) {
            TextView nameText = (TextView)view.findViewById(R.id.name_entry);
            TextView numberText = (TextView)view.findViewById(R.id.number_entry);
            TextView createdText = (TextView)view.findViewById(R.id.created_entry);

            nameText.setText(cur.getString(cur.getColumnIndex(context.getString(R.string.db_trip_name))));
            numberText.setText(cur.getString(cur.getColumnIndex(context.getString(R.string.db_trip_distance)))+" km");
            String createdTime = cur.getString(cur.getColumnIndex(context.getString(R.string.db_trip_created)));
            Timestamp created = new Timestamp(Long.parseLong(createdTime ));
            createdText.setText(created.toString());
        }
    }
}
