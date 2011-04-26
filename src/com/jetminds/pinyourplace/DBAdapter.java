package com.jetminds.pinyourplace;

import java.sql.SQLException;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

	private final Context context;

	private DBOpenHelper dbHelper;
	private SQLiteDatabase db;
	
	public DBAdapter(Context ctx) {
		this.context = ctx;
		dbHelper = new DBOpenHelper(context);
	}

	public DBAdapter open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void startTrip() {
		ContentValues initialValues = new ContentValues();
//        initialValues.put(context.getString(R.string.), trip.getName());
//        initialValues.put(TRIP_DESCRIPTION, trip.getDescription());
//        return db.insert(TRACK_TABLE_NAME, null, initialValues);
//
//		
//		db.update(table, values, whereClause, whereArgs)
	}

	public void close() {
		dbHelper.close();
	}
	
	public class DBOpenHelper extends SQLiteOpenHelper {

		private static final int DATABASE_VERSION = 2;
		
		DBOpenHelper(Context context) {
	        super(context, context.getString(R.string.db_name), null, DATABASE_VERSION);
	    }

	    @Override
	    public void onCreate(SQLiteDatabase db) {
	    	db.execSQL(context.getString(R.string.db_create_table_trip));
	        db.execSQL(context.getString(R.string.db_create_table_location));
	    }
	    
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}


}
