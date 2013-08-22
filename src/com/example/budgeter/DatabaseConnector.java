package com.example.budgeter;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "Budgeteer";
	private static final int DATABASE_VERSION = 2;
	private static final String[] tables = {"transactions","transaction_type","categories"};
	
	
	private static String database_create;
	
	private DatabaseHelper(Context context)
	{
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}
	
}
