package com.example.budgeter.datasources;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.budgeter.models.Category;

public class CategoryDataSource extends AbstractDataSource {
	
	private final String TABLE = "categories";
	
	private final String CAT_ID = "id",
			CAT_NAME = "name";
	
	public CategoryDataSource(Context context)
	{
		super(context);
	}
	
	public long createRecord(String name)
	{
		ContentValues values = new ContentValues();
		
		values.put(CAT_NAME, name);
		
		return database.insert(TABLE, null, values);
	}
	public Category createIfNotExists(String name)
	{
		Category category = this.findByName(name);
		
		if(category != null)
			return category;
		
		long id = this.createRecord(name);
		category = new Category((int)id,name);
		
		return category;
	}
	public Category findById(int id)
	{
		String[] cols = {CAT_ID,CAT_NAME};
		String[] selectionArgs = {""+id};
		String where = "id = ?";
		Cursor cursor = database.query(TABLE, cols, where, selectionArgs, null, null, null, "1");
		cursor.moveToFirst();
		Category category = null;
		if(cursor.getCount() > 0)
		{
			int index_name = cursor.getColumnIndex(CAT_NAME);
			int index_id = cursor.getColumnIndex(CAT_ID);
			
			category = new Category(cursor.getInt(index_id),cursor.getString(index_name));
		}
		
		return category;
		
	}
	public Category findByName(String name)
	{
		String[] cols = {CAT_ID,CAT_NAME};
		String[] selectionArgs = {name};
		String where = "name = ?";
		Cursor cursor = database.query(TABLE, cols, where, selectionArgs, null, null, null, "1");
		cursor.moveToFirst();
		Category category = null;
		if(cursor.getCount() > 0)
		{
			int index_name = cursor.getColumnIndex(CAT_NAME);
			int index_id = cursor.getColumnIndex(CAT_ID);
			
			category = new Category(cursor.getInt(index_id),cursor.getString(index_name));
		}
		
		return category;
	}
	public ArrayList<Category> selectAllRecords()
	{
		String[] cols = {CAT_ID,CAT_NAME};
		ArrayList<Category> categories = new ArrayList<Category>();
		Cursor cursor = database.query(false,TABLE,cols,null,null,null,null,CAT_NAME+" asc",null);
		
		cursor.moveToFirst();
		int index_id = cursor.getColumnIndex(CAT_ID);
		int index_name = cursor.getColumnIndex(CAT_NAME);
		
		while(!cursor.isAfterLast())
		{
			categories.add(new Category(cursor.getInt(index_id),cursor.getString(index_name)));
			cursor.moveToNext();
		}
		
		return categories;
	}
}
