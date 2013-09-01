package com.example.budgeter.datasources;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public abstract class AbstractDataSource {
	protected static SQLiteDatabase database;
	
	protected static DatabaseHelper dbHelper = null;
	protected String TAG = "Budgeteer";
	
	public AbstractDataSource(Context context)
	{
		if(dbHelper == null)
			dbHelper = new DatabaseHelper(context);
		
		Log.d(TAG,"database initialized");
		
		if(database == null)
			database = dbHelper.getWritableDatabase();
		
		Log.d(TAG,"database = "+database);
	}

}
