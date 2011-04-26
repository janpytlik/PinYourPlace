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

	/**
	 * Open db connection
	 * @return
	 * @throws SQLException
	 */
	public DBAdapter open() throws SQLException {
		db = dbHelper.getWritableDatabase();
		return this;
	}
	
	/**
	 * Creates a new unnamed trip
	 * @return id of a trip
	 */
	public long startTrip() {
		ContentValues values = new ContentValues();
		values.put(context.getString(R.string.db_trip_name), context.getString(R.string.unsaved_trip));
		return db.insert(context.getString(R.string.db_table_trip), null, values);
	}
	
	/**
	 * Saves current location of trip
	 * @param tripId current trip
	 * @param longitude
	 * @param lattitude
	 */
	public void saveTripLocation(long tripId, double longitude, double lattitude) {
		ContentValues values = new ContentValues();
		values.put(context.getString(R.string.db_location_trip), tripId);
		values.put(context.getString(R.string.db_location_lon), longitude);
		values.put(context.getString(R.string.db_location_lat), lattitude);
		values.put(context.getString(R.string.db_location_created), System.currentTimeMillis());
		db.insert(context.getString(R.string.db_table_location), null, values);
	}	

	/**
	 * Close db connection
	 */
	public void close() {
		dbHelper.close();
	}
	
	public class DBOpenHelper extends SQLiteOpenHelper {

		private static final int DATABASE_VERSION = 1;
		
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
