package com.example.budgeter.datasources;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final String TAG = "Budgeteer";
	
	private static final String DATABASE_NAME = "Budgeteer";
	private static final int DATABASE_VERSION = 25;
	private static final String[] TABLES = 
		{
			"transactions",
			"categories"
		};
	private static final String[][] TABLE_COLUMNS = 
		{
			{"id integer primary key AUTOINCREMENT, amount float, category_id integer, transaction_type integer not null, desc text, " +
					"created TIMESTAMP DEFAULT CURRENT_TIMESTAMP"},
			{"id integer primary key AUTOINCREMENT, name text"}
		};
	private static final String[] INSERTS = 
		{
			"INSERT INTO categories(name) VALUES('Food');",
			"INSERT INTO categories(name) VALUES('Entertainment');",
			"INSERT INTO categories(name) VALUES('Gas');",
			"INSERT INTO transactions(amount, category_id,transaction_type,desc) VALUES(143.05,1,2,\"Paid for a gig\" )"
		};
	
	public DatabaseHelper(Context context)
	{
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase database)
	{
		
		String createDB = new String();
		
		for(int i=0;i<TABLES.length;i++)
		{
			createDB += "create table "+TABLES[i]+" (";
			
			for(int j=0;j<TABLE_COLUMNS[i].length;j++)
			{
				createDB += TABLE_COLUMNS[i][j];
				
				if( j > TABLE_COLUMNS[i].length-1 )
					createDB += ",";	
			}
			createDB += "); ";
			
			database.execSQL(createDB);
			createDB = "";
		}
		
		Log.d(TAG,"Create Statement: "+createDB);
		
		
		for(int i=0;i<INSERTS.length;i++)
		{
			database.execSQL(INSERTS[i]);
		}
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		Log.w(DatabaseHelper.class.getName(),
				 "Upgrading database from version " + oldVersion + " to "
                         + newVersion + ", which will destroy all old data");
         database.execSQL("DROP TABLE IF EXISTS transactions");
         database.execSQL("DROP TABLE IF EXISTS categories");
         onCreate(database);
	}
	
	
}
